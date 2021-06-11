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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    /**************************Design***************************/
    public ImageView BackGround;
    public Button generateButt, saveButt, loadButt,returnToMainButt,exitButt,optionsButt,helpButt, solveButt;
    public ImageView[] generateButtonStates,saveButtonState,loadButtonStates,returnButtonStates,exitButtonStates,optionsButtonStates,helpButtonStates,solveButtonStates;

    public void configureButtons()
    {
        loadButtonGrapic();

        setButtonFunctions(exitButt,exitButtonStates);
        setButtonFunctions(generateButt,generateButtonStates);
        setButtonFunctions(loadButt,loadButtonStates);
        setButtonFunctions(saveButt,saveButtonState);
        setButtonFunctions(returnToMainButt,returnButtonStates);
        setButtonFunctions(optionsButt,optionsButtonStates);
        setButtonFunctions(helpButt,helpButtonStates);
        setButtonFunctions(solveButt,solveButtonStates);

    }

    public void setButtonFunctions(Button button, ImageView[] imageView)
    {
        button.setGraphic(imageView[0]);
        button.setOnMouseEntered(e -> {button.setGraphic(imageView[1]);
            Main.playButtonHoverSound();});
        button.setOnMouseExited(e -> button.setGraphic(imageView[0]));
        button.setOnMousePressed(e -> button.setGraphic(imageView[2]));
    }


    public void loadButtonGrapic()
    {
        generateButtonStates = new ImageView[3];
        loadButtonStates = new ImageView[3];
        saveButtonState = new ImageView[3];
        returnButtonStates = new ImageView[3];
        exitButtonStates = new ImageView[3];
        optionsButtonStates = new ImageView[3];
        helpButtonStates = new ImageView[3];
        solveButtonStates = new ImageView[3];
        exitButtonStates[0] = loadImageView("./resources/UI/Buttons/RedButtons/SmallRedButtons/RedExitButtonUnPressed.png", 100, 100, false, false);
        exitButtonStates[1] = loadImageView("./resources/UI/Buttons/RedButtons/SmallRedButtons/RedExitButtonHover.png", 100, 100, false, false);
        exitButtonStates[2] = loadImageView("./resources/UI/Buttons/RedButtons/SmallRedButtons/RedExitButtonPressed.png", 100, 100, false, false);
        generateButtonStates[0] = loadImageView("./resources/UI/Buttons/BlueButtons/BlueButtonGenerateMazeUnPressed.png", 200, 100, false, false);
        generateButtonStates[1] = loadImageView("./resources/UI/Buttons/BlueButtons/BlueButtonGenerateMazeHover.png", 200, 100, false, false);
        generateButtonStates[2] = loadImageView("./resources/UI/Buttons/BlueButtons/BlueButtonGenerateMazePressed.png", 200, 100, false, false);
        loadButtonStates[0] = loadImageView("./resources/UI/Buttons/GreenButtons/GreenLoadGameButtonUnPressed.png", 200, 100, false, false);
        loadButtonStates[1] = loadImageView("./resources/UI/Buttons/GreenButtons/GreenLoadGameHover.png", 200, 100, false, false);
        loadButtonStates[2] = loadImageView("./resources/UI/Buttons/GreenButtons/GreenLoadGameButtonPressed.png", 200, 100, false, false);
        saveButtonState[0] = loadImageView("./resources/UI/Buttons/GreenButtons/GreenSaveGameButtonUnPressed.png", 200, 100, false, false);
        saveButtonState[1] = loadImageView("./resources/UI/Buttons/GreenButtons/GreenSaveGameHover.png", 200, 100, false, false);
        saveButtonState[2] = loadImageView("./resources/UI/Buttons/GreenButtons/GreenSaveGameButtonPressed.png", 200, 100, false, false);
        returnButtonStates[0] = loadImageView("./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenReturnButtonUnPressed.png", 70, 70, false, false);
        returnButtonStates[1] = loadImageView("./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenReturnButtonHover.png", 70, 70, false, false);
        returnButtonStates[2] = loadImageView("./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenReturnButtonPressed.png", 70, 70, false, false);
        optionsButtonStates[0] = loadImageView("./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenSettingsButtonUnPressed.png", 70, 70, false, false);
        optionsButtonStates[1] = loadImageView("./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenSettingsButtonHover.png", 70, 70, false, false);
        optionsButtonStates[2] = loadImageView("./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenSettingsButtonPressed.png", 70, 70, false, false);
        helpButtonStates[0] = loadImageView("./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenHelpButtonUnPressed.png", 70, 70, false, false);
        helpButtonStates[1] = loadImageView("./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenHelpButtonHover.png", 70, 70, false, false);
        helpButtonStates[2] = loadImageView("./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenHelpButtonPressed.png", 70, 70, false, false);
        solveButtonStates[0] = loadImageView("./resources/UI/Buttons/GreenButtons/GreenShowSolutionButtonUnPressed.png", 200, 100, false, false);
        solveButtonStates[1] = loadImageView("./resources/UI/Buttons/GreenButtons/GreenShowSolutionHover.png", 200, 100, false, false);
        solveButtonStates[2] = loadImageView("./resources/UI/Buttons/GreenButtons/GreenShowSolutionButtonPressed.png", 200, 100, false, false);
    }

    public ImageView loadImageView(String path,double num1, double num2, boolean bool1, boolean bool2)
    {
        try
        {
            return new ImageView(new Image(new FileInputStream(path),num1,num2,bool1,bool2));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Button load failed");
            e.printStackTrace();
        }
        return null;
    }

    public ImageView loadImageView(String path)
    {
        try
        {
            return new ImageView(new Image(new FileInputStream(path)));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Button load failed");
            e.printStackTrace();
        }
        return null;
    }

    public void setBackGround(Stage stage)
    {
        BackGround.fitWidthProperty().bind(stage.widthProperty());
        BackGround.fitHeightProperty().bind(stage.heightProperty());
    }

    public void setBackGroundType(String type)
    {
        try
        {
            BackGround.setImage(new Image(new FileInputStream("./resources/UI/UI/GameStage_"+type+".png")));
        }
        catch (Exception e)
        {
            System.out.println("Background terrain not found");
        }
    }

    /*********************************************************/


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
        try
        {
            mazeDisplayer.solutionIsShowing = false;
            int rows = Integer.parseInt(textField_mazeRows.getText());
            int cols = Integer.parseInt(textField_mazeColumns.getText());
            if (rows <2 || rows >1000 || cols <2 || cols >100)
                throw new Exception();
            myViewModel.generateMaze(rows, cols);
        }
        catch (Exception e)
        {
            Main.showWarningMenu();
            return;
        }

    }

    public void solutionButton(ActionEvent actionEvent)
    {
        if (mazeDisplayer.solutionIsShowing)
        {
            mazeDisplayer.solutionIsShowing = false;
            mazeDisplayer.draw();
        }
        else
        {
            mazeDisplayer.solutionIsShowing = true;
            myViewModel.solveMaze();
        }
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
            case "maze solved" -> showSolution();
            case "loaded"-> updateRowsAndCols();
            case "player winning" -> playerWinning();

            default -> System.out.println("Not implemented change: " + change);
        }
    }

    private void playerWinning()
    {
        System.out.println("player winning");
        Main.goToWinMenu();
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

    private void showSolution()
    {
        mazeDisplayer.solution = Main.viewModel.model.getSolution();
        mazeDisplayer.drawSolution();
    }

    private void playerMoved()
    {
        setPlayerPosition(myViewModel.getPlayerRow(), myViewModel.getPlayerCol());
    }

    public void mazeGenerated()
    {
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
            this.myViewModel = viewModel;
            this.gameMenuScene = gameMenuScene;
            this.currentStage = currentStage;
            currentStage.setOnCloseRequest(new EventHandler<WindowEvent>()
            {
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
        if (saveFile != null)
        {
            myViewModel.saveGame(saveFile);
        }
    }


    public void helpButtClicked(ActionEvent actionEvent)
    {
        Main.goToHelpMenu();
    }

    public void optionsButtClicked(ActionEvent actionEvent)
    {
        Main.goToOptionsMenu();
    }

    public void returnLastSceneClicked(ActionEvent actionEvent)
    {
        Main.returnToMainMenu();
    }

    public void loadButtClicked(ActionEvent actionEvent) { Main.load(); }

    public void exitButtClicked(ActionEvent actionEvent)
    {
        Main.viewModel.model.shutDown();
        Platform.exit();
        System.exit(0);
    }

    public void setPlayerCharacter(String name)
    {
        mazeDisplayer.playerCharacter.set(name);
        mazeDisplayer.loadCharacters();
    }

    public void setTerrainType(String name)
    {
        mazeDisplayer.setTerrainType(name);
        mazeDisplayer.loadRoadTileImages();
        mazeDisplayer.loadRoadTileImages();
    }
}
