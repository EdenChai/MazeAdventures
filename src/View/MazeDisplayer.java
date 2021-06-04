package View;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Eden_Hai
 * @version 1.0
 * @since 29-05-2021
 */
public class MazeDisplayer extends Canvas
{
    private Maze maze;
    private Solution solution;

    // player position:
    private int playerRow;
    private int playerCol;

    // wall and player images:
    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();

    // goal position image
    StringProperty imageFileNameGoalPosition = new SimpleStringProperty();

    /* Getter and Setter */
    public int getPlayerRow() { return playerRow; }
    public int getPlayerCol() { return playerCol; }
    public String getImageFileNameWall() { return imageFileNameWall.get(); }
    public String getImageFileNamePlayer() { return imageFileNamePlayer.get(); }
    public String getImageFileNameGoalPosition() { return imageFileNameGoalPosition.get(); }
    public void setImageFileNameWall(String imageFileNameWall) { this.imageFileNameWall.set(imageFileNameWall); }
    public void setImageFileNamePlayer(String imageFileNamePlayer) { this.imageFileNamePlayer.set(imageFileNamePlayer); }
    public void setImageFileNameGoalPosition(String imageFileNameGoalPosition) { this.imageFileNameGoalPosition.set(imageFileNameGoalPosition); }

    public void setPlayerPosition(int playerRow, int playerCol)
    {
        this.playerRow = playerRow;
        this.playerCol = playerCol;
        draw();
    }

    public void setSolution(Solution solution)
    {
        this.solution = solution;
        //TODO: change to other function
        draw();
    }

    public void drawMaze(Maze maze)
    {
        this.maze = maze;
        draw();
    }

    private void draw()
    {
        if (maze == null) return;

        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        int rows = maze.getRowSize();
        int cols = maze.getColSize();

        double cellHeight = canvasHeight / rows;
        double cellWidth = canvasWidth / cols;

        GraphicsContext graphicsContext = getGraphicsContext2D();

        // clear the canvas:
        graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

        drawMazeWalls(graphicsContext, rows, cols, cellHeight, cellWidth);
        drawMazePlayer(graphicsContext, cellHeight, cellWidth);
        drawMazeGoalPosition(graphicsContext, cellHeight, cellWidth);
    }

    private void drawMazeGoalPosition(GraphicsContext graphicsContext, double cellHeight, double cellWidth)
    {
        Image goalImage = null;
        try
        {
            goalImage = new Image(new FileInputStream(getImageFileNameGoalPosition()));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("There is no goal image");
        }

        double x = maze.getGoalPosition().getColumnIndex() * cellWidth;
        double y = maze.getGoalPosition().getRowIndex() * cellHeight;

        graphicsContext.setFill(Color.GREEN);

        if (goalImage == null) { graphicsContext.fillRect(x, y, cellWidth, cellHeight); }
        else { graphicsContext.drawImage(goalImage, x, y, cellWidth, cellHeight); }
    }

    private void drawMazePlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth)
    {
        Image playerImage = null;
        try
        {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("There is no player image");
        }

        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;

        graphicsContext.setFill(Color.GREEN);

        if (playerImage == null) { graphicsContext.fillRect(x, y, cellWidth, cellHeight); }
        else { graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight); }
    }

    private void drawMazeWalls(GraphicsContext graphicsContext, int rows, int cols, double cellHeight, double cellWidth)
    {
        Image wallImage = null;
        try
        {
            wallImage = new Image(new FileInputStream(getImageFileNameWall()));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("There is no wall image");
        }

        graphicsContext.setFill(Color.RED);

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                // if it is a wall:
                if (maze.getMaze()[i][j] == 1)
                {
                    double x = j * cellWidth;
                    double y = i * cellHeight;

                    if (wallImage == null) { graphicsContext.fillRect(x, y, cellWidth, cellHeight); }
                    else { graphicsContext.drawImage(wallImage, x, y, cellWidth, cellHeight); }
                }
            }
        }
    }
}
