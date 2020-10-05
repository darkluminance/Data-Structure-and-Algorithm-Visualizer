package GraphAlgorithms.Maze;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class MazeVisualize extends JPanel{
    public static final int WIDTH = 1280;       //Width of screen
    public static final int HEIGHT = 720;       //Height of screen
    public static final int gridWIDTH = WIDTH - 1;    //Width of grid
    public static final int gridHEIGHT = HEIGHT - 80;   //Height of grid
    public static final int gridSIZE = 20;      //Size of each grid
    public static final int gridRows = gridHEIGHT/gridSIZE ;      //Rows in grid
    public static final int gridCols = gridWIDTH/gridSIZE;           //Columns in grid

    public int sourceX = 1;
    public int sourceY = gridRows/2;
    public int targetX = gridCols-2;
    public int targetY = gridRows/2;
    public int clickState = 0;                  //Keeps track of which mouse key has been pressed

    public boolean status;

    public Point mPos = new Point(0,0);
    public Point current = new Point(0,0);

    public Color BGColor  = Color.darkGray;

    public int[][] grid = new int[gridRows][gridCols];

    public Dictionary pathPos = new Hashtable();            //Stores the found path



    //0 = unvisited
    //1 = obstacle
    //2 = start point
    //3 = path
    //4 = target
    //5 = visited
    //69= visiting
    public MazeVisualize(){
        drawGrid();
    }

    //Resets all the variables to its initial
    public void resetValues(){
        current = new Point(0,0);
        pathPos = new Hashtable();

        for (int i = 0; i<gridRows; i++){
            for (int j = 0; j<gridCols; j++){
                if (grid[i][j] != 1)
                    grid[i][j] = 0;

                if (i == 0 || i == gridRows-1)  grid[i][j] = 1;
                else if (j == 0 || j == gridCols-1)  grid[i][j] = 1;
            }
        }

        grid[sourceY][sourceX] = 2;
        grid[targetY][targetX] = 4;

        Update();
    }

    //Reset the full grid
    public void drawGrid(){
        for (int i = 0; i<gridRows; i++){
            for (int j = 0; j<gridCols; j++){
                grid[i][j] = 0;
                if (i == 0 || i == gridRows-1)  grid[i][j] = 1;
                else if (j == 0 || j == gridCols-1)  grid[i][j] = 1;
            }
        }

        resetValues();
    }

    //Each time repaint is called, this function runs
    @Override
    public void paintComponent(Graphics g){
        Graphics2D graphics = (Graphics2D)g;

        //For smoother edges, turning on Anti Aliasing
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paintComponent(graphics);

        graphics.setColor(BGColor);
        graphics.fillRect(0,0, WIDTH,gridHEIGHT);
        graphics.setColor(BGColor.darker());
        graphics.fillRect(0,0, WIDTH,WIDTH - gridHEIGHT);

        int startX = 0;     //This is the x start point of the grid
        int startY = 0;     //This is the y start point of the grid

        grid[sourceY][sourceX] = 2;
        grid[targetY][targetX] = 4;


        //For every cell in the grid
        for (int i = 0; i<gridRows; i++){
            for (int j = 0; j<gridCols; j++){
                //If mouse click is valid
                if (mPos.x >= gridSIZE*j && mPos.x <= gridSIZE*(j+1) &&
                        mPos.y >= gridSIZE*i && mPos.y <= gridSIZE*(i+1)){

                    //If shift + left click is pressed
                    if (clickState == 2){
                        grid[sourceY][sourceX] = 0;     //Set source
                        sourceY = i;
                        sourceX = j;
                    }
                    //If alt + left click is pressed
                    else if (clickState == 4){
                        grid[targetY][targetX] = 0;     //Set target
                        targetY = i;
                        targetX = j;
                    }
                    //if(j!=sourceX && i!= sourceY && j!= targetX && i!= targetY)
                    grid[i][j] = clickState;
                    grid[sourceY][sourceX] = 2;
                    grid[targetY][targetX] = 4;

                    if (clickState != 1)
                        clickState = 0;

                    for (int p = 0; p<gridRows; p++) {
                        for (int q = 0; q < gridCols; q++) {
                            if (grid[p][q] == 2 && sourceX != q && sourceY != p) grid[p][q] = 0;
                            if (grid[p][q] == 4 && targetX != q && targetY != p) grid[p][q] = 0;
                        }
                    }
                }
                if (grid[i][j] == 0){
                    graphics.setColor(Color.white);
                }else if (grid[i][j] == 1){
                    graphics.setColor(new Color(18, 18, 18, 255));
                }else if (grid[i][j] == 2 || grid[i][j] == -1){
                    graphics.setColor(new Color(214, 48, 49, 255));
                }else if (grid[i][j] == 3){
                    graphics.setColor(new Color(253, 253, 150, 255));
                }else if (grid[i][j] == 4){
                    graphics.setColor(new Color(108, 92, 231));
                }


                if (i == 0 && j == 0){
                    graphics.setColor(new Color(18, 18, 18, 255));
                }


                graphics.fillRect((gridSIZE*j)+startX,(gridSIZE*i)+startY, gridSIZE, gridSIZE);
                graphics.setColor(BGColor.darker());
                graphics.drawRect((gridSIZE*j)+startX,(gridSIZE*i)+startY, gridSIZE, gridSIZE);

                //Draw an oval on the source and target cells
                if (grid[i][j] == 2 || grid[i][j] == 4){
                    graphics.setColor(new Color(253, 253, 150, 255));
                    graphics.fillOval((gridSIZE*j)+startX + 10 ,(gridSIZE*i)+startY + 10 , gridSIZE/3, gridSIZE/3);
                }

            }
        }

    }


    public void Update(){
        validate();
        repaint();
    }
}