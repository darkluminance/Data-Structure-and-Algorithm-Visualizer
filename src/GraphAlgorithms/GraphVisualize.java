package GraphAlgorithms;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class GraphVisualize extends JPanel {
    public static final int WIDTH = 1280;       //Width of screen
    public static final int HEIGHT = 728;       //Height of screen
    public static final int win_WIDTH = 1000;   //Width of visualizer
    public static final int gridWIDTH = 990;    //Width of grid
    public static final int gridHEIGHT = 630;   //Height of grid
    public static final int gridSIZE = 30;      //Size of each grid
    public static final int gridRows = gridHEIGHT/gridSIZE ;      //Rows in grid
    public static final int gridCols = gridWIDTH/gridSIZE;           //Columns in grid

    public int iterations = 0;                  //Counts the no of iterations in path-finding
    public int sourceX = 1;
    public int sourceY = gridRows/2;
    public int targetX = gridCols-2;
    public int targetY = gridRows/2;
    public int clickState = 0;
    public int place = 0;
    public int pathPlace = 0;
    public int getAnimSpeed = 15;

    public boolean status;
    public boolean willFind = false;
    public boolean willAnimate = true;

    public Point mPos = new Point(0,0);
    public Point current = new Point(0,0);

    public Color BGColor  = Color.darkGray;

    public int[][] grid = new int[gridRows][gridCols];
    public int[][] level = new int[gridRows][gridCols];

    public Dictionary pathPos = new Hashtable();

    public String whichAlgorithm = "";

    List<Integer> patto = new ArrayList<Integer>();
    //-1 = visited start point
    //0 = unvisited
    //1 = obstacle
    //2 = start point
    //3 = path
    //4 = target
    //5 = visited
    //69= visiting
    public GraphVisualize(){
        drawGrid();     randomWalls();
    }

    //Resets all the variables to its initial
    public void resetValues(){
        current = new Point(0,0);
        pathPos = new Hashtable();
        pathPlace = 0;
        place = 0;
        iterations = 0;

        for (int i = 0; i<gridRows; i++){
            for (int j = 0; j<gridCols; j++){
                if (grid[i][j] != 1)
                    grid[i][j] = 0;

                level[i][j] = 0;
                if (i == 0 || i == gridRows-1)  grid[i][j] = 1;
                else if (j == 0 || j == gridCols-1)  grid[i][j] = 1;
            }
        }

        grid[sourceY][sourceX] = 2;
        grid[targetY][targetX] = 4;

        Update();
    }

    //Generate random walls like a maze
    public void randomWalls(){
        for (int i = 0; i<gridRows; i++){
            for (int j = 0; j<gridCols; j++){
                //69 percent of cells will be passable
                if (Math.random() * (100 - 0 + 1) + 0 >= 69){
                    grid[i][j] = 1;
                }
                if (i == 0 || i == gridRows-1)  grid[i][j] = 1;
                else if (j == 0 || j == gridCols-1)  grid[i][j] = 1;
            }
        }
    }

    //Reset the full grid
    public void drawGrid(){
        for (int i = 0; i<gridRows; i++){
            for (int j = 0; j<gridCols; j++){
                grid[i][j] = 0;
                level[i][j] = 0;
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
        super.paintComponent(graphics);

        graphics.setColor(BGColor);                     //BG color
        graphics.fillRect(0,0, WIDTH,HEIGHT);     //Background

        int startX = 0;
        int startY = 0;

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
                }else if (grid[i][j] == 5){
                    int area = 300;
                    if (whichAlgorithm == "dfs"){
                        area = 300;
                    }else if (whichAlgorithm == "bfs"){
                        area = 180;
                    }
                    float p = (float) level[i][j];
                    float a = (float) area;
                    float part = p/a * 180 ;
                    if (part >= 180){
                        part = 180 + 180 - part;
                    }
                    if (part <= 0){
                        part = -part +180;
                    }
                    if (part >= 180){
                        part = 180 + 180 - part;
                    }
                    if (part <= 0){
                        part = -part + 180;
                    }

                    Color c = new Color(0, 180-Math.round(part) + 69, 180-Math.round(part) + 29);
                    graphics.setColor(c);
                }
                if(i != 0 && j != 0 && grid[i][j] != 1 && grid[i][j] != 3  && grid[i][j] != 4){
                    if(i == current.y && j == current.x){
                        graphics.setColor(new Color(116, 185, 255));
                    }
                    if (grid[i][j] == 69){
                        graphics.setColor(new Color(116, 185, 255));
                    }

                }
                if (i == 0 && j == 0){
                    graphics.setColor(new Color(18, 18, 18, 255));
                }



                graphics.fillRect((gridSIZE*j)+startX,(gridSIZE*i)+startY, gridSIZE, gridSIZE);
                graphics.setColor(BGColor.darker());
                graphics.drawRect((gridSIZE*j)+startX,(gridSIZE*i)+startY, gridSIZE, gridSIZE);

                if (grid[i][j] == 2 || grid[i][j] == 4){
                    graphics.setColor(new Color(253, 253, 150, 255));
                    graphics.fillOval((gridSIZE*j)+startX + (gridSIZE/4),(gridSIZE*i)+startY + (gridSIZE/4), gridSIZE/2, gridSIZE/2);
                }

                if (grid[i][j] == 2 || grid[i][j] == 5 || grid[i][j] == 3 || grid[i][j] == 69){
                    graphics.setColor(Color.black);
                    g.setFont(new Font("Century Gothic", Font.PLAIN, 8));
                    graphics.drawString(Integer.toString(level[i][j]), (gridSIZE*j)+startX+2,(gridSIZE*i)+startY+28);
                }
                if (grid[i][j] == 3){
                    if (!pathPos.isEmpty()){
                        String pp = pathPos.get(new Point(i, j)).toString();
                        graphics.setColor(Color.darkGray);
                        g.setFont(new Font("Century Gothic", Font.BOLD, 13));
                        graphics.drawString(pp, (gridSIZE*j)+startX+1,(gridSIZE*i)+startY+13);
                    }

                }




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
