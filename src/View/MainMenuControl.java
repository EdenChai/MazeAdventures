package View;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainMenuControl
{
    public ImageView BackGround;
    public Button newButton,loadButton,creditsButton,exitButton,optionsButton,helpButton;
    public ImageView[] newButtonStates,loadButtonStates,creditsButtonStates,exitButtonStates,optionsButtonStates,helpButtonStates;

    public void configureButtons()
    {
        loadButtonGrapic();

        setButtonFunctions(exitButton,exitButtonStates);
        setButtonFunctions(newButton,newButtonStates);
        setButtonFunctions(loadButton,loadButtonStates);
        setButtonFunctions(creditsButton,creditsButtonStates);
        setButtonFunctions(optionsButton,optionsButtonStates);
        setButtonFunctions(helpButton,helpButtonStates);


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
        newButtonStates = new ImageView[3];
        loadButtonStates = new ImageView[3];
        creditsButtonStates = new ImageView[3];
        exitButtonStates = new ImageView[3];
        optionsButtonStates = new ImageView[3];
        helpButtonStates = new ImageView[3];
        exitButtonStates[0] = loadImageView("./resources/UI/Buttons/RedButtons/SmallRedButtons/RedExitButtonUnPressed.png", 100, 100, false, false);
        exitButtonStates[1] = loadImageView("./resources/UI/Buttons/RedButtons/SmallRedButtons/RedExitButtonHover.png", 100, 100, false, false);
        exitButtonStates[2] = loadImageView("./resources/UI/Buttons/RedButtons/SmallRedButtons/RedExitButtonPressed.png", 100, 100, false, false);
        newButtonStates[0] = loadImageView("./resources/UI/Buttons/GreenButtons/GreenNewGameButtonUnPressed.png");
        newButtonStates[1] = loadImageView("./resources/UI/Buttons/GreenButtons/GreenNewGameButtonHover.png");
        newButtonStates[2] = loadImageView("./resources/UI/Buttons/GreenButtons/GreenNewGameButtonPressed.png");
        loadButtonStates[0] = loadImageView("./resources/UI/Buttons/GreenButtons/GreenLoadGameButtonUnPressed.png");
        loadButtonStates[1] = loadImageView("./resources/UI/Buttons/GreenButtons/GreenLoadGameHover.png");
        loadButtonStates[2] = loadImageView("./resources/UI/Buttons/GreenButtons/GreenLoadGameButtonPressed.png");
        creditsButtonStates[0] = loadImageView("./resources/UI/Buttons/BlueButtons/BlueButtonCredditsUnPressed.png");
        creditsButtonStates[1] = loadImageView("./resources/UI/Buttons/BlueButtons/BlueButtonCredditsHover.png");
        creditsButtonStates[2] = loadImageView("./resources/UI/Buttons/BlueButtons/BlueButtonCredditsPressed.png");
        optionsButtonStates[0] = loadImageView("./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenSettingsButtonUnPressed.png", 100, 100, false, false);
        optionsButtonStates[1] = loadImageView("./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenSettingsButtonHover.png", 100, 100, false, false);
        optionsButtonStates[2] = loadImageView("./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenSettingsButtonPressed.png", 100, 100, false, false);
        helpButtonStates[0] = loadImageView("./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenHelpButtonUnPressed.png", 100, 100, false, false);
        helpButtonStates[1] = loadImageView("./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenHelpButtonHover.png", 100, 100, false, false);
        helpButtonStates[2] = loadImageView("./resources/UI/Buttons/GreenButtons/SmallGreenButton/GreenHelpButtonPressed.png", 100, 100, false, false);
    }

    public ImageView loadImageView(String path,double num1, double num2, boolean bool1, boolean bool2)
    {
        try
        {
            return new ImageView(new Image(new FileInputStream(path),num1,num2,bool1,bool2));
        } catch (FileNotFoundException e)
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
        } catch (FileNotFoundException e)
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

    public void optionsButtonClicked(ActionEvent actionEvent) throws IOException {
        Main.goToOptionsMenu();
    }

    public void helpButtonClicked(ActionEvent actionEvent)
    {
        Main.goToHelpMenu();
    }

    public void exitButtonClicked(ActionEvent actionEvent) {
        Main.viewModel.model.shutDown();
        Platform.exit();
        System.exit(0);
    }

    public void creditsButtonClicked(ActionEvent actionEvent) {
        Main.goToCreditsMenu();

    }

    public void newGameButtonClicked(ActionEvent actionEvent){
        Main.goToGameMenu();
    }

    public void loadGameButtonClicked(ActionEvent actionEvent)
    {
        Main.load();
    }
}
