package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application {

    public static MyModel model;
    public static MyViewModel viewModel;
    public static MainMenuControl mainMenuControl;
    public static CreditsMenuControl creditsMenuControl;
    public static HelpMenuControl helpMenuControl;
    public static OptionsMenuControl optionsMenuControl;
    public static MyViewController myViewController;
    public static WarningMenuControl warningMenuControl;
    public static WinMenuControl winMenuControl;
    public static FXMLLoader mainMenuLoader,creditsMenuLoader,helpMenuLoader,optionsMenuLoader,gameMenuLoader,warningMenuLoader, winMenuLoader;
    private static Scene mainMenuScene,helpMenuScene,creditsMenuScene,optionsMenuScene,gameMenuScene,warningMenuScene, lastScene, winMenuScene;
    public static Stage optimalStage, warningStage, borderStage, currentStage;
    public Parent mainMenuStructure,creditsMenuStructure,helpMenuStructure,optionsMenuStructure,gameMenuStructure,warningMenuStructure,winMenuStructure;
    public static MediaPlayer mainMenuPlayer;



    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //Initialize the current stage
        optimalStage = primaryStage;
        borderStage = new Stage();
        optimalStage.getIcons().add(new Image(new FileInputStream("./resources/UI/UI/TomAndJerryIcon.png")));
        borderStage.getIcons().add(new Image(new FileInputStream("./resources/UI/UI/TomAndJerryIcon.png")));
        optimalStage.initStyle(StageStyle.TRANSPARENT);
        borderStage.initStyle(StageStyle.DECORATED);
        currentStage = optimalStage;


        //Load loaders
        mainMenuLoader = new FXMLLoader(getClass().getResource("../View/MainMenuStructure.fxml"));
        creditsMenuLoader = new FXMLLoader(getClass().getResource("../View/CreditsMenuStructure.fxml"));
        helpMenuLoader = new FXMLLoader(getClass().getResource("../View/HelpMenuStructure.fxml"));
        optionsMenuLoader = new FXMLLoader(getClass().getResource("../View/OptionsMenuStructure.fxml"));
        gameMenuLoader = new FXMLLoader(getClass().getResource("../View/MazeDisplayerStructure.fxml"));
        warningMenuLoader = new FXMLLoader(getClass().getResource("../View/WarningMenuStructure.fxml"));
        winMenuLoader = new FXMLLoader(getClass().getResource("../View/WinMenuStructure.fxml"));

        //Load FXML
        mainMenuStructure = mainMenuLoader.load();
        creditsMenuStructure = creditsMenuLoader .load();
        helpMenuStructure =helpMenuLoader.load();
        optionsMenuStructure = optionsMenuLoader.load();
        gameMenuStructure = gameMenuLoader.load();
        warningMenuStructure = warningMenuLoader.load();
        winMenuStructure = winMenuLoader.load();

        //Load controllers
        mainMenuControl = mainMenuLoader.getController();
        creditsMenuControl= creditsMenuLoader.getController();
        helpMenuControl= helpMenuLoader.getController();
        optionsMenuControl= optionsMenuLoader.getController();
        myViewController = gameMenuLoader.getController();
        warningMenuControl = warningMenuLoader.getController();
        winMenuControl = winMenuLoader.getController();


        //Load scenes
        creditsMenuScene = new Scene(creditsMenuStructure,899,952);
        helpMenuScene = new Scene(helpMenuStructure,899,952);
        optionsMenuScene = new Scene(optionsMenuStructure,899,952);
        mainMenuScene = new Scene(mainMenuLoader.getRoot(),899,952);
        gameMenuScene = new Scene(gameMenuStructure,899,952);
        warningMenuScene = new Scene(warningMenuStructure,790,344);
        winMenuScene = new Scene(winMenuStructure,899,952);

        //Set Sences background
        creditsMenuScene.setFill(Color.TRANSPARENT);
        helpMenuScene .setFill(Color.TRANSPARENT);
        optionsMenuScene.setFill(Color.TRANSPARENT);
        mainMenuScene .setFill(Color.TRANSPARENT);
        gameMenuScene.setFill(Color.TRANSPARENT);
        warningMenuScene.setFill(Color.TRANSPARENT);
        winMenuScene.setFill(Color.TRANSPARENT);

        //Load the game stage control
        FXMLLoader mainMenuLoader = new FXMLLoader(getClass().getResource("../View/MainMenuStructure.fxml"));
        mainMenuLoader.load();



        //Load the models
        model = new MyModel();
        viewModel = new MyViewModel(model);
        model.addObserver(viewModel);

        //Load the music
        File soundClip = new File("./resources/Sound/Menu_Stage/Funky_Chill_2_loop.wav");
        Media sound = new Media(soundClip.toURI().toString());
        mainMenuPlayer = new MediaPlayer(sound);
        mainMenuPlayer.setVolume(0.04);
        mainMenuPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mainMenuPlayer.play();

        configureControllers();

        //Load the main menu
        lastScene =mainMenuScene;
        optimalStage.setScene(mainMenuScene);
        borderStage.setScene(gameMenuScene);


        //Load the warning menu
        warningStage = new Stage();
        warningStage.setScene(warningMenuScene);
        warningStage.initModality(Modality.APPLICATION_MODAL);
        warningStage.setResizable(false);
        warningStage.setAlwaysOnTop(true);
        warningStage.initStyle(StageStyle.TRANSPARENT);

        //Calibrate all scene backgrounds
        mainMenuControl.setBackGround(currentStage);
        creditsMenuControl.setBackGround(currentStage);
        helpMenuControl.setBackGround(currentStage);
        optionsMenuControl.setBackGround(currentStage);
        myViewController.setBackGround(borderStage);
        winMenuControl.setBackGround(currentStage);



        optimalStage.setResizable(false);
        borderStage.setResizable(true);
        optimalStage.show();

    }

    public static void configureControllers()
    {
        optionsMenuControl.configureButtons();
        mainMenuControl.configureButtons();
        creditsMenuControl.configureButtons();
        helpMenuControl.configureButtons();
        myViewController.configureButtons();
        warningMenuControl.configureButtons();
        winMenuControl.configureButtons();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void returnToMainMenu()
    {
        lastScene = mainMenuScene;
        borderStage.hide();
        optimalStage.show();
        playButtonClickSound();
        optimalStage.setScene(mainMenuScene);
    }

    public static void returnToMainMenuFromWin()
    {
       playButtonClickSound();
       currentStage.setScene(mainMenuScene);
    }

    public static void returnToLastScene()
    {
        if (lastScene == gameMenuScene)
        {
            optimalStage.hide();
            borderStage.show();
            borderStage.setScene(lastScene);
        }
        else
        {
            optimalStage.show();
            borderStage.hide();
            optimalStage.setScene(lastScene);
        }
    }

    public static void showWarningMenu()
    {
        playButtonExitSound();
        warningStage.showAndWait();
    }

    public static void exitWarningMenu()
    {
        playButtonClickSound();
        warningStage.hide();
    }

    public static void goToCreditsMenu()
    {
        borderStage.hide();
        optimalStage.show();
        playButtonClickSound();
        optimalStage.setScene(creditsMenuScene);
    }

    public static void goToWinMenu()
    {
        borderStage.hide();
        optimalStage.show();
        currentStage.setScene(winMenuScene);
        //playWinSoundInto();
        //playWinSoundLoop();
    }


    public static void goToHelpMenu()
    {
        borderStage.hide();
        optimalStage.show();
        playButtonClickSound();
        optimalStage.setScene(helpMenuScene);
    }

    public static void goToOptionsMenu()
    {
        borderStage.hide();
        optimalStage.show();
        playButtonClickSound();
        optimalStage.setScene(optionsMenuScene);
    }

    public static Stage getOptimalStage()
    {
        return optimalStage;
    }

    public static void goToGameMenu()
    {
        lastScene = gameMenuScene;
        optimalStage.hide();
        borderStage.show();
        myViewController.configure(viewModel,gameMenuScene,borderStage);
        viewModel.addObserver(myViewController);
        playButtonClickSound();
        borderStage.setScene(gameMenuScene);
    }

    public static void playButtonHoverSound()
    {
        File soundClip = new File("./resources/Sound/Buttons/ui_menu_button_beep_16.wav");
        Media hoverSoundClip = new Media(soundClip.toURI().toString());
        MediaPlayer movementSoundPlayer = new MediaPlayer(hoverSoundClip);
        movementSoundPlayer.setVolume(0.1);
        movementSoundPlayer.play();
    }

    public static void playButtonClickSound()
    {
        File soundClip = new File("./resources/Sound/Buttons/ui_menu_button_click_22.wav");
        Media hoverSoundClip = new Media(soundClip.toURI().toString());
        MediaPlayer movementSoundPlayer = new MediaPlayer(hoverSoundClip);
        movementSoundPlayer.setVolume(0.1);
        movementSoundPlayer.play();
    }

    public static void playButtonAcceptSound()
    {
        File soundClip = new File("./resources/Sound/Buttons/ui_menu_button_confirm_01.wav");
        Media hoverSoundClip = new Media(soundClip.toURI().toString());
        MediaPlayer movementSoundPlayer = new MediaPlayer(hoverSoundClip);
        movementSoundPlayer.setVolume(0.1);
        movementSoundPlayer.play();
    }

    public static void playButtonExitSound()
    {
        File soundClip = new File("./resources/Sound/Buttons/ui_menu_button_cancel_02.wav");
        Media hoverSoundClip = new Media(soundClip.toURI().toString());
        MediaPlayer movementSoundPlayer = new MediaPlayer(hoverSoundClip);
        movementSoundPlayer.setVolume(0.1);
        movementSoundPlayer.play();
    }

    public static void loadButtonGraphicsExitAndReturn(ImageView[] exitButtonStates, ImageView[] returnButtonStates, Button exitButton, Button returnButton)
    {
        try
        {
            exitButtonStates[0] = new ImageView(new Image(new FileInputStream("./resources/UI/Buttons/RedButtons/SmallRedButtons/RedExitButtonUnPressed.png"),100,100,false,false));
            exitButtonStates[1] = new ImageView(new Image(new FileInputStream("./resources/UI/Buttons/RedButtons/SmallRedButtons/RedExitButtonHover.png"),100,100,false,false));
            exitButtonStates[2] = new ImageView(new Image(new FileInputStream("./resources/UI/Buttons/RedButtons/SmallRedButtons/RedExitButtonPressed.png"),100,100,false,false));
            returnButtonStates[0] = new ImageView(new Image(new FileInputStream("./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenReturnButtonUnPressed.png"),100,100,false,false));
            returnButtonStates[1] = new ImageView(new Image(new FileInputStream("./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenReturnButtonHover.png"),100,100,false,false));
            returnButtonStates[2] = new ImageView(new Image(new FileInputStream("./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenReturnButtonPressed.png"),100,100,false,false));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Button load failed");
            e.printStackTrace();
        }

        exitButton.setGraphic(exitButtonStates[0]);
        exitButton.setOnMouseEntered(e -> {exitButton.setGraphic(exitButtonStates[1]);
            Main.playButtonHoverSound();});
        exitButton.setOnMouseExited(e -> exitButton.setGraphic(exitButtonStates[0]));
        exitButton.setOnMousePressed(e -> exitButton.setGraphic(exitButtonStates[2]));
        returnButton.setGraphic(returnButtonStates[0]);
        returnButton.setOnMouseEntered(e -> {returnButton.setGraphic(returnButtonStates[1]);
            Main.playButtonHoverSound();});
        returnButton.setOnMouseExited(e -> returnButton.setGraphic(returnButtonStates[0]));
        returnButton.setOnMousePressed(e -> returnButton.setGraphic(returnButtonStates[2]));
    }

    @Override
    public void stop() throws Exception
    {
        System.out.println("Game is shutting down");
        Main.viewModel.model.shutDown();
    }

    public static void load()
    {
        try
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Maze Files", "*.maze"));
            File loadFile = fileChooser.showOpenDialog(optimalStage);
            if (loadFile != null)
            {
                lastScene = mainMenuScene;
                Main.goToGameMenu();
                viewModel.loadGame(loadFile);
                myViewController.mazeGenerated();
            }
        }
        catch (Exception e)
        {

        }

    }
}
