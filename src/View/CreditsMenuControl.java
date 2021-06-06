package View;

import javafx.application.Platform;
import javafx.event.ActionEvent;

public class CreditsMenuControl
{
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
