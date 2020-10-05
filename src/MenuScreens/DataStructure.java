package MenuScreens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataStructure extends JFrame {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    JFrame c;
    JLabel label1;
    JPanel pp;
    JButton btn1,btn2,btn3;
    Font f,f1;

    public DataStructure(){
        c = new JFrame("Data Structures");
        c.setBackground(Color.darkGray);
        c.setBounds(0,0,WIDTH,HEIGHT);
        c.setLocationRelativeTo(null);                  //Sets the frame to the middle of the screen
        c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c.setLayout(null);

        pp = new JPanel();
        pp.setBounds(0,0, WIDTH, HEIGHT);
        pp.setBackground(Color.darkGray);
        pp.setLayout(null);

        f=new Font("Futura",Font.BOLD,40);
        f1=new Font("Century Gothic",Font.PLAIN,20);

        label1=new JLabel("Data Structures", SwingConstants.CENTER);
        label1.setBounds((WIDTH/2)-250,100,500,50);
        label1.setFont(f);
        label1.setForeground(Color.white);
        pp.add(label1);

        btn1=new JButton("Stack (Not yet added)");
        btn1.setBounds((WIDTH/2)-150,250,300,50);
        btn1.setForeground(Color.white);
        btn1.setFocusable(false);
        btn1.setBorder(null);
        btn1.setBackground(Color.darkGray.darker());
        btn1.setFont(f1);
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //c.dispose();
                //new AlgorithmVisualizingScreen();
            }
        });
        //When the button is hovered
        btn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn1.isEnabled())
                    btn1.setBackground(Color.darkGray.darker().darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn1.setBackground(Color.darkGray.darker());
            }
        });


        pp.add(btn1);

        btn2=new JButton("Queue (Not yet added)");
        btn2.setBounds((WIDTH/2)-150,325,300,50);
        btn2.setForeground(Color.white);
        btn2.setBackground(Color.darkGray.darker());
        btn2.setFocusable(false);
        btn2.setBorder(null);
        btn2.setFont(f1);
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //c.dispose();
                //new GraphVisualizingScreen();
            }
        });
        //When the button is hovered
        btn2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn2.isEnabled())
                    btn2.setBackground(Color.darkGray.darker().darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn2.setBackground(Color.darkGray.darker());
            }
        });
        pp.add(btn2);

        btn3=new JButton("Back");
        btn3.setBounds((WIDTH/2)-150,400,300,50);
        btn3.setForeground(Color.white);
        btn3.setBackground(Color.darkGray.darker());
        btn3.setFocusable(false);
        btn3.setBorder(null);
        btn3.setFont(f1);
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.dispose();
                new Menu();
            }
        });
        //When the button is hovered
        btn3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn3.isEnabled())
                    btn3.setBackground(Color.darkGray.darker().darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn2.setBackground(Color.darkGray.darker());
            }
        });
        pp.add(btn3);

        pp.setVisible(true);
        c.add(pp);
        c.setVisible(true);
    }
}
