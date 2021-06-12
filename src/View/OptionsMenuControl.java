package View;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public ToggleButton tomToggleButton;
    public ToggleButton jerryToggleButton;
    public ToggleButton grassToggleButton;
    public ToggleButton sandToggleButton;
    public Button exitButton;
    public Button returnButton;
    public ImageView[] exitButtonStates;
    public ImageView[] returnButtonStates;

    public void configureButtons()
    {
        emptyToggleButton.setOnMouseEntered(e -> Main.playButtonHoverSound());
        emptyToggleButton.prefHeightProperty().bind(Bindings.divide(Main.getOptimalStage().widthProperty(), 10.0));
        simpleToggleButton.setOnMouseEntered(e -> Main.playButtonHoverSound());
        myMazeToggleButton.setOnMouseEntered(e -> Main.playButtonHoverSound());
        dfsToggleButton.setOnMouseEntered(e -> Main.playButtonHoverSound());
        bfsToggleButton.setOnMouseEntered(e -> Main.playButtonHoverSound());
        bestToggleButton.setOnMouseEntered(e -> Main.playButtonHoverSound());
        tomToggleButton.setOnMouseEntered(e -> Main.playButtonHoverSound());
        jerryToggleButton.setOnMouseEntered(e -> Main.playButtonHoverSound());
        grassToggleButton.setOnMouseEntered(e -> Main.playButtonHoverSound());
        sandToggleButton.setOnMouseEntered(e -> Main.playButtonHoverSound());

        Image image;
        try
        {
            if (Main.viewModel.getGenerateMazeConfiguration().equals("EmptyMazeGenerator"))
            {
                image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnEmpty.png"));
            }
            else
            {
                image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffEmpty.png"));
            }
            emptyToggleButton.setGraphic(new ImageView(image));

            if (Main.viewModel.getGenerateMazeConfiguration().equals("SimpleMazeGenerator"))
            {
                image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnSimple.png"));
            }
            else
            {
                image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffSimple.png"));
            }
            simpleToggleButton.setGraphic(new ImageView(image));

            if (Main.viewModel.getGenerateMazeConfiguration().equals("MyMazeGenerator"))
            {
                image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnMy.png"));
            }
            else
            {
                image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffMy.png"));
            }
            myMazeToggleButton.setGraphic(new ImageView(image));


            if (Main.viewModel.getMazeSearchingAlgorithm().equals("DepthFirstSearch"))
            {
                image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnDepth.png"));
            }
            else
            {
                image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffDepth.png"));
            }
            dfsToggleButton.setGraphic(new ImageView(image));

            if (Main.viewModel.getMazeSearchingAlgorithm().equals("BreadthFirstSearch"))
            {
                image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnBreadth.png"));
            }
            else
            {
                image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffBreadth.png"));
            }
            bfsToggleButton.setGraphic(new ImageView(image));

            if (Main.viewModel.getMazeSearchingAlgorithm().equals("BestFirstSearch"))
            {
                image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnBest.png"));
            }
            else
            {
                image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffBest.png"));
            }
            bestToggleButton.setGraphic(new ImageView(image));

            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnTom.png"));
            tomToggleButton.setGraphic(new ImageView(image));

            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffJerry.png"));
            jerryToggleButton.setGraphic(new ImageView(image));

            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnGrass.png"));
            grassToggleButton.setGraphic(new ImageView(image));

            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffSand.png"));
            sandToggleButton.setGraphic(new ImageView(image));

            exitButtonStates = new ImageView[3];
            returnButtonStates = new ImageView[3];
            Main.loadExitAndReturn(exitButtonStates, returnButtonStates, exitButton, returnButton);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Button apply failed");
            e.printStackTrace();
        }
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

    public void emptyButtonClicked(ActionEvent actionEvent)
    {
        try
        {
            Main.playButtonAcceptSound();
            Image image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnEmpty.png"));
            emptyToggleButton.setGraphic(new ImageView(image));
            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffSimple.png"));
            simpleToggleButton.setGraphic(new ImageView(image));
            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffMy.png"));
            myMazeToggleButton.setGraphic(new ImageView(image));
            Main.viewModel.setGenerateMazeConfiguration("EmptyMazeGenerator");

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
            Main.viewModel.setGenerateMazeConfiguration("SimpleMazeGenerator");
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
            Main.viewModel.setGenerateMazeConfiguration("MyMazeGenerator");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Tile image not found");
        }
    }


    public void dfsButtonClicked(ActionEvent actionEvent)
    {
        try
        {
            Main.playButtonAcceptSound();
            Image image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnDepth.png"));
            dfsToggleButton.setGraphic(new ImageView(image));
            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffBreadth.png"));
            bfsToggleButton.setGraphic(new ImageView(image));
            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffBest.png"));
            bestToggleButton.setGraphic(new ImageView(image));
            Main.viewModel.setSearchingAlgorithmConfiguration("DepthFirstSearch");

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Tile image not found");
        }
    }

    public void bfsButtonClicked(ActionEvent actionEvent)
    {
        try
        {
            Main.playButtonAcceptSound();
            Image image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffDepth.png"));
            dfsToggleButton.setGraphic(new ImageView(image));
            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnBreadth.png"));
            bfsToggleButton.setGraphic(new ImageView(image));
            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffBest.png"));
            bestToggleButton.setGraphic(new ImageView(image));
            Main.viewModel.setSearchingAlgorithmConfiguration("BreadthFirstSearch");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Tile image not found");
        }
    }

    public void bestButtonClicked(ActionEvent actionEvent)
    {
        try
        {
            Main.playButtonAcceptSound();
            Image image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffDepth.png"));
            dfsToggleButton.setGraphic(new ImageView(image));
            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffBreadth.png"));
            bfsToggleButton.setGraphic(new ImageView(image));
            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnBest.png"));
            bestToggleButton.setGraphic(new ImageView(image));
            Main.viewModel.setSearchingAlgorithmConfiguration("BestFirstSearch");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Tile image not found");
        }
    }

    public void tomButtonClicked(ActionEvent actionEvent)
    {
        Main.playButtonAcceptSound();
        Main.myViewController.setPlayerCharacter("Tom");
        Main.winMenuControl.setBackGroundCharacter();
        try
        {
            Image image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnTom.png"));
            tomToggleButton.setGraphic(new ImageView(image));

            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffJerry.png"));
            jerryToggleButton.setGraphic(new ImageView(image));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Character image not found");
        }
        Main.myViewController.mazeDisplayer.draw();
    }

    public void jerryButtonClicked(ActionEvent actionEvent)
    {
        Main.playButtonAcceptSound();
        Main.myViewController.setPlayerCharacter("Jerry");
        Main.winMenuControl.setBackGroundCharacter();
        try
        {
            Image image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffTom.png"));
            tomToggleButton.setGraphic(new ImageView(image));

            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnJerry.png"));
            jerryToggleButton.setGraphic(new ImageView(image));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Character image not found");
        }
        Main.myViewController.mazeDisplayer.draw();
    }

    public void grassButtonClicked(ActionEvent actionEvent)
    {
        Main.playButtonAcceptSound();
        Main.myViewController.setTerrainType("grass");
        try
        {
            Image image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnGrass.png"));
            grassToggleButton.setGraphic(new ImageView(image));

            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffSand.png"));
            sandToggleButton.setGraphic(new ImageView(image));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Terrain image not found");
        }
        Main.myViewController.mazeDisplayer.draw();
        Main.myViewController.setBackGroundType("Grass");
    }

    public void sandButtonClicked(ActionEvent actionEvent)
    {
        Main.playButtonAcceptSound();
        Main.myViewController.setTerrainType("sand");
        try
        {
            Image image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOffGrass.png"));
            grassToggleButton.setGraphic(new ImageView(image));

            image = new Image(new FileInputStream("./resources/UI/Buttons/Picker/PickerOnSand.png"));
            sandToggleButton.setGraphic(new ImageView(image));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Terrain image not found");
        }
        Main.myViewController.mazeDisplayer.draw();
        Main.myViewController.setBackGroundType("Sand");
    }
}
