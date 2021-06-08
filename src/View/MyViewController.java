package View;

import Model.MazeGenerator;
import ViewModel.MyViewModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController implements Initializable, Observer
{
    public MyViewModel myViewModel;
    public Scene gameMenuScene;
    public Stage currentStage;

    public void setViewModel(MyViewModel myViewModel)
    {
        this.myViewModel = myViewModel;
        this.myViewModel.addObserver(this);
    }

    private MazeGenerator generator;
    public Label lbl_playerRow;
    public Label lbl_playerCol;
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public MazeDisplayer mazeDisplayer;
    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        lbl_playerRow.textProperty().bind(updatePlayerRow);
        lbl_playerCol.textProperty().bind(updatePlayerCol);
    }

    public String getUpdatePlayerRow()
    {
        return updatePlayerRow.get();
    }

    public void setUpdatePlayerRow(int row)
    {
        this.updatePlayerRow.set("" + row);
    }

    public String getUpdatePlayerCol()
    {
        return updatePlayerCol.get();
    }

    public void setUpdatePlayerCol(int col)
    {
        this.updatePlayerCol.set("" + col);
    }

    public void generateMaze(ActionEvent actionEvent)
    {
        int rows = Integer.parseInt(textField_mazeRows.getText());
        int cols = Integer.parseInt(textField_mazeColumns.getText());

        myViewModel.generateMaze(rows, cols);
    }

    public void solveMaze(ActionEvent actionEvent)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Solving maze...");
        alert.show();
        myViewModel.solveMaze();
    }

    public void openFile(ActionEvent actionEvent)
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
        fc.setInitialDirectory(new File("./resources"));
        File chosen = fc.showOpenDialog(null);
        //...
    }

    public void keyPressed(KeyEvent keyEvent)
    {
        myViewModel.movePlayer(keyEvent.getCode());
        keyEvent.consume();
    }

    public void setPlayerPosition(int row, int col)
    {
        mazeDisplayer.setPlayerPosition(row, col);
        mazeDisplayer.playMovementSound();
        setUpdatePlayerRow(row);
        setUpdatePlayerCol(col);
    }
    public void setPlayerDirection(String direction)
    {
        mazeDisplayer.setPlayerDirection(direction);
    }

    public void mouseClick(MouseEvent mouseEvent)
    {
        mazeDisplayer.requestFocus();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        String change = (String) arg;
        switch (change)
        {
            case "maze generated" -> mazeGenerated();
            case "player moved" -> playerMoved();
            case "player looked to the left" ->playerLooked("left");
            case "player looked to the right" ->playerLooked("right");
            case "maze solved" -> mazeSolved();
            case "loaded"-> updateRowsAndCols();

            default -> System.out.println("Not implemented change: " + change);
        }
    }

    private void updateRowsAndCols()
    {
        updatePlayerRow.set(myViewModel.model.getCharacterRow() + "");
        updatePlayerCol.set(myViewModel.model.getCharacterCol() + "");
        mazeDisplayer.setPlayerPosition(myViewModel.model.getCharacterRow(), myViewModel.model.getCharacterCol());
        setUpdatePlayerRow(myViewModel.model.getCharacterRow());
        setUpdatePlayerCol(myViewModel.model.getCharacterCol());
    }

    private void playerLooked(String direction)
    {
        setPlayerDirection(direction);
    }

    private void mazeSolved()
    {
        mazeDisplayer.setSolution(myViewModel.getSolution());
    }

    private void playerMoved()
    {
        setPlayerPosition(myViewModel.getPlayerRow(), myViewModel.getPlayerCol());
    }



    public void mazeGenerated()
    {
        mazeDisplayer.playerCharacter.set("Tom"); //TODO - change this in options menu
        mazeDisplayer.setTerrainType("grass"); //TODO - set the terrain type from the options menu (possible)
        mazeDisplayer.loadCharacters();
        mazeDisplayer.drawMaze(myViewModel.getMaze());
        mazeDisplayer.setGoalDirection();
        mazeDisplayer.loadSounds();

    }

    public void configure(MyViewModel viewModel, Scene gameMenuScene, Stage currentStage)
    {
        textField_mazeRows.clear();
        textField_mazeColumns.clear();
        if(viewModel !=null && gameMenuScene!= null && currentStage != null)
        {
            this.myViewModel= viewModel;
            this.gameMenuScene = gameMenuScene;
            this.currentStage=currentStage;
            currentStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
                @Override
                public void handle(WindowEvent e)
                {
                    viewModel.model.shutDown();
                    Platform.exit();
                    System.exit(0);
                }
            });
        }
    }

    public void SaveMaze(ActionEvent actionEvent) throws FileNotFoundException
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Maze Files", "*.maze")
        );
        fileChooser.setInitialFileName("myMaze");
        File saveFile = fileChooser.showSaveDialog(currentStage);
        if (saveFile != null) {
            myViewModel.saveGame(saveFile);
        }
    }
}
