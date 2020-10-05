package GraphAlgorithms.Maze;


import GraphAlgorithms.Pathfinder.GraphVisualize;
import GraphAlgorithms.Pathfinder.GraphVisualizingScreen;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import java.awt.event.*;

public class MazeScreen {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int gridWIDTH = WIDTH - 1;    //Width of grid
    public static final int gridHEIGHT = HEIGHT - 80;   //Height of grid
    public static final int gridSIZE = 20;      //Size of each grid
    public static final int gridRows = gridHEIGHT/gridSIZE ;      //Rows in grid
    public static final int gridCols = gridWIDTH/gridSIZE;           //Columns in grid

    public Color themeColor  = new Color(110,217,161);
    public Color bgColor  = Color.darkGray;
    //public Color themeColor  = bgColor.darker();


    JFrame f;
    JPanel p,bp;

    MazeVisualize gv;

    public Point mousePos;      //Stores the position of the mouse when it's click

    public String mainFont = "Century Gothic";

    public MazeScreen(){
        gv = new MazeVisualize();

        //Main frame
        f = new JFrame("Maze Visualization");
        f.setSize(WIDTH, HEIGHT);                           //Setting dimensions
        f.setLayout(null);
        f.setLocationRelativeTo(null);                      //Sets the frame to the middle of the screen
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Will close when close BUTTON pressed
        f.setResizable(false);
        //f.setUndecorated(true);


        //Main panel
        p = new JPanel();
        p.setBounds(0,0,WIDTH, gridHEIGHT);
        p.setLayout((new GridLayout(1,1 )));
        p.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                gridPaint(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        });
        p.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gridPaint(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                gridPaint(e);

            }

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                gridPaint(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                gv.mPos = new Point(0,0);
            }
        });

        //Botton side panel
        bp = new JPanel();
        bp.setBounds(0,0,WIDTH, HEIGHT - gridHEIGHT);
        bp.setBackground(bgColor.darker());
        bp.setLayout(null);
        bp.setVisible(true);

        p.add(gv);
        p.setVisible(true);

        //bp.add(instructionText);


        f.add(p);
        f.add(bp);

        f.setVisible(true);
    }

    public static void main(String[] args) {
        MazeScreen gvs = new MazeScreen();
    }


    //Checks whether mouse click is in the grid area
    //If left click, paint wall -> 1
    //If right click, remove wall   -> 0
    //Shift + left click, set source point  -> 2
    //Alt + left click, set target point    -> 4
    public void gridPaint(MouseEvent e){
        if (e.getPoint().x >= 22 && e.getPoint().y >= 22 &&
                e.getPoint().x <= WIDTH - gridSIZE - 2 && e.getPoint().y <= gridHEIGHT - gridSIZE - 2){
            if (e.getButton() == MouseEvent.BUTTON1 && e.isShiftDown()) gv.clickState = 2;
            else if (e.getButton() == MouseEvent.BUTTON1 && e.isAltDown()) gv.clickState = 4;
            else if (e.getButton() == MouseEvent.BUTTON1) gv.clickState = 1;
            else if (e.getButton() == MouseEvent.BUTTON3) gv.clickState = 0;
            mousePos = e.getPoint();
            gv.mPos = mousePos;
            gv.Update();
        }
    }

}
