package View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class WinMenuControl
{
        public ImageView BackGround;
        public Button exitButton;
        public Button returnToMenuButton;
        public ImageView[] exitButtonStates;
        public ImageView[] returnToMenuButtonStates;

        public void configureButtons()
        {
            loadButtonGrapic();
            exitButtonStates = new ImageView[3];
            ImageView[] returnButtonStates = new ImageView[3];
            Button returnButton = new Button();
            Main.loadButtonGraphicsExitAndReturn(exitButtonStates,returnButtonStates, exitButton, returnButton);
            setButtonFunctions(returnToMenuButton,returnToMenuButtonStates);
        }

    public void loadButtonGrapic()
    {
        returnToMenuButtonStates = new ImageView[3];
        returnToMenuButtonStates[0] = loadImageView("./resources/UI/Buttons/BlueButtons/BlueButtonReturnToMenuUnPressed.png");
        returnToMenuButtonStates[1] = loadImageView("./resources/UI/Buttons/BlueButtons/BlueButtonReturnToMenuHover.png");
        returnToMenuButtonStates[2] = loadImageView("./resources/UI/Buttons/BlueButtons/BlueButtonReturnToMenuPressed.png");
    }

    public ImageView loadImageView(String path)
    {
        try
        {
            return new ImageView(new Image(new FileInputStream(path)));
        } catch (FileNotFoundException e)
        {
            System.out.println("Button load failed");
        }
        return null;
    }


    public void setButtonFunctions(Button button, ImageView[] imageView)
    {
        button.setGraphic(imageView[0]);
        button.setOnMouseEntered(e -> {button.setGraphic(imageView[1]);
            Main.playButtonHoverSound();});
        button.setOnMouseExited(e -> button.setGraphic(imageView[0]));
        button.setOnMousePressed(e -> button.setGraphic(imageView[2]));
    }

    public void setBackGroundCharacter()
    {
        String name = Main.myViewController.getPlayerCharacter();
        try
        {
            BackGround.setImage(new Image(new FileInputStream("./resources/UI/UI/GameWin"+name+".png")));
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
