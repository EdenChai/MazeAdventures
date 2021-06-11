package View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class WinMenuControl
{
        public ImageView BackGround;
        public Button exitButton;
        public Button returnToMenuButton;
        public ImageView[] exitButtonStates;

        public void configureButtons()
        {
            exitButtonStates = new ImageView[3];
            ImageView[] returnButtonStates = new ImageView[3];
            Button returnButton = new Button();
            Main.loadButtonGraphicsExitAndReturn(exitButtonStates,returnButtonStates, exitButton, returnButton);
        }



        public void setBackGround(Stage stage)
        {
            BackGround.fitWidthProperty().bind(stage.widthProperty());
            BackGround.fitHeightProperty().bind(stage.heightProperty());
        }

        public void returnToMenuButtonClicked(ActionEvent actionEvent)
        {
            Main.playButtonClickSound();
            Main.returnToMainMenu();
        }

        public void exitButtonClicked(ActionEvent actionEvent)
        {
            Main.viewModel.model.shutDown();
            Platform.exit();
            System.exit(0);
        }
    }
