package GraphAlgorithms.Maze;


import GraphAlgorithms.Pathfinder.*;
import MenuScreens.Algorithms;
import MenuScreens.GraphAlgorithms;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MazeScreen {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int gridWIDTH = WIDTH ;    //Width of grid
    public static final int gridHEIGHT = HEIGHT - 80;   //Height of grid
    public int gridSIZE = 20;      //Size of each grid
    public int gridRows = gridHEIGHT/gridSIZE ;      //Rows in grid
    public int gridCols = gridWIDTH/gridSIZE;           //Columns in grid

    public Color themeColor  = new Color(110,217,161);
    public Color bgColor  = Color.darkGray;
    //public Color themeColor  = bgColor.darker();


    JFrame f;
    JPanel p,bp;
    JButton loadBtn, saveBtn, startBtn, resetBtn,instructionsBtn, backBtn, randomMazeBtn, generateMazeBtn;

    MazeVisualize gv;

    public Point mousePos;      //Stores the position of the mouse when it's click

    public String mainFont = "Century Gothic";

    public MazeScreen(){
        gv = new MazeVisualize();
        gridSIZE = gv.gridSIZE;

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
                gv.mPos = new Point(1280,720);
            }
        });

        //Botton side panel
        bp = new JPanel();
        bp.setBounds(0,gridHEIGHT,WIDTH, HEIGHT - gridHEIGHT);
        bp.setBackground(bgColor.darker());
        bp.setLayout(null);

        int posCounter = 20;
        loadBtn = new JButton("Load Maze");
        loadBtn.setBounds(posCounter, 5, 100,30);
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //new GraphAlgorithms();
            }
        });
        loadBtn.setBackground(Color.darkGray.darker());
        loadBtn.setFont(new Font(mainFont, Font.BOLD, 15));
        loadBtn.setForeground(Color.white);
        loadBtn.setFocusable(false);
        loadBtn.setBorder(null);
        loadBtn.setVisible(true);
        //When the button is hovered
        loadBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loadBtn.setForeground(Color.white.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loadBtn.setForeground(Color.white);
            }
        });
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new java.io.File("./mazes"));
                fc.setDialogTitle("Choose a maze image");
                int response = fc.showOpenDialog(f);
                if(response == JFileChooser.APPROVE_OPTION){
                    String filename = fc.getSelectedFile().toString();
                    new LoadMaze(gv, filename);
                }

            }
        });
        posCounter+=130;


        saveBtn = new JButton("Save Maze");
        saveBtn.setBounds(posCounter, 5, 100,30);
        saveBtn.setBackground(Color.darkGray.darker());
        saveBtn.setFont(new Font(mainFont, Font.BOLD, 15));
        saveBtn.setForeground(Color.white);
        saveBtn.setFocusable(false);
        saveBtn.setBorder(null);
        saveBtn.setVisible(true);
        //When the button is hovered
        saveBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveBtn.setForeground(Color.white.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                saveBtn.setForeground(Color.white);
            }
        });
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(f,"Maze saved in ./mazes/maze" + new SaveMaze().Save(gv, false) + ".png");
            }
        });
        posCounter+=130;

        randomMazeBtn = new JButton("Randomize");
        randomMazeBtn.setBounds(posCounter, 5, 100,30);
        randomMazeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gv.drawGrid();
                gv.randomWalls();
                gv.Update();
            }
        });
        randomMazeBtn.setBackground(Color.darkGray.darker());
        randomMazeBtn.setFont(new Font(mainFont, Font.BOLD, 15));
        randomMazeBtn.setForeground(Color.white);
        randomMazeBtn.setFocusable(false);
        randomMazeBtn.setBorder(null);
        randomMazeBtn.setVisible(true);
        //When the button is hovered
        randomMazeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                randomMazeBtn.setForeground(Color.white.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                randomMazeBtn.setForeground(Color.white);
            }
        });
        posCounter+=130;


        startBtn = new JButton("Find Path");
        startBtn.setBounds(posCounter, 5, 100,30);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SolveMaze(gv);
            }
        });
        startBtn.setBackground(Color.darkGray.darker());
        startBtn.setFont(new Font(mainFont, Font.BOLD, 15));
        startBtn.setForeground(Color.white);
        startBtn.setFocusable(false);
        startBtn.setBorder(null);
        startBtn.setVisible(true);
        //When the button is hovered
        startBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startBtn.setForeground(Color.white.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                startBtn.setForeground(Color.white);
            }
        });
        posCounter+=130;


        resetBtn = new JButton("Reset Grid");
        resetBtn.setBounds(posCounter, 5, 100,30);
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gv.drawGrid();
                gv.Update();
            }
        });
        resetBtn.setBackground(Color.darkGray.darker());
        resetBtn.setFont(new Font(mainFont, Font.BOLD, 15));
        resetBtn.setForeground(Color.white);
        resetBtn.setFocusable(false);
        resetBtn.setBorder(null);
        resetBtn.setVisible(true);
        //When the button is hovered
        resetBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                resetBtn.setForeground(Color.white.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                resetBtn.setForeground(Color.white);
            }
        });
        posCounter+=130;



        instructionsBtn = new JButton("Instructions");
        instructionsBtn.setBounds(posCounter, 5, 100,30);
        instructionsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(f,"<html><div align: 'Center'>You can draw mazes on the screen and save them with the 'Save Maze' button.<br>" +
                        "You can load mazes using the 'Load Maze' button and select an appropriate maze image<br>" +
                        "You can generate random walls using the 'Randomize' button and also save it as a maze<br>" +
                        "You can also click on the 'Find Path' to solve the maze and then also save it.</div></html>");
            }
        });
        instructionsBtn.setBackground(Color.darkGray.darker());
        instructionsBtn.setFont(new Font(mainFont, Font.BOLD, 15));
        instructionsBtn.setForeground(Color.white);
        instructionsBtn.setFocusable(false);
        instructionsBtn.setBorder(null);
        instructionsBtn.setVisible(true);
        //When the button is hovered
        instructionsBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                instructionsBtn.setForeground(Color.white.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                instructionsBtn.setForeground(Color.white);
            }
        });
        posCounter+=130;


        generateMazeBtn = new JButton("Generate Maze");
        generateMazeBtn.setBounds(posCounter, 5, 130,30);
        generateMazeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBtn.setEnabled(false);
                saveBtn.setEnabled(false);
                startBtn.setEnabled(false);
                resetBtn.setEnabled(false);
                instructionsBtn.setEnabled(false);
                randomMazeBtn.setEnabled(false);
                generateMazeBtn.setEnabled(false);
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    public Void doInBackground() {
                        new MazeGenerator(gv);
                        return null;
                    }
                    @Override
                    protected void done() {
                        System.out.println("Done");
                        loadBtn.setEnabled(true);
                        saveBtn.setEnabled(true);
                        startBtn.setEnabled(true);
                        resetBtn.setEnabled(true);
                        instructionsBtn.setEnabled(true);
                        randomMazeBtn.setEnabled(true);
                        generateMazeBtn.setEnabled(true);
                    }
                };
                worker.execute();
            }
        });
        generateMazeBtn.setBackground(Color.darkGray.darker());
        generateMazeBtn.setFont(new Font(mainFont, Font.BOLD, 15));
        generateMazeBtn.setForeground(Color.white);
        generateMazeBtn.setFocusable(false);
        generateMazeBtn.setBorder(null);
        generateMazeBtn.setVisible(true);
        //When the button is hovered
        generateMazeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                generateMazeBtn.setForeground(Color.white.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                generateMazeBtn.setForeground(Color.white);
            }
        });
        posCounter+=130;


        backBtn = new JButton("Back");
        backBtn.setBounds(gridWIDTH - 100, 5, 80,30);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new GraphAlgorithms();
            }
        });
        backBtn.setBackground(Color.darkGray.darker());
        backBtn.setFont(new Font(mainFont, Font.BOLD, 15));
        backBtn.setForeground(Color.white);
        backBtn.setFocusable(false);
        backBtn.setBorder(null);
        backBtn.setVisible(true);
        //When the button is hovered
        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (backBtn.isEnabled())
                    backBtn.setForeground(Color.white.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backBtn.setForeground(Color.white);
            }
        });

        p.add(gv);
        p.setVisible(true);

        bp.add(loadBtn);
        bp.add(saveBtn);
        bp.add(startBtn);
        bp.add(randomMazeBtn);
        bp.add(backBtn);
        bp.add(resetBtn);
        bp.add(instructionsBtn);
        bp.add(generateMazeBtn);
        bp.setVisible(true);

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
