package View;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class WarningMenuControl
{
    public ImageView BackGround;
    public Button exitButton;
    public ImageView[] exitButtonStates;

    public void configureButtons()
    {
        Button returnButton = new Button();
        exitButtonStates = new ImageView[3];
        ImageView[] returnButtonStates = new ImageView[3];
        Main.loadExitAndReturn(exitButtonStates,returnButtonStates, exitButton,returnButton);
    }



    public void setBackGround(Stage stage)
    {
        BackGround.fitWidthProperty().bind(stage.widthProperty());
        BackGround.fitHeightProperty().bind(stage.heightProperty());
    }

    public void exitButtonClicked(ActionEvent actionEvent)
    {
        Main.exitWarningMenu();
    }
}
