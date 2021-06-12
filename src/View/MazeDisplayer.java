package View;

import algorithms.mazeGenerators.Maze;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * @author Eden_Hai
 * @version 1.0
 * @since 29-05-2021
 */
public class MazeDisplayer extends Canvas
{
    private Maze maze;
    public Solution solution;
    public boolean online;

    // player position:
    private int playerRow;
    private int playerCol;
    public boolean solutionIsShowing;

    double canvasHeight, canvasWidth, cellHeight, cellWidth;

    StringProperty terrainType = new SimpleStringProperty();
    StringProperty playerDirection = new SimpleStringProperty();
    StringProperty playerCharacter = new SimpleStringProperty();

    Media[] moveSounds = new Media[5];
    Media[] flipSounds = new Media[2];

    // wall and player images:
    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();

    // goal position image
    StringProperty imageFileNameGoalPosition = new SimpleStringProperty();

    // solution path image
    StringProperty imageFileNamePathSolution = new SimpleStringProperty();


    /* Getter and Setter */

    public int getPlayerRow() { return playerRow; }

    public int getPlayerCol() { return playerCol; }

    public String getImageFileNameWall() { return imageFileNameWall.get(); }

    public String getImageFileNamePlayer() { return imageFileNamePlayer.get(); }

    public String getImageFileNameGoalPosition() { return imageFileNameGoalPosition.get(); }

    public String getTerrainType() {return terrainType.get(); }

    public String getPlayerCharacter() {return playerCharacter.get();}

    public void setImageFileNameWall(String imageFileNameWall) { this.imageFileNameWall.set(imageFileNameWall); }

    public void setImageFileNamePlayer(String imageFileNamePlayer) { this.imageFileNamePlayer.set(imageFileNamePlayer); }

    public void setImageFileNameGoalPosition(String imageFileNameGoalPosition) { this.imageFileNameGoalPosition.set(imageFileNameGoalPosition); }

    public void setTerrainType(String terrainType) {this.terrainType.set(terrainType);}

    public void setPlayerCharacter(String characterName) {this.playerCharacter.set(characterName);}

