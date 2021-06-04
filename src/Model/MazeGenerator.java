package Model;

import java.util.Arrays;

/**
 * @author Eden_Hai
 * @version 1.0
 * @since 29-05-2021
 */
public class MazeGenerator
{

    public static void main(String[] args)
    {
        MazeGenerator generator = new MazeGenerator();
        int[][] maze = generator.generateRandomMaze(5,5);
        System.out.println(Arrays.deepToString(maze));
    }

    public int[][] generateRandomMaze(int rows, int cols)
    {
        int[][] maze = new int[rows][cols];
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                maze[i][j] = (int)Math.round(Math.random());
            }
        }
        return maze;
    }
}
