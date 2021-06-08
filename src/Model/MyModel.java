package Model;

import Client.Client;
import IO.MyDecompressorInputStream;
import View.MazeDisplayer;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.AState;
import algorithms.search.Solution;

import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import Server.Configurations;

import Client.IClientStrategy;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Eden_Hai
 * @version 1.0
 * @since 29-05-2021
 */
public class MyModel extends Observable implements IModel
{
    private Maze maze;
    private Server mazeGeneratingServer;
    private Server solveSearchProblemServer;
    private Solution solution;
    private int characterRow;
    private int characterCol;
    private boolean isSolved;
    public Configurations configurations;

    private ExecutorService threadPool = Executors.newCachedThreadPool();


    public MyModel()
    {
        configurations = Configurations.initialize();

        // Initializing servers
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        mazeGeneratingServer.start();
        solveSearchProblemServer.start();
        isSolved = true;

    }

    /**
     * calls Server.stop() for both servers
     */
    public void stopServers()
    {
        if (isSolved)
        {
            mazeGeneratingServer.stop();
            solveSearchProblemServer.stop();
        }
    }
    private void generateMazeWithServer(int rows, int cols)
    {
        try
        {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy()
            {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer)
                {
                    try
                    {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{rows, cols};

                        // Send maze dimensions to server
                        toServer.writeObject(mazeDimensions);

                        toServer.flush();

                        // Read generated maze (compressed with MyCompressor) from server
                        byte[] compressedMaze = (byte[]) fromServer.readObject();
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));

                        // Allocating byte[] for the decompressed maze
                        int size = rows * cols + 12;
                        byte[] decompressedMaze = new byte[size];

                        // Fill decompressedMaze with bytes
                        is.read(decompressedMaze);

                        maze = new Maze(decompressedMaze);

                        // Close servers
                        toServer.close();
                        fromServer.close();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        }

        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void generateMaze(int rows, int cols)
    {
        /* Generate new maze through server */
        generateMazeWithServer(rows, cols);
        setChanged();
        notifyObservers("maze generated");

        /* Initialize the start position of character */
        this.characterRow = maze.getStartPosition().getRowIndex();
        this.characterCol = maze.getStartPosition().getColumnIndex();

        setChanged();
        notifyObservers("player moved");
    }

    @Override
    public Maze getMaze()
    {
        return maze;
    }

    @Override
    public void updatePlayerLocation(KeyCode keyCode)
    {
        switch (keyCode)
        {
            case UP:
            case NUMPAD8:
                if (isMoveValid(characterRow - 1, characterCol))
                {
                    characterRow--;
                    setChanged();
                    notifyObservers("player moved");
                }
                break;

            case DOWN:
            case NUMPAD2:
                if (isMoveValid(characterRow + 1, characterCol))
                {
                    characterRow++;
                    setChanged();
                    notifyObservers("player moved");
                }
                break;

            case LEFT:
            case NUMPAD4:
                setChanged();
                notifyObservers("player looked to the left");
                if (isMoveValid(characterRow, characterCol-1))
                {
                    characterCol--;
                    setChanged();
                    notifyObservers("player moved");
                }
                break;


            case RIGHT:
            case NUMPAD6:
                setChanged();
                notifyObservers("player looked to the right");
                if (isMoveValid(characterRow, characterCol+1))
                {
                    characterCol++;
                    setChanged();
                    notifyObservers("player moved");
                }
                break;

            /*----------------------diagonal moves---------------------*/

            // left-up
            case NUMPAD7:
                setChanged();
                notifyObservers("player looked to the left");
                if (isMoveValid(characterRow-1, characterCol-1) && (isMoveValid(characterRow, characterCol-1) || isMoveValid(characterRow-1, characterCol)))
                {
                    characterRow--;
                    characterCol--;
                    setChanged();
                    notifyObservers("player moved");
                }
                break;

            // left-down
            case NUMPAD1:
                setChanged();
                notifyObservers("player looked to the left");
                if (isMoveValid(characterRow+1, characterCol-1) && (isMoveValid(characterRow, characterCol-1) || isMoveValid(characterRow+1, characterCol)))
                {
                    characterRow++;
                    characterCol--;
                    setChanged();
                    notifyObservers("player moved");
                }
                break;

            // right-up
            case NUMPAD9:
                setChanged();
                notifyObservers("player looked to the right");
                if (isMoveValid(characterRow-1, characterCol+1) && (isMoveValid(characterRow-1, characterCol) || isMoveValid(characterRow, characterCol+1)))
                {
                    characterRow--;
                    characterCol++;
                    setChanged();
                    notifyObservers("player moved");
                }
                break;

            // right-down
            case NUMPAD3:
                setChanged();
                notifyObservers("player looked to the right");
                if (isMoveValid(characterRow+1, characterCol+1) && (isMoveValid(characterRow+1, characterCol) || isMoveValid(characterRow, characterCol+1)))
                {
                    characterRow++;
                    characterCol++;
                    setChanged();
                    notifyObservers("player moved");
                }
                break;
            default:
                return;
        }
        //TODO: need to check if is the goal position

    }


    @Override
    public int getCharacterRow()
    {
        return characterRow;
    }

    @Override
    public int getCharacterCol()
    {
        return characterCol;
    }

    @Override
    public void assignObserver(Observer o)
    {
        this.addObserver(o);
    }

    @Override
    public void solveMaze()
    {
        solveMazeWithServer();
        setChanged();
        notifyObservers("maze solved");
    }

    private void solveMazeWithServer()
    {
        try
        {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy()
            {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer)
                {
                    try
                    {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();

                        // Send maze to server
                        toServer.writeObject(maze);

                        toServer.flush();

                        // Read generated maze (compressed with MyCompressor) from server
                        solution = (Solution) fromServer.readObject();
                    }

                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });

            client.communicateWithServer();
        }

        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Solution getSolution()
    {
        return solution;
    }

    private boolean isMoveValid(int row, int col)
    {
        try { return maze.getMaze()[row][col] == 0; }
        catch (Exception e) { return false; }
    }

    @Override
    public void setMazeGeneratingAlgorithm(String generatingAlgorithm)
    {
        configurations.setConfiguration("mazeGeneratingAlgorithm", generatingAlgorithm);

    }

    @Override
    public void setMazeSearchingAlgorithm(String searchingAlgorithm)
    {
        configurations.setConfiguration("mazeSearchingAlgorithm", searchingAlgorithm);
    }



    @Override
    public void shutDown() {
        System.out.println("Close Model");
        mazeGeneratingServer.stop();
        if (solveSearchProblemServer != null)
        {
            solveSearchProblemServer.stop();
        }
        threadPool.shutdown();
    }


}
