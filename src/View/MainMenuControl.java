package View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class MainMenuControl
{
    public ImageView BackGround;

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

    public void loadGameButtonClicked(ActionEvent actionEvent){
        //Main.load();
    }
}
