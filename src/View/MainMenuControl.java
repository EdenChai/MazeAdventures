package View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainMenuControl
{
    public ImageView BackGround;
    public Button newButton, loadButton, creditsButton, exitButton, optionsButton, helpButton;
    public ImageView[] newButtonStates, loadButtonStates, creditsButtonStates, exitButtonStates, optionsButtonStates, helpButtonStates;

    public void configureButtons()
    {
        loadButtonGraphic();
        Main.setButtonFunctions(exitButton, exitButtonStates);
        Main.setButtonFunctions(newButton, newButtonStates);
        Main.setButtonFunctions(loadButton, loadButtonStates);
        Main.setButtonFunctions(creditsButton, creditsButtonStates);
        Main.setButtonFunctions(optionsButton, optionsButtonStates);
        Main.setButtonFunctions(helpButton, helpButtonStates);
    }

    public void loadButtonGraphic()
    {
        newButtonStates = new ImageView[3];
        loadButtonStates = new ImageView[3];
        creditsButtonStates = new ImageView[3];
        exitButtonStates = new ImageView[3];
        optionsButtonStates = new ImageView[3];
        helpButtonStates = new ImageView[3];

        Button returnButton = new Button();
        ImageView[] returnButtonStates = new ImageView[3];

        Main.loadExitAndReturn(exitButtonStates, returnButtonStates, exitButton, returnButton);

        String path1 = "./resources/UI/Buttons/GreenButtons/GreenNewGameButtonUnPressed.png";
        String path2 = "./resources/UI/Buttons/GreenButtons/GreenNewGameButtonHover.png";
        String path3 = "./resources/UI/Buttons/GreenButtons/GreenNewGameButtonPressed.png";
        Main.loadButtonGraphics(path1, path2, path3, newButtonStates, newButton);

        path1 = "./resources/UI/Buttons/GreenButtons/GreenLoadGameButtonUnPressed.png";
        path2 = "./resources/UI/Buttons/GreenButtons/GreenLoadGameHover.png";
        path3 = "./resources/UI/Buttons/GreenButtons/GreenLoadGameButtonPressed.png";
        Main.loadButtonGraphics(path1, path2, path3, loadButtonStates, loadButton);

        path1 = "./resources/UI/Buttons/BlueButtons/BlueButtonCredditsUnPressed.png";
        path2 = "./resources/UI/Buttons/BlueButtons/BlueButtonCredditsHover.png";
        path3 = "./resources/UI/Buttons/BlueButtons/BlueButtonCredditsPressed.png";
        Main.loadButtonGraphics(path1, path2, path3, creditsButtonStates, creditsButton);

        Main.loadHelpAndOptions(helpButtonStates, optionsButtonStates, helpButton, optionsButton);
    }

//    public ImageView loadImageView(String path, double num1, double num2, boolean bool1, boolean bool2)
//    {
//        try
//        {
//            return new ImageView(new Image(new FileInputStream(path), num1, num2, bool1, bool2));
//        }
//        catch (FileNotFoundException e)
//        {
//            System.out.println("Button load failed");
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public ImageView loadImageView(String path)
//    {
//        try
//        {
//            return new ImageView(new Image(new FileInputStream(path)));
//        }
//        catch (FileNotFoundException e)
//        {
//            System.out.println("Button load failed");
//            e.printStackTrace();
//        }
//        return null;
//    }

    public void setBackGround(Stage stage)
    {
        BackGround.fitWidthProperty().bind(stage.widthProperty());
        BackGround.fitHeightProperty().bind(stage.heightProperty());
    }

    public void newGameButtonClicked(ActionEvent actionEvent)
    {
        Main.goToGameMenu();
    }

    public void loadGameButtonClicked(ActionEvent actionEvent)
    {
        Main.load();
    }

    public void creditsButtonClicked(ActionEvent actionEvent)
    {
        Main.goToCreditsMenu();
    }

    public void optionsButtonClicked(ActionEvent actionEvent)
    {
        Main.goToOptionsMenu();
    }

    public void helpButtonClicked(ActionEvent actionEvent)
    {
        Main.goToHelpMenu();
    }

    public void exitButtonClicked(ActionEvent actionEvent)
    {
        Main.viewModel.model.shutDown();
        Platform.exit();
        System.exit(0);
    }
}
