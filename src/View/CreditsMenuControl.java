package View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CreditsMenuControl
{
    public ImageView BackGround;

    public void setBackGround(Stage stage)
    {
        BackGround.fitWidthProperty().bind(stage.widthProperty());
        BackGround.fitHeightProperty().bind(stage.heightProperty());
    }

    public void returnButtonClicked(ActionEvent actionEvent)
    {
        Main.returnToMainMenu();
    }

    public void exitButtonClicked(ActionEvent actionEvent)
    {
        Main.viewModel.model.shutDown();
        Platform.exit();
        System.exit(0);
    }
}