    public MazeDisplayer()
    {
        widthProperty().addListener(e -> draw());
        heightProperty().addListener(e -> draw());
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, canvasWidth, canvasHeight);
        setTerrainType("grass");
        setPlayerCharacter("Tom");
        online = false;
    }

    public void setPlayerPosition(int playerRow, int playerCol)
    {
//        if (online)
//        {
            this.playerRow = playerRow;
            this.playerCol = playerCol;
            draw();
//        }
    }

    public void drawSolution()
    {
        canvasHeight = getHeight();
        canvasWidth = getWidth();
        cellHeight = canvasHeight / (maze.getRowSize() + 2);
        cellWidth = canvasWidth / (maze.getColSize() + 2);

        Image trophy = null;
        try
        {
            trophy = new Image(new FileInputStream("./resources/Character/Tom/footStep.png"));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("There is no tile image");
        }
        GraphicsContext graphicsContext = getGraphicsContext2D();
        int pathSize = solution.getSolutionPath().size();
        for (int index = 1; index < pathSize - 1; index++)
        {
            int rowIndex = ((MazeState) solution.getSolutionPath().get(index)).getPosition().getRowIndex();
            int colIndex = ((MazeState) solution.getSolutionPath().get(index)).getPosition().getColumnIndex();

            double x = (colIndex + 1) * cellWidth;
            double y = (rowIndex + 1) * cellHeight;
            graphicsContext.drawImage(trophy, x - cellWidth * 0.3, y - cellHeight * 0.7, cellWidth * 1.5, cellHeight * 1.5);
        }
    }

    public void drawMaze(Maze maze)
    {
        this.maze = maze;
        draw();
    }

    public void draw()
    {
        if (maze == null) return;

        canvasHeight = getHeight();
        canvasWidth = getWidth();
        int rows = maze.getRowSize();
        int cols = maze.getColSize();

        cellHeight = canvasHeight / (rows + 2);
        cellWidth = canvasWidth / (cols + 2);

        GraphicsContext graphicsContext = getGraphicsContext2D();

        // clear the canvas:
        graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

        drawMazeTiles(graphicsContext, rows, cols, cellHeight, cellWidth);
        drawMazePlayer(graphicsContext, cellHeight, cellWidth);
        drawMazeGoalPosition(graphicsContext, cellHeight, cellWidth);
        if (solutionIsShowing) drawSolution();
    }

    private void drawMazeGoalPosition(GraphicsContext graphicsContext, double cellHeight, double cellWidth)
    {
        Image goalImage = null;
        try
        {
            goalImage = new Image(new FileInputStream(getImageFileNameGoalPosition()));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("There is no goal image");
        }

        double x = (maze.getGoalPosition().getColumnIndex() + 1) * cellWidth;
        double y = (maze.getGoalPosition().getRowIndex() + 1) * cellHeight;

        graphicsContext.setFill(Color.GREEN);

        if (goalImage == null) { graphicsContext.fillRect(x, y, cellWidth, cellHeight); }
        else { graphicsContext.drawImage(goalImage, x - cellWidth * 0.3, y - cellHeight * 0.7, cellWidth * 1.5, cellHeight * 1.5); }
    }

    private void drawMazePlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth)
    {
        Image playerImage = null;
        try
        {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("There is no player image");
        }

        double x = (getPlayerCol() + 1) * cellWidth;
        double y = (getPlayerRow() + 1) * cellHeight;

        graphicsContext.setFill(Color.GREEN);

        if (playerImage == null) { graphicsContext.fillRect(x, y, cellWidth, cellHeight); }
        else
        {
            //graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
            graphicsContext.drawImage(playerImage, x - cellWidth * 0.3, y - cellHeight * 0.7, cellWidth * 1.5, cellHeight * 1.5);
        }
    }

    private void drawMazeTiles(GraphicsContext graphicsContext, int rows, int cols, double cellHeight, double cellWidth)
    {
        Image[] roadTiles = loadRoadTileImages();
        Image[] tiles = loadTileImages();

        graphicsContext.setFill(Color.RED);

        int[][] mazeTiles = maze.getMaze();
        Random rand = new Random(123456789);
        for (int i = 0; i < rows + 2; i++)
        {
            for (int j = 0; j < cols + 2; j++)
            {
                double x = j * cellWidth;
                double y = i * cellHeight;

                //if it is frame:
                if (i == 0 || i == rows + 1 || j == 0 || j == cols + 1)
                {
                    drawTheImage(tiles[10], graphicsContext, cellHeight, cellWidth, x, y);
                    continue;
                }

                // if it is a tile:
                if (maze.getMaze()[i - 1][j - 1] == 1)
                {
                    int variant = rand.nextInt(11);
                    drawTheImage(tiles[variant], graphicsContext, cellHeight, cellWidth, x, y);
                }

                //if its a road tile
                if (maze.getMaze()[i - 1][j - 1] == 0)
                {
                    boolean canMoveUp = false;
                    if (i != 1) canMoveUp = (mazeTiles[i - 2][j - 1] == 0);
                    boolean canMoveDown = false;
                    if (i != rows) canMoveDown = (mazeTiles[i][j - 1] == 0);
                    boolean canMoveLeft = false;
                    if (j != 1) canMoveLeft = (mazeTiles[i - 1][j - 2] == 0);
                    boolean canMoveRight = false;
                    if (j != cols) canMoveRight = (mazeTiles[i - 1][j] == 0);
                    if (canMoveLeft && !canMoveUp && canMoveRight && canMoveDown)
                    {
                        drawTheImage(roadTiles[0], graphicsContext, cellHeight, cellWidth, x, y);
                    }
                    else if (canMoveLeft && canMoveUp && !canMoveRight && canMoveDown)
                    {
                        drawTheImage(roadTiles[1], graphicsContext, cellHeight, cellWidth, x, y);
                    }
                    else if (!canMoveLeft && canMoveUp && canMoveRight && canMoveDown)
                    {
                        drawTheImage(roadTiles[2], graphicsContext, cellHeight, cellWidth, x, y);
                    }
                    else if (canMoveLeft && canMoveUp && canMoveRight && !canMoveDown)
                    {
                        drawTheImage(roadTiles[3], graphicsContext, cellHeight, cellWidth, x, y);
                    }
                    else if (!canMoveLeft && canMoveUp && !canMoveRight && canMoveDown)
                    {
                        int variant = rand.nextInt(2);
                        drawTheImage(roadTiles[4 + variant], graphicsContext, cellHeight, cellWidth, x, y);
                    }
                    else if (canMoveLeft && !canMoveUp && canMoveRight && !canMoveDown)
                    {
                        int variant = rand.nextInt(2);
                        drawTheImage(roadTiles[6 + variant], graphicsContext, cellHeight, cellWidth, x, y);
                    }
                    else if (canMoveLeft && canMoveUp && canMoveRight && canMoveDown)
                    {
                        drawTheImage(roadTiles[8], graphicsContext, cellHeight, cellWidth, x, y);
                    }
                    else if (!canMoveLeft && !canMoveUp && canMoveRight && canMoveDown)
                    {
                        drawTheImage(roadTiles[9], graphicsContext, cellHeight, cellWidth, x, y);
                    }
                    else if (canMoveLeft && !canMoveUp && !canMoveRight && canMoveDown)
                    {
                        drawTheImage(roadTiles[10], graphicsContext, cellHeight, cellWidth, x, y);
                    }
                    else if (!canMoveLeft && canMoveUp && canMoveRight && !canMoveDown)
                    {
                        drawTheImage(roadTiles[11], graphicsContext, cellHeight, cellWidth, x, y);
                    }
                    else if (canMoveLeft && canMoveUp && !canMoveRight && !canMoveDown)
                    {
                        drawTheImage(roadTiles[12], graphicsContext, cellHeight, cellWidth, x, y);
                    }
                    else if (canMoveLeft && !canMoveUp && !canMoveRight && !canMoveDown)
                    {
                        drawTheImage(roadTiles[13], graphicsContext, cellHeight, cellWidth, x, y);
                    }
                    else if (!canMoveLeft && canMoveUp && !canMoveRight && !canMoveDown)
                    {
                        drawTheImage(roadTiles[14], graphicsContext, cellHeight, cellWidth, x, y);
                    }
                    else if (!canMoveLeft && !canMoveUp && canMoveRight && !canMoveDown)
                    {
                        drawTheImage(roadTiles[15], graphicsContext, cellHeight, cellWidth, x, y);
                    }
                    else if (!canMoveLeft && !canMoveUp && !canMoveRight && canMoveDown)
                    {
                        drawTheImage(roadTiles[16], graphicsContext, cellHeight, cellWidth, x, y);
                    }
                }
            }
        }
    }

    public Image[] loadRoadTileImages()
    {
        Image[] pathTiles = new Image[17];
        try
        {

            for (int i = 0; i < 17; i++)
            {
                pathTiles[i] = new Image(new FileInputStream("./resources/MazeTiles/" + getTerrainType() + "/RoadTiles/" + getTerrainType() + "_01_tile_road_256_" + (i + 1) + ".png"));
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Road tile image not found");
        }
        return pathTiles;
    }

    public Image[] loadTileImages()
    {
        Image[] tiles = new Image[11];
        try
        {
            for (int i = 0; i < 11; i++)
            {
                tiles[i] = new Image(new FileInputStream("./resources/MazeTiles/" + getTerrainType() + "/Tiles/" + getTerrainType() + "_01_tile_256_" + (i + 1) + ".png"));
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Tile image not found");
        }
        return tiles;
    }

    private void drawTheImage(Image img, GraphicsContext graphicsContext, double cellHeight, double cellWidth, double x, double y)
    {
        if (img == null) { graphicsContext.fillRect(x, y, cellWidth, cellHeight); }
        else { graphicsContext.drawImage(img, x, y, cellWidth, cellHeight); }
    }

    public void setPlayerDirection(String direction)
    {
        if (playerDirection.get() != direction)
        {
            playerDirection.set(direction);
            switch (direction)
            {
                case "left", "right" -> imageFileNamePlayer.set("./resources/Character/" + playerCharacter.get() + "/active_" + direction + ".png");
                default -> System.out.println("Player " + direction + " direction image not found");
            }
            draw();
            playFlipSound(direction);
        }
    }

    private boolean isMoveValid(int row, int col)
    {
        try { return maze.getMaze()[row][col] == 0; }
        catch (Exception e) { return false; }
    }

    public void setGoalDirection()
    {
        String direction = "";
        if (isMoveValid(maze.getGoalPosition().getRowIndex(), maze.getGoalPosition().getColumnIndex() - 1) && !isMoveValid(maze.getGoalPosition().getRowIndex(), maze.getGoalPosition().getColumnIndex() + 1))
        {
            direction = "left";
        }
        else if (!isMoveValid(maze.getGoalPosition().getRowIndex(), maze.getGoalPosition().getColumnIndex() - 1) && isMoveValid(maze.getGoalPosition().getRowIndex(), maze.getGoalPosition().getColumnIndex() + 1))
        {
            direction = "right";
        }
        else if (maze.getGoalPosition().getColumnIndex() >= maze.getColSize() / 2) { direction = "left"; }
        else if (maze.getGoalPosition().getColumnIndex() < maze.getColSize() / 2) direction = "right";
        switch (direction)
        {
            case "left", "right" -> imageFileNameGoalPosition.set("./resources/Character/" + playerCharacter.get() + "/notActive_" + direction + ".png");
            default -> System.out.println("Player " + direction + " direction image not found");
        }
        System.out.println("Direction set to " + direction);
    }

    public void loadSounds()
    {
        for (int i = 0; i < 5; i++)
        {
            File soundClip = new File("./resources/Sound/Movement/" + terrainType.get() + "_Movement/" + terrainType.get() + "_footstep_run_" + (i + 1) + ".wav");
            moveSounds[i] = new Media(soundClip.toURI().toString());
        }
        for (int i = 0; i < 2; i++)
        {
            File soundClip = new File("./resources/Sound/Movement/Flip/whoosh_swish_small_" + (i + 1) + ".wav");
            flipSounds[i] = new Media(soundClip.toURI().toString());
        }
    }

    public void playMovementSound()
    {
        Random random = new Random();
        int variant = random.nextInt(5);
        MediaPlayer movementSoundPlayer = new MediaPlayer((moveSounds[variant]));
        movementSoundPlayer.setVolume(0.1);
        movementSoundPlayer.play();
    }

    private void playFlipSound(String direction)
    {
        Media flipSoundClip = null;
        switch (direction)
        {
            case "left" -> flipSoundClip = flipSounds[0];
            case "right" -> flipSoundClip = flipSounds[1];
            default -> System.out.println("Player " + direction + " direction image not found");
        }
        MediaPlayer movementSoundPlayer = new MediaPlayer(flipSoundClip);
        movementSoundPlayer.setVolume(0.5);
        movementSoundPlayer.play();
    }

    public void loadCharacters()
    {
        imageFileNamePlayer.set("./resources/Character/" + playerCharacter.get() + "/active_right.png");
        imageFileNameGoalPosition.set("./resources/Character/" + playerCharacter.get() + "/notActive_right.png");
    }
}
