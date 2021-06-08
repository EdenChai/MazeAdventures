package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    public IModel model;

    public MyViewModel(IModel model) {
        this.model = model;
        this.model.assignObserver(this); //Observe the MyModel for it's changes
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    public Maze getMaze(){
        return model.getMaze();
    }

    public int getPlayerRow(){
        return model.getCharacterRow();
    }

    public int getPlayerCol(){
        return model.getCharacterCol();
    }

    public Solution getSolution(){
        return model.getSolution();
    }

    public void generateMaze(int rows, int cols){
        model.generateMaze(rows, cols);
    }

    public void movePlayer(KeyCode keyCode)
    {
        model.updatePlayerLocation(keyCode);
    }

    public void solveMaze(){
        model.solveMaze();
    }

    public void setGenerateMazeConfiguration(String mazeGeneratingName) {model.setMazeGeneratingAlgorithm(mazeGeneratingName);}

    public void setSearchingAlgorithmConfiguration(String mazeSearchingAlgorithmName) {model.setMazeGeneratingAlgorithm(mazeSearchingAlgorithmName);}
}
