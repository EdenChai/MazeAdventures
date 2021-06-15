package View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class WinMenuControl
{
    public ImageView BackGround;
    public Button exitButton;
    public Button returnToMenuButton;
    public ImageView[] exitButtonStates;
    public ImageView[] returnToMenuButtonStates;

    public void configureButtons()
    {
        returnToMenuButtonStates = new ImageView[3];
        String path1 = "./resources/UI/Buttons/BlueButtons/BlueButtonReturnToMenuUnPressed.png";
        String path2 = "./resources/UI/Buttons/BlueButtons/BlueButtonReturnToMenuHover.png";
        String path3 = "./resources/UI/Buttons/BlueButtons/BlueButtonReturnToMenuPressed.png";
        Main.loadButtonGraphics(path1, path2, path3, returnToMenuButtonStates, returnToMenuButton);
        exitButtonStates = new ImageView[3];
        ImageView[] returnButtonStates = new ImageView[3];
        Button returnButton = new Button();
        Main.loadExitAndReturn(exitButtonStates, returnButtonStates, exitButton, returnButton);
    }

    public void setBackGroundCharacter()
    {
        String name = Main.myViewController.getPlayerCharacter();
        try
        {
            BackGround.setImage(new Image(new FileInputStream("./resources/UI/UI/GameWin" + name + ".png")));
        }
        catch (Exception e)
        {
            System.out.println("Background load failed");
        }
    }

    public void setBackGround(Stage stage)
    {
        BackGround.fitWidthProperty().bind(stage.widthProperty());
        BackGround.fitHeightProperty().bind(stage.heightProperty());
    }

    public void returnToMenuButtonClicked(ActionEvent actionEvent)
    {
        Main.playButtonClickSound();
        Main.returnToMainMenuFromWin();
    }

    public void exitButtonClicked(ActionEvent actionEvent)
    {
        Main.viewModel.model.shutDown();
        Platform.exit();
        System.exit(0);
    }
}
