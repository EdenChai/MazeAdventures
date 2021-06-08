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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {

    public static MyModel model;
    public static MyViewModel viewModel;
    public static MainMenuControl mainMenuControl;
    public static CreditsMenuControl creditsMenuControl;
    public static HelpMenuControl helpMenuControl;
    public static OptionsMenuControl optionsMenuControl;
    public static MyViewController myViewController;
    public static FXMLLoader mainMenuLoader,creditsMenuLoader,helpMenuLoader,optionsMenuLoader,gameMenuLoader;
    private static Scene mainMenuScene,helpMenuScene,creditsMenuScene,optionsMenuScene,gameMenuScene;
    public static Stage currentStage;
    public Parent mainMenuStructure,creditsMenuStructure,helpMenuStructure,optionsMenuStructure,gameMenuStructure;
    public static MediaPlayer mainMenuPlayer;


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //Initialize the current stage
        currentStage = primaryStage;
        currentStage.getIcons().add(new Image(new FileInputStream("./resources/UI/UI/TomAndJerryIcon.png")));
        currentStage.initStyle(StageStyle.DECORATED);

        //Load loaders
        mainMenuLoader = new FXMLLoader(getClass().getResource("../View/MainMenuStructure.fxml"));
        creditsMenuLoader = new FXMLLoader(getClass().getResource("../View/CreditsMenuStructure.fxml"));
        helpMenuLoader = new FXMLLoader(getClass().getResource("../View/HelpMenuStructure.fxml"));
        optionsMenuLoader = new FXMLLoader(getClass().getResource("../View/OptionsMenuStructure.fxml"));
        gameMenuLoader = new FXMLLoader(getClass().getResource("../View/MazeDisplayerStructure.fxml"));

        //Load FXML
        mainMenuStructure = mainMenuLoader.load();
        creditsMenuStructure = creditsMenuLoader .load();
        helpMenuStructure =helpMenuLoader.load();
        optionsMenuStructure = optionsMenuLoader.load();
        gameMenuStructure = gameMenuLoader.load();

        //Load controllers
        mainMenuControl = mainMenuLoader.getController();
        creditsMenuControl= creditsMenuLoader.getController();
        helpMenuControl= helpMenuLoader.getController();
        optionsMenuControl= optionsMenuLoader.getController();
        myViewController = gameMenuLoader.getController();


        //Load scenes
        creditsMenuScene = new Scene(creditsMenuStructure,899,952);
        helpMenuScene = new Scene(helpMenuStructure,899,952);
        optionsMenuScene = new Scene(optionsMenuStructure,899,952);
        mainMenuScene = new Scene(mainMenuLoader.getRoot(),899,952);
        gameMenuScene = new Scene(gameMenuStructure,899,952);

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
        currentStage.setScene(mainMenuScene);


        //Calibrate all scene backgrounds
        mainMenuControl.setBackGround(currentStage);
        creditsMenuControl.setBackGround(currentStage);
        helpMenuControl.setBackGround(currentStage);
        optionsMenuControl.setBackGround(currentStage);

        currentStage.setResizable(true);
        currentStage.show();

        //currentStage.setScene(creditsMenuScene);

/*
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MazeDisplayerStructure.fxml")); // Original Maze Stage
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();

        model = new MyModel();
        viewModel = new MyViewModel(model);
        model.addObserver(viewModel);
//        IModel model = new MyModel();
//        MyViewModel myViewModel = new MyViewModel(model);
        MyViewController myViewController = fxmlLoader.getController();
        myViewController.setViewModel(viewModel);

 */




    }

    public static void configureControllers()
    {
        optionsMenuControl.configureButtons();
        mainMenuControl.configureButtons();
        creditsMenuControl.configureButtons();
        helpMenuControl.configureButtons();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void returnToMainMenu()
    {
        playButtonClickSound();
        currentStage.setScene(mainMenuScene);
    }

    public static void goToCreditsMenu()
    {
        playButtonClickSound();
        currentStage.setScene(creditsMenuScene);
    }

    public static void goToHelpMenu()
    {
        playButtonClickSound();
        currentStage.setScene(helpMenuScene);
    }

    public static void goToOptionsMenu()
    {
        playButtonClickSound();
        currentStage.setScene(optionsMenuScene);
    }

    public static Stage getCurrentStage()
    {
        return currentStage;
    }

    public static void goToGameMenu()
    {
        myViewController.configure(viewModel,gameMenuScene,currentStage);
        viewModel.addObserver(myViewController);
        playButtonClickSound();
        currentStage.setScene(gameMenuScene);
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
            File loadFile = fileChooser.showOpenDialog(currentStage);
            if (loadFile != null)
            {
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
