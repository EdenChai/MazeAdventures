package View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HelpMenuControl
{
    public ImageView BackGround;

    public void setBackGround(Stage stage)
    {
        BackGround.fitWidthProperty().bind(stage.widthProperty());
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
