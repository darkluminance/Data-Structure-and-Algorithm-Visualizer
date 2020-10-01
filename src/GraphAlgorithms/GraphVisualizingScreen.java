package GraphAlgorithms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GraphVisualizingScreen {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 728;
    public static final int win_WIDTH = 1000;
    public static final int gridWIDTH = 990;    //Width of grid
    public static final int gridHEIGHT = 690;   //Height of grid
    public static final int gridSIZE = 30;      //Size of each grid
    public static final int gridRows = gridHEIGHT/gridSIZE ;      //Rows in grid
    public static final int gridCols = gridWIDTH/gridSIZE;           //Columns in grid

    public Color themeColor  = new Color(110,217,161);

    JFrame f;
    JPanel p,bp;
    JButton sBtn, rBtn;
    GraphVisualize gv;

    public Point mousePos;

    public String mainFont = "Century Gothic";

    public GraphVisualizingScreen(){
        gv = new GraphVisualize();

        f = new JFrame("Graph Visualization");
        f.setSize(WIDTH, HEIGHT);   //Setting dimensions
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Will close when close BUTTON pressed
        f.setResizable(false);

        p = new JPanel();
        p.setBounds(0,0,gridWIDTH, HEIGHT);
        p.setLayout((new GridLayout(1,1 )));
        p.add(gv);
        p.setVisible(true);
        p.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //if (!e.isAltDown() && !e.isShiftDown())
                    gridPaint(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        p.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.isAltDown() || e.isShiftDown())
                {
                    //gridPaint2(e);
                }
                    gridPaint(e);


            }

            @Override
            public void mousePressed(MouseEvent e) {
                //if (!e.isAltDown() && !e.isShiftDown())
                    gridPaint(e);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                gridPaint(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                gv.mPos = new Point(0,0);
            }
        });

        bp = new JPanel();
        bp.setBounds(gridWIDTH,0,WIDTH-gridWIDTH, HEIGHT);
        bp.setBackground(Color.darkGray);
        bp.setLayout(null);
        bp.setVisible(true);

        sBtn = new JButton("Start");
        sBtn.setBounds((WIDTH-gridWIDTH -180)/2, 50, 180,50);
        sBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sBtn.setEnabled(false);
                rBtn.setEnabled(false);
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    public Void doInBackground() {
                        new DepthFirstSearch(gv, new Point(gv.sourceX, gv.sourceY));

                        return null;
                    }
                    @Override
                    protected void done() {
                        System.out.println("Done");
                        sBtn.setEnabled(true);
                        rBtn.setEnabled(true);
                    }
                };
                    worker.execute();




            }
        });
        sBtn.setBackground(themeColor);
        sBtn.setFont(new Font(mainFont, Font.BOLD, 20));
        sBtn.setForeground(Color.white);
        sBtn.setFocusable(false);
        sBtn.setBorder(null);
        sBtn.setVisible(true);
        //When the button is hovered
        sBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (sBtn.isEnabled())
                    sBtn.setBackground(themeColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                sBtn.setBackground(themeColor);
            }
        });

        rBtn = new JButton("Reset");
        rBtn.setBounds((WIDTH-gridWIDTH -180)/2, 120, 180,50);
        rBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sBtn.setEnabled(false);
                rBtn.setEnabled(false);
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    public Void doInBackground() {
                        gv.drawGrid();

                        return null;
                    }
                    @Override
                    protected void done() {
                        System.out.println("Done");
                        sBtn.setEnabled(true);
                        rBtn.setEnabled(true);
                    }
                };
                worker.execute();
            }
        });
        rBtn.setBackground(themeColor);
        rBtn.setFont(new Font(mainFont, Font.BOLD, 20));
        rBtn.setForeground(Color.white);
        rBtn.setFocusable(false);
        rBtn.setBorder(null);
        rBtn.setVisible(true);
        //When the button is hovered
        rBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (rBtn.isEnabled())
                    rBtn.setBackground(themeColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                rBtn.setBackground(themeColor);
            }
        });



        bp.add(sBtn);
        bp.add(rBtn);


        f.add(p);
        f.add(bp);

        f.setVisible(true);

    }

    public static void main(String[] args) {
        GraphVisualizingScreen gvs = new GraphVisualizingScreen();
    }

    public void gridPaint(MouseEvent e){
        if (e.getPoint().x >= 32 && e.getPoint().y >= 32 &&
                e.getPoint().x <= 958 && e.getPoint().y <= 658){
            if (e.getButton() == MouseEvent.BUTTON1 && e.isShiftDown()) gv.clickState = 2;
            else if (e.getButton() == MouseEvent.BUTTON1 && e.isAltDown()) gv.clickState = 4;
            else if (e.getButton() == MouseEvent.BUTTON1) gv.clickState = 1;
            else if (e.getButton() == MouseEvent.BUTTON3) gv.clickState = 0;
            mousePos = e.getPoint();
            gv.mPos = mousePos;
            gv.Update();
        }
    }
    public void gridPaint2(MouseEvent e){
        if (e.getPoint().x >= 32 && e.getPoint().y >= 32 &&
                e.getPoint().x <= 958 && e.getPoint().y <= 658){
            if (e.getButton() == MouseEvent.BUTTON1 && e.isShiftDown()) gv.clickState = 2;
            else if (e.getButton() == MouseEvent.BUTTON1 && e.isAltDown()) gv.clickState = 4;
            mousePos = e.getPoint();
            gv.mPos = mousePos;
            gv.Update();
        }
    }
}
