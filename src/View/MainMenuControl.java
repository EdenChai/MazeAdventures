package View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class MainMenuControl
{

    public void settingButtonClicked(ActionEvent actionEvent) throws IOException {
        //Main.switchSceneSettings();

    }

    public void helpButtonClicked(ActionEvent actionEvent) {
        //Main.switchSceneHelp();

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
        //Main.play();
    }

    public void loadGameButtonClicked(ActionEvent actionEvent){
        //Main.load();
    }
}
