package GraphAlgorithms;

import javax.swing.*;
import java.awt.*;

public class GraphVisualize extends JPanel {
    public static final int WIDTH = 1280;       //Width of screen
    public static final int HEIGHT = 720;       //Height of screen
    public static final int win_WIDTH = 1000;   //Width of visualizer
    public static final int gridWIDTH = 990;    //Width of grid
    public static final int gridHEIGHT = 690;   //Height of grid
    public static final int gridSIZE = 30;      //Size of each grid
    public static final int gridRows = gridHEIGHT/gridSIZE ;      //Rows in grid
    public static final int gridCols = gridWIDTH/gridSIZE;           //Columns in grid

    public int iterations = 0;                  //Counts the no of iterations in path-finding
    public int animSpeed = 1;                   //Amount of delay in animation
    public int sourceX = 1;
    public int sourceY = 1;
    public int targetX = gridCols-2;
    public int targetY = gridRows-2;
    public int clickState = 0;

    public Point mPos = new Point(69,69);

    public Color BGColor  = Color.darkGray;

    public int[][] grid = new int[gridRows][gridCols];

    //0 = unvisited
    //1 = obstacle
    //2 = start point
    //5-~ = visited
    //4 = target
    public GraphVisualize(){
        for (int i = 0; i<gridRows; i++){
            for (int j = 0; j<gridCols; j++){
                grid[i][j] = 0;
                if (i == 0 || i == gridRows-1)  grid[i][j] = 1;
                else if (j == 0 || j == gridCols-1)  grid[i][j] = 1;
            }
        }

        Update();
    }

    //Each time repaint is called, this function runs
    @Override
    public void paintComponent(Graphics g){
        Graphics2D graphics = (Graphics2D)g;
        super.paintComponent(graphics);

        graphics.setColor(BGColor);                     //BG color
        graphics.fillRect(0,0, WIDTH,HEIGHT);     //Background

        int startX = 0;
        int startY = 0;

        grid[sourceY][sourceX] = 2;
        grid[targetY][targetX] = 4;

        for (int i = 0; i<gridRows; i++){
            for (int j = 0; j<gridCols; j++){
                if (mPos.x >= gridSIZE*j && mPos.x <= gridSIZE*(j+1) &&
                        mPos.y >= gridSIZE*i && mPos.y <= gridSIZE*(i+1)){
                    if (clickState == 2){
                        grid[sourceY][sourceX] = 0;
                        sourceY = i;
                        sourceX = j;
                    }else if (clickState == 4){
                        grid[targetY][targetX] = 0;
                        targetY = i;
                        targetX = j;
                    }
                    //if(j!=sourceX && i!= sourceY && j!= targetX && i!= targetY)
                    grid[i][j] = clickState;
                    grid[sourceY][sourceX] = 2;
                    grid[targetY][targetX] = 4;

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
                    graphics.setColor(Color.black);
                }else if (grid[i][j] == 2){
                    graphics.setColor(Color.green);
                }else if (grid[i][j] == 4){
                    graphics.setColor(Color.red);
                }

                graphics.fillRect((gridSIZE*j)+startX,(gridSIZE*i)+startY, gridSIZE, gridSIZE);
                graphics.setColor(BGColor.darker());
                graphics.drawRect((gridSIZE*j)+startX,(gridSIZE*i)+startY, gridSIZE, gridSIZE);
            }
        }


    }

    public void Update(){
        validate();
        repaint();
    }

    //Delay task for t milliseconds
    public void sleep(int t){
        try {
            Thread.sleep(t);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}
