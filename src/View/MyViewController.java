package View;

import ViewModel.MyViewModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController implements Initializable, Observer
{
    public MyViewModel myViewModel;
    public Scene gameMenuScene;
    public Stage currentStage;
    public Pane pane;
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public MazeDisplayer mazeDisplayer;
    public ImageView colImage;
    public ImageView rowImage;
    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();

    /************************** Design ***************************/

    public ImageView BackGround;
    public Button generateButt, saveButt, loadButt, returnToMainButt, exitButt, optionsButt, helpButt, solveButt;
    public ImageView[] generateButtonStates, saveButtonState, loadButtonStates, returnButtonStates, exitButtonStates, optionsButtonStates, helpButtonStates, solveButtonStates;

    public void configureButtons()
    {
        loadButtonGraphic();
        Main.setButtonFunctions(exitButt, exitButtonStates);
        Main.setButtonFunctions(generateButt, generateButtonStates);
        Main.setButtonFunctions(loadButt, loadButtonStates);
        Main.setButtonFunctions(saveButt, saveButtonState);
        Main.setButtonFunctions(returnToMainButt, returnButtonStates);
        Main.setButtonFunctions(optionsButt, optionsButtonStates);
        Main.setButtonFunctions(helpButt, helpButtonStates);
        Main.setButtonFunctions(solveButt, solveButtonStates);
    }

    public void loadButtonGraphic()
    {
        generateButtonStates = new ImageView[3];
        loadButtonStates = new ImageView[3];
        saveButtonState = new ImageView[3];
        returnButtonStates = new ImageView[3];
        exitButtonStates = new ImageView[3];
        optionsButtonStates = new ImageView[3];
        helpButtonStates = new ImageView[3];
        solveButtonStates = new ImageView[3];

//        Main.loadExitAndReturn(exitButtonStates, returnButtonStates, exitButt, returnToMainButt);
        String path1 = "./resources/UI/Buttons/RedButtons/SmallRedButtons/RedExitButtonUnPressed.png";
        String path2 = "./resources/UI/Buttons/RedButtons/SmallRedButtons/RedExitButtonHover.png";
        String path3 = "./resources/UI/Buttons/RedButtons/SmallRedButtons/RedExitButtonPressed.png";
        Main.loadButtonGraphics(path1, path2, path3, exitButtonStates, exitButt, 100, 100, false, false);
        path1 = "./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenReturnButtonUnPressed.png";
        path2 = "./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenReturnButtonHover.png";
        path3 = "./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenReturnButtonPressed.png";
        Main.loadButtonGraphics(path1, path2, path3, returnButtonStates, returnToMainButt, 70, 70, false, false);

        path1 = "./resources/UI/Buttons/BlueButtons/BlueButtonGenerateMazeUnPressed.png";
        path2 = "./resources/UI/Buttons/BlueButtons/BlueButtonGenerateMazeHover.png";
        path3 = "./resources/UI/Buttons/BlueButtons/BlueButtonGenerateMazePressed.png";
        Main.loadButtonGraphics(path1, path2, path3, generateButtonStates, generateButt, 200, 100, false, false);

        path1 = "./resources/UI/Buttons/GreenButtons/GreenLoadGameButtonUnPressed.png";
        path2 = "./resources/UI/Buttons/GreenButtons/GreenLoadGameHover.png";
        path3 = "./resources/UI/Buttons/GreenButtons/GreenLoadGameButtonPressed.png";
        Main.loadButtonGraphics(path1, path2, path3, loadButtonStates, loadButt, 200, 100, false, false);

        path1 = "./resources/UI/Buttons/GreenButtons/GreenSaveGameButtonUnPressed.png";
        path2 = "./resources/UI/Buttons/GreenButtons/GreenSaveGameHover.png";
        path3 = "./resources/UI/Buttons/GreenButtons/GreenSaveGameButtonPressed.png";
        Main.loadButtonGraphics(path1, path2, path3, saveButtonState, saveButt, 200, 100, false, false);

//        Main.loadHelpAndOptions(helpButtonStates, optionsButtonStates, helpButt, optionsButt);
        path1 = "./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenSettingsButtonUnPressed.png";
        path2 = "./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenSettingsButtonHover.png";
        path3 = "./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenSettingsButtonPressed.png";
        Main.loadButtonGraphics(path1, path2, path3, optionsButtonStates, optionsButt, 70, 70, false, false);

        path1 = "./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenHelpButtonUnPressed.png";
        path2 = "./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenHelpButtonHover.png";
        path3 = "./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenHelpButtonPressed.png";
        Main.loadButtonGraphics(path1, path2, path3, helpButtonStates, helpButt, 70, 70, false, false);

        path1 = "./resources/UI/Buttons/GreenButtons/GreenShowSolutionButtonUnPressed.png";
        path2 = "./resources/UI/Buttons/GreenButtons/GreenShowSolutionHover.png";
        path3 = "./resources/UI/Buttons/GreenButtons/GreenShowSolutionButtonPressed.png";
        Main.loadButtonGraphics(path1, path2, path3, solveButtonStates, solveButt, 200, 100, false, false);
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
            BackGround.setImage(new Image(new FileInputStream("./resources/UI/UI/GameStage_" + type + ".png")));
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
            if (rows < 2 || rows > 1000 || cols < 2 || cols > 1000) throw new Exception();
            myViewModel.generateMaze(rows, cols);
            mazeDisplayer.requestFocus();
        }
        catch (Exception e)
        {
            Main.showWarningMenu();
        }
    }

    public void showSolutionClicked(ActionEvent actionEvent)
    {
        if (myViewModel.getMaze() == null)
        {
            Main.playButtonExitSound();
            return;
        }

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
        mazeDisplayer.requestFocus();
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
            case "player looked to the left" -> playerLooked("left");
            case "player looked to the right" -> playerLooked("right");
            case "maze solved" -> showSolution();
            case "loaded" -> updateRowsAndCols();
            case "player winning" -> playerWinning();

            default -> System.out.println("Not implemented change: " + change);
        }
    }

    private void playerWinning()
    {
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
        mazeDisplayer.requestFocus();
    }

    public void configure(MyViewModel viewModel, Scene gameMenuScene, Stage currentStage)
    {
        textField_mazeRows.clear();
        textField_mazeColumns.clear();
        if (viewModel != null && gameMenuScene != null && currentStage != null)
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

    public void SaveMazeClicked(ActionEvent actionEvent)
    {
        if (myViewModel.getMaze() == null)
        {
            Main.playButtonExitSound();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Maze Files", "*.maze"));
        fileChooser.setInitialFileName("myMaze");
        File saveFile = fileChooser.showSaveDialog(currentStage);
        if (saveFile != null)
        {
            myViewModel.saveGame(saveFile);
            mazeDisplayer.requestFocus();
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

    public String getPlayerCharacter()
    {
        return mazeDisplayer.playerCharacter.get();
    }

    public void setTerrainType(String name)
    {
        mazeDisplayer.setTerrainType(name);
        mazeDisplayer.loadRoadTileImages();
        mazeDisplayer.loadRoadTileImages();
    }

    public void creditsButtClicked(ActionEvent actionEvent)
    {
        Main.goToCreditsMenu();
    }

    public void newButtClicked(ActionEvent actionEvent)
    {
        mazeGenerated();
    }

    public void setResizeEvent(Scene scene)
    {
        mazeDisplayer.widthProperty().bind(pane.widthProperty());
        mazeDisplayer.heightProperty().bind(pane.heightProperty());
        scene.widthProperty().addListener((observable, oldValue, newValue) -> mazeDisplayer.widthProperty().bind(pane.widthProperty()));
        scene.heightProperty().addListener((observable, oldValue, newValue) -> mazeDisplayer.heightProperty().bind(pane.heightProperty()));
    }

    // Zoom-in
    public void setOnScroll(ScrollEvent scroll)
    {
        if (scroll.isControlDown())
        {
            double zoom_fac = 1.05;
            if (scroll.getDeltaY() < 0)
            {
                zoom_fac = 2.0 - zoom_fac;
            }
            Scale newScale = new Scale();
            newScale.setPivotX(scroll.getX());
            newScale.setPivotY(scroll.getY());
            newScale.setX(mazeDisplayer.getScaleX() * zoom_fac);
            newScale.setY(mazeDisplayer.getScaleY() * zoom_fac);
            mazeDisplayer.getTransforms().add(newScale);
            scroll.consume();
        }
    }

//        /**
//         * handler for onMouseDragged(dragging the player) inside the Maze
//         */
//        public void mouseDragged(MouseEvent mouseEvent)
//        {
//            if (myViewModel.getMaze() != null)
//            {
//                int maximumSize = Math.max(myViewModel.getMaze().getRowSize(), myViewModel.getMaze().getColSize());
//                double mousePosX = helperMouseDragged(maximumSize, mazeDisplayer.getHeight(), myViewModel.getMaze().getRowSize(), mouseEvent.getX(), mazeDisplayer.getWidth() / maximumSize);
//                double mousePosY = helperMouseDragged(maximumSize, mazeDisplayer.getWidth(), myViewModel.getMaze().getColSize(), mouseEvent.getY(), mazeDisplayer.getHeight() / maximumSize);
//
//                if (mousePosX == myViewModel.getPlayerCol() && mousePosY < myViewModel.getPlayerRow()) { myViewModel.movePlayer(KeyCode.NUMPAD8); }
//                else if (mousePosY == myViewModel.getPlayerRow() && mousePosX > myViewModel.getPlayerCol()) { myViewModel.movePlayer(KeyCode.NUMPAD6); }
//                else if (mousePosY == myViewModel.getPlayerRow() && mousePosX < myViewModel.getPlayerCol()) { myViewModel.movePlayer(KeyCode.NUMPAD4); }
//                else if (mousePosX == myViewModel.getPlayerCol() && mousePosY > myViewModel.getPlayerRow()) myViewModel.movePlayer(KeyCode.NUMPAD2);
//            }
//        }
//
//        private double helperMouseDragged(int maxsize, double canvasSize, int mazeSize, double mouseEvent, double temp)
//        {
//            double cellSize = canvasSize / maxsize;
//            double start = (canvasSize / 2 - (cellSize * mazeSize / 2)) / cellSize;
//            int mouse = (int) ((mouseEvent) / (temp) - start);
//            return mouse;
//        }

    public void mouseDragged(MouseEvent mouseEvent)
    {

        if (mazeDisplayer != null)
        {
            int maxSize = Math.max(myViewModel.getMaze().getColSize(), myViewModel.getMaze().getRowSize());
            double cellHeight = mazeDisplayer.getHeight() / (maxSize + 2);
            double cellWidth = mazeDisplayer.getWidth() / (maxSize + 2);
            double canvasHeight = mazeDisplayer.getHeight();
            double canvasWidth = mazeDisplayer.getWidth();
            int rowMazeSize = myViewModel.getMaze().getRowSize();
            int colMazeSize = myViewModel.getMaze().getColSize();
            double startRow = (canvasHeight / 2 - (cellHeight * rowMazeSize / 2)) / cellHeight;
            double startCol = (canvasWidth / 2 - (cellWidth * colMazeSize / 2)) / cellWidth;
            //            double mouseX = 1 + (int) ((mouseEvent.getX() + startRow ) / (mazeDisplayer.getWidth()  / maxSize));
            //            double mouseY = 1 + (int) ((mouseEvent.getY() + startCol ) / (mazeDisplayer.getHeight() / maxSize));
            double mouseX = (int) ((mouseEvent.getX()) / cellWidth - startCol);
            double mouseY = (int) ((mouseEvent.getY()) / cellHeight - startRow);
            System.out.println("MouseX = " + mouseX);
            System.out.println("MouseY = " + mouseY + "\n");
            //            if (!(myViewModel.getPlayerRow() == )
            //            {
            if (mouseY < myViewModel.getPlayerRow() && mouseX == myViewModel.getPlayerCol()) myViewModel.movePlayer(KeyCode.UP);

            if (mouseY > myViewModel.getPlayerRow() && mouseX == myViewModel.getPlayerCol()) myViewModel.movePlayer(KeyCode.DOWN);

            if (mouseX < myViewModel.getPlayerCol() && mouseY == myViewModel.getPlayerRow()) myViewModel.movePlayer(KeyCode.LEFT);

            if (mouseX > myViewModel.getPlayerCol() && mouseY == myViewModel.getPlayerRow()) myViewModel.movePlayer(KeyCode.RIGHT);
            //            }
        }
    }
}
