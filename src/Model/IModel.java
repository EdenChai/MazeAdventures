package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.File;

import java.util.Observer;

public interface IModel
{
    Maze getMaze();
    int getCharacterRow();
    int getCharacterCol();
    Solution getSolution();
    void setMazeGeneratingAlgorithm(String generatingAlgorithm);
    String getMazeGeneratingAlgorithm();
    String getMazeSearchingAlgorithm();
    void setMazeSearchingAlgorithm(String searchingAlgorithm);
    void generateMaze(int rows, int cols);
    void updatePlayerLocation(KeyCode keyCode);
    void solveMaze();
    void assignObserver(Observer o);
    void shutDown();
    void loadMaze(File file);
    void saveMaze(File file);
}
