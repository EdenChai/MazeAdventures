package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;

public class Main extends Application {

    public static MyModel model;
    public static MyViewModel viewModel;
    public static MainMenuControl mainMenuControl;
    private static Scene mainMenuScene,helpMenuScene,creditsMenuScene,optionMenuScene;
    public static Stage currentStage;
    public Parent creditsMenuStructure;
    public static MediaPlayer mainMenuPlayer;


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //Initialize the current stage
        currentStage = primaryStage;
        currentStage.getIcons().add(new Image(new FileInputStream("./resources/UI/UI/TomAndJerryIcon.png")));
        currentStage.initStyle(StageStyle.DECORATED);

        //Load FXML
        creditsMenuStructure = FXMLLoader.load(getClass().getResource("../View/CreditsMenuStructure.fxml"));

        //Load scenes
        creditsMenuScene = new Scene(creditsMenuStructure,899,952);

        //Load the game stage control
        FXMLLoader mainMenuLoader = new FXMLLoader(getClass().getResource("../View/MainMenuStructure.fxml"));
        mainMenuLoader.load();
        mainMenuControl = mainMenuLoader.getController();
        mainMenuScene = new Scene(mainMenuLoader.getRoot(),899,952);

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

        currentStage.setScene(mainMenuScene);
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


    public static void main(String[] args) {
        launch(args);
    }

    public static void returnToMainMenu()
    {
        currentStage.setScene(mainMenuScene);
    }

    public static void goToCreditsMenu()
    {
        currentStage.setScene(creditsMenuScene);
    }

    public static void switchToCredits()
    {
        currentStage.setScene(creditsMenuScene);
    }

    @Override
    public void stop() throws Exception
    {
        System.out.println("Game is shutting down");
        Main.viewModel.model.shutDown();
    }
}
