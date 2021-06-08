package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.File;

import java.util.Observer;

public interface IModel
{
    void generateMaze(int rows, int cols);
    Maze getMaze();
    void updatePlayerLocation(KeyCode keyCode);

    int getCharacterRow();
    int getCharacterCol();
    void solveMaze();
    Solution getSolution();


    void assignObserver(Observer o);

    void shutDown();

    void setMazeGeneratingAlgorithm(String generatingAlgorithm);

    String getMazeGeneratingAlgorithm();

    String getMazeSearchingAlgorithm();

    void setMazeSearchingAlgorithm(String searchingAlgorithm);

    void loadMaze(File file);

    void saveMaze(File file);
}
