package View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CreditsMenuControl
{
    public ImageView BackGround;
    public Button exitButton;
    public Button returnButton;
    public ImageView[] exitButtonStates;
    public ImageView[] returnButtonStates;

    public void configureButtons()
    {
        exitButtonStates = new ImageView[3];
        returnButtonStates = new ImageView[3];
        Main.loadExitAndReturn(exitButtonStates, returnButtonStates, exitButton, returnButton);
    }


    public void setBackGround(Stage stage)
    {
        BackGround.fitWidthProperty().bind(stage.widthProperty());
        BackGround.fitHeightProperty().bind(stage.heightProperty());
    }

    public void returnButtonClicked(ActionEvent actionEvent)
    {
        Main.playButtonClickSound();
        Main.returnToLastScene();
    }

    public void exitButtonClicked(ActionEvent actionEvent)
    {
        Main.viewModel.model.shutDown();
        Platform.exit();
        System.exit(0);
    }
}
