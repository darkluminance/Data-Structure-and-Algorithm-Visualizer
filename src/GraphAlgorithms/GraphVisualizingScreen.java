package GraphAlgorithms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GraphVisualizingScreen {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 728;
    public static final int win_WIDTH = 1000;

    public Color themeColor  = new Color(110,217,161);

    JFrame f;
    JPanel p;
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
        p.setBounds(0,0,WIDTH, HEIGHT);
        p.setLayout((new GridLayout(1,1 )));
        p.add(gv);
        p.setVisible(true);
        p.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                gridPaint(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
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
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                gridPaint(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });



        f.add(p);

        f.setVisible(true);

    }

    public static void main(String[] args) {
        GraphVisualizingScreen gvs = new GraphVisualizingScreen();
    }

    public void gridPaint(MouseEvent e){
        if (e.getPoint().x >= 31 && e.getPoint().y >= 31 &&
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
}
