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
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class Main extends Application
{
    public static MyModel model;
    public static MyViewModel viewModel;
    public static MainMenuControl mainMenuControl;
    public static CreditsMenuControl creditsMenuControl;
    public static HelpMenuControl helpMenuControl;
    public static OptionsMenuControl optionsMenuControl;
    public static MyViewController myViewController;
    public static WarningMenuControl warningMenuControl;
    public static WinMenuControl winMenuControl;
    public static FXMLLoader mainMenuLoader, creditsMenuLoader, helpMenuLoader, optionsMenuLoader, gameMenuLoader, warningMenuLoader, winMenuLoader;
    private static Scene mainMenuScene, helpMenuScene, creditsMenuScene, optionsMenuScene, gameMenuScene, warningMenuScene, lastScene, winMenuScene;
    public static Stage optimalStage, warningStage, borderStage, currentStage;
    private static MediaPlayer winLoopSoundPlayer;
    public Parent mainMenuStructure, creditsMenuStructure, helpMenuStructure, optionsMenuStructure, gameMenuStructure, warningMenuStructure, winMenuStructure;
    public static MediaPlayer mainMenuPlayer;


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //Initialize the current stage
        optimalStage = primaryStage;
        borderStage = new Stage();
        optimalStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("UI/UI/TomAndJerryIcon.png"))));
        borderStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("UI/UI/TomAndJerryIcon.png"))));
        optimalStage.initStyle(StageStyle.TRANSPARENT);
        borderStage.initStyle(StageStyle.DECORATED);
        currentStage = optimalStage;

        //Load loaders
        mainMenuLoader = new FXMLLoader(getClass().getClassLoader().getResource("View/MainMenuStructure.fxml"));
        creditsMenuLoader = new FXMLLoader(getClass().getClassLoader().getResource("View/CreditsMenuStructure.fxml"));
        helpMenuLoader = new FXMLLoader(getClass().getClassLoader().getResource("View/HelpMenuStructure.fxml"));
        optionsMenuLoader = new FXMLLoader(getClass().getClassLoader().getResource("View/OptionsMenuStructure.fxml"));
        gameMenuLoader = new FXMLLoader(getClass().getClassLoader().getResource("View/MyView.fxml"));
        warningMenuLoader = new FXMLLoader(getClass().getClassLoader().getResource("View/WarningMenuStructure.fxml"));
        winMenuLoader = new FXMLLoader(getClass().getClassLoader().getResource("View/WinMenuStructure.fxml"));

        //Load FXML
        mainMenuStructure = mainMenuLoader.load();
        creditsMenuStructure = creditsMenuLoader.load();
        helpMenuStructure = helpMenuLoader.load();
        optionsMenuStructure = optionsMenuLoader.load();
        gameMenuStructure = gameMenuLoader.load();
        warningMenuStructure = warningMenuLoader.load();
        winMenuStructure = winMenuLoader.load();

        //Load controllers
        mainMenuControl = mainMenuLoader.getController();
        creditsMenuControl = creditsMenuLoader.getController();
        helpMenuControl = helpMenuLoader.getController();
        optionsMenuControl = optionsMenuLoader.getController();
        myViewController = gameMenuLoader.getController();
        warningMenuControl = warningMenuLoader.getController();
        winMenuControl = winMenuLoader.getController();

        //Load scenes
        creditsMenuScene = new Scene(creditsMenuStructure, 899, 952);
        helpMenuScene = new Scene(helpMenuStructure, 899, 952);
        optionsMenuScene = new Scene(optionsMenuStructure, 899, 952);
        mainMenuScene = new Scene(mainMenuLoader.getRoot(), 899, 952);
        gameMenuScene = new Scene(gameMenuStructure, 899, 952);
        warningMenuScene = new Scene(warningMenuStructure, 790, 344);
        winMenuScene = new Scene(winMenuStructure, 899, 952);

        //Set Scenes background
        creditsMenuScene.setFill(Color.TRANSPARENT);
        helpMenuScene.setFill(Color.TRANSPARENT);
        optionsMenuScene.setFill(Color.TRANSPARENT);
        mainMenuScene.setFill(Color.TRANSPARENT);
        gameMenuScene.setFill(Color.TRANSPARENT);
        warningMenuScene.setFill(Color.TRANSPARENT);
        winMenuScene.setFill(Color.TRANSPARENT);

        //Load the models
        model = new MyModel();
        viewModel = new MyViewModel(model);
        model.addObserver(viewModel);

        //Load the music
        File soundClip = new File("./resources/Sound/Menu_Stage/Funky_Chill_2_loop.mp3");
        Media sound = new Media(soundClip.toURI().toString());
        mainMenuPlayer = new MediaPlayer(sound);
        mainMenuPlayer.setVolume(0.04);
        mainMenuPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mainMenuPlayer.play();

        configureControllers();

        //Load the main menu
        lastScene = mainMenuScene;
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

    public static void setButtonFunctions(Button button, ImageView[] imageView)
    {
        button.setGraphic(imageView[0]);
        button.setOnMouseEntered(e ->
        {
            button.setGraphic(imageView[1]);
            Main.playButtonHoverSound();
        });
        button.setOnMouseExited(e -> button.setGraphic(imageView[0]));
        button.setOnMousePressed(e -> button.setGraphic(imageView[2]));
    }

    public static void main(String[] args)
    {
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
        lastScene = mainMenuScene;
        playButtonClickSound();
        winLoopSoundPlayer.stop();
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
        playWinSoundLoop();
    }

    private static void playWinSoundLoop()
    {
        File soundClip = new File("./resources/Sound/Win_Lose/BRPG_Victory_Music_Loop.mp3");
        Media winSoundClip = new Media(soundClip.toURI().toString());
        winLoopSoundPlayer = new MediaPlayer(winSoundClip);
        winLoopSoundPlayer = new MediaPlayer(winSoundClip);
        winLoopSoundPlayer.setVolume(0.1);
        winLoopSoundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        winLoopSoundPlayer.setStartTime(Duration.seconds(0));
        winLoopSoundPlayer.setStopTime(Duration.seconds(9));
        winLoopSoundPlayer.play();
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
        myViewController.configure(viewModel, gameMenuScene, borderStage);
        viewModel.addObserver(myViewController);
        playButtonClickSound();
        borderStage.setScene(gameMenuScene);
        myViewController.setResizeEvent(gameMenuScene);
        myViewController.mazeDisplayer.online = false;
        myViewController.unDraw();
    }

    public static void playButtonHoverSound()
    {
        File soundClip = new File("./resources/Sound/Buttons/menu_button_beep.mp3");
        Media hoverSoundClip = new Media(soundClip.toURI().toString());
        MediaPlayer movementSoundPlayer = new MediaPlayer(hoverSoundClip);
        movementSoundPlayer.setVolume(0.1);
        movementSoundPlayer.play();
    }

    public static void playButtonClickSound()
    {
        File soundClip = new File("./resources/Sound/Buttons/menu_button_click.mp3");
        Media hoverSoundClip = new Media(soundClip.toURI().toString());
        MediaPlayer movementSoundPlayer = new MediaPlayer(hoverSoundClip);
        movementSoundPlayer.setVolume(0.1);
        movementSoundPlayer.play();
    }

    public static void playButtonAcceptSound()
    {
        File soundClip = new File("./resources/Sound/Buttons/menu_button_confirm.mp3");
        Media hoverSoundClip = new Media(soundClip.toURI().toString());
        MediaPlayer movementSoundPlayer = new MediaPlayer(hoverSoundClip);
        movementSoundPlayer.setVolume(0.1);
        movementSoundPlayer.play();
    }

    public static void playButtonExitSound()
    {
        File soundClip = new File("./resources/Sound/Buttons/menu_button_cancel.mp3");
        Media hoverSoundClip = new Media(soundClip.toURI().toString());
        MediaPlayer movementSoundPlayer = new MediaPlayer(hoverSoundClip);
        movementSoundPlayer.setVolume(0.1);
        movementSoundPlayer.play();
    }

    public static void loadButtonGraphics(String path1, String path2, String path3, ImageView[] buttonStates, Button button, double num1, double num2, boolean bool1, boolean bool2)
    {
        try
        {
            buttonStates[0] = new ImageView(new Image(new FileInputStream(path1), num1, num2, bool1, bool2));
            buttonStates[1] = new ImageView(new Image(new FileInputStream(path2), num1, num2, bool1, bool2));
            buttonStates[2] = new ImageView(new Image(new FileInputStream(path3), num1, num2, bool1, bool2));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Button load failed");
            e.printStackTrace();
        }
        setButtonFunctions(button, buttonStates);
    }

    public static void loadExitAndReturn(ImageView[] exitButtonStates, ImageView[] returnButtonStates, Button exitButton, Button returnButton)
    {
        String path1 = "./resources/UI/Buttons/RedButtons/SmallRedButtons/RedExitButtonUnPressed.png";
        String path2 = "./resources/UI/Buttons/RedButtons/SmallRedButtons/RedExitButtonHover.png";
        String path3 = "./resources/UI/Buttons/RedButtons/SmallRedButtons/RedExitButtonPressed.png";
        Main.loadButtonGraphics(path1, path2, path3, exitButtonStates, exitButton, 100, 100, false, false);
        path1 = "./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenReturnButtonUnPressed.png";
        path2 = "./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenReturnButtonHover.png";
        path3 = "./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenReturnButtonPressed.png";
        Main.loadButtonGraphics(path1, path2, path3, returnButtonStates, returnButton, 100, 100, false, false);
    }

    public static void loadHelpAndOptions(ImageView[] helpButtonStates, ImageView[] optionsButtonStates, Button helpButton, Button optionsButton)
    {
        String path1 = "./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenSettingsButtonUnPressed.png";
        String path2 = "./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenSettingsButtonHover.png";
        String path3 = "./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenSettingsButtonPressed.png";
        Main.loadButtonGraphics(path1, path2, path3, optionsButtonStates, optionsButton, 100, 100, false, false);

        path1 = "./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenHelpButtonUnPressed.png";
        path2 = "./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenHelpButtonHover.png";
        path3 = "./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenHelpButtonPressed.png";
        Main.loadButtonGraphics(path1, path2, path3, helpButtonStates, helpButton, 100, 100, false, false);
    }

    public static void loadButtonGraphics(String path1, String path2, String path3, ImageView[] buttonStates, Button button)
    {
        loadButtonGraphics(path1, path2, path3, buttonStates, button, 0, 0, false, false);
    }

    @Override
    public void stop()
    {
        Main.viewModel.model.shutDown();
    }

    public static void load()
    {
        try
        {
            myViewController.mazeDisplayer.solutionIsShowing = false;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Maze Files", "*.maze"));
            File loadFile = fileChooser.showOpenDialog(optimalStage);
            if (loadFile != null)
            {
                lastScene = mainMenuScene;
                Main.goToGameMenu();
                myViewController.mazeDisplayer.online = true;
                viewModel.loadGame(loadFile);
                myViewController.mazeGenerated();
            }
        }
        catch (Exception ignored) { }
    }
}
