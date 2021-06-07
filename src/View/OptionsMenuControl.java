package View;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OptionsMenuControl
{
    public ImageView BackGround;
    public ToggleButton emptyToggleButton;
    public ToggleButton simpleToggleButton;
    public ToggleButton myMazeToggleButton;
    public ToggleButton dfsToggleButton;
    public ToggleButton bfsToggleButton;
    public ToggleButton bestToggleButton;

    public void configureButtons()
    {
        emptyToggleButton.setOnMouseEntered(e -> Main.playButtonHoverSound());
        emptyToggleButton.setPickOnBounds(false);
        emptyToggleButton.prefHeightProperty().bind(Bindings.divide(Main.getCurrentStage().widthProperty(), 10.0));
        simpleToggleButton.setOnMouseEntered(e -> Main.playButtonHoverSound());
        simpleToggleButton.setPickOnBounds(false);
        myMazeToggleButton.setOnMouseEntered(e -> Main.playButtonHoverSound());
        myMazeToggleButton.setPickOnBounds(false);
        dfsToggleButton.setOnMouseEntered(e -> Main.playButtonHoverSound());
        dfsToggleButton.setPickOnBounds(false);
        bfsToggleButton.setOnMouseEntered(e -> Main.playButtonHoverSound());
        bestToggleButton.setPickOnBounds(false);
        bestToggleButton.setOnMouseEntered(e -> Main.playButtonHoverSound());
        bestToggleButton.setPickOnBounds(false);
    }

    public void setBackGround(Stage stage)
    {
        BackGround.fitWidthProperty().bind(stage.widthProperty());
        BackGround.fitHeightProperty().bind(stage.heightProperty());
    }

    public void returnButtonClicked(ActionEvent actionEvent)
        {
            Main.playButtonExitSound();
            Main.returnToMainMenu();
        }

    public void exitButtonClicked(ActionEvent actionEvent)
    {
        Main.viewModel.model.shutDown();
        Platform.exit();
        System.exit(0);
    }

    public void emptyButtonClicked(ActionEvent actionEvent)
    {
        try
        {
            Main.playButtonAcceptSound();
            Image image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnEmpty.png"));
            ImageView imageView = new ImageView(image);
            imageView.fitWidthProperty().bind(Main.getCurrentStage().widthProperty());
            imageView.fitHeightProperty().bind(Main.getCurrentStage().heightProperty());
            emptyToggleButton.setGraphic(imageView);
            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffSimple.png"));
            simpleToggleButton.setGraphic(new ImageView(image));
            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffMy.png"));
            myMazeToggleButton.setGraphic(new ImageView(image));

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Tile image not found");
        }
    }

    public void simpleButtonClicked(ActionEvent actionEvent)
    {
        try
        {
            Main.playButtonAcceptSound();
            Image image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffEmpty.png"));
            emptyToggleButton.setGraphic(new ImageView(image));
            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnSimple.png"));
            simpleToggleButton.setGraphic(new ImageView(image));
            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffMy.png"));
            myMazeToggleButton.setGraphic(new ImageView(image));
        }
        catch (FileNotFoundException e)
            {
                System.out.println("Tile image not found");
            }
    }

    public void myMazeButtonClicked(ActionEvent actionEvent)
    {
        try
        {
            Main.playButtonAcceptSound();
            Image image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffEmpty.png"));
            emptyToggleButton.setGraphic(new ImageView(image));
            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffSimple.png"));
            simpleToggleButton.setGraphic(new ImageView(image));
            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnMy.png"));
            myMazeToggleButton.setGraphic(new ImageView(image));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Tile image not found");
        }
    }


    public void dfsButtonClicked(ActionEvent actionEvent)
    {

    }

    public void bfsButtonClicked(ActionEvent actionEvent)
    {

    }

    public void bestButtonClicked(ActionEvent actionEvent)
    {

    }
}
