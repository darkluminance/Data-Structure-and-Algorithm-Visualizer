package Stacc;


import MenuScreens.DataStructure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class Staccc extends JPanel{
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int win_WIDTH = 1000;

    public Color themeColor  = new Color(110,217,161);
    public Color BGColor  = Color.darkGray;
    public Color blueColor  = new Color(77, 171, 213);
    public Color pinkColor  = new Color(226, 86, 186);
    public Color purpleColor  = new Color(128, 86, 226);
    public Color orangeColor  = new Color(226, 160, 86);
    Color[] arrayColor;       //Stores color of each item representing array elements while searching

    Stack<String> arr = new Stack<>();
    String[] stackArray = new String[10];

    public int stackSize = 10;
    public int topPos = 0;
    public int numtoact = 0;
    public int animSpeed = 1000;                   //Initial speed of the sorting animation


    public String statusText = "";

    JFrame f;
    JPanel panel, btnPanel;
    JButton startBtn, resetBtn, bottomBtn, popBtn;
    JTextField input;
    JLabel  datainputtext, actiontext, speedText;

    public String mainFont = "Century Gothic";

    public Staccc(){
        f = new JFrame("Stack Visualization");
        f.setSize(WIDTH, HEIGHT);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Will close when close BUTTON pressed
        f.setResizable(false);      //Resizing will destroy the ratio of grids

        panel = new JPanel();
        panel.setBounds(0,0, win_WIDTH+10, HEIGHT);
        panel.setBackground(Color.darkGray);
        panel.setLayout(new GridLayout(1,100));        //Cols will be equal to max array size
        panel.setVisible(true);
        panel.addMouseWheelListener(this::mouseWheelMoved);

        btnPanel = new JPanel();
        btnPanel.setBounds(win_WIDTH+10,0, WIDTH - 10 - win_WIDTH, HEIGHT);
        btnPanel.setBackground(BGColor.darker());
        btnPanel.setLayout(null);
        btnPanel.setVisible(true);
        btnPanel.addMouseWheelListener(this::mouseWheelMoved);


        int pos = 50;

        resetBtn = new JButton("Reset");
        resetBtn.setBounds(40, pos, 180,40);
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusText = "Array has been reset";
                Reset(stackSize);
            }
        });
        resetBtn.setBackground(themeColor);
        resetBtn.setFont(new Font(mainFont, Font.BOLD, 15));
        resetBtn.setForeground(Color.white);
        resetBtn.setFocusable(false);
        resetBtn.setBorder(null);
        resetBtn.setVisible(true);
        //When the button is hovered
        resetBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (resetBtn.isEnabled())
                    resetBtn.setBackground(themeColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                resetBtn.setBackground(themeColor);
            }
        });
        pos += 50;

        datainputtext = new JLabel("Enter Value to push: ");
        datainputtext.setBounds(40, pos, 280, 50);
        datainputtext.setFont(new Font(mainFont, Font.PLAIN, 15));
        datainputtext.setForeground(Color.white);
        pos+= 50;

        input = new JTextField(Integer.toString(numtoact));
        input.setBorder(BorderFactory.createLineBorder(Color.white));
        input.setBounds(40, pos, 180, 40);
        input.setBackground(new Color(102,102,102));
        input.setForeground(Color.white);
        input.setFont(new Font(mainFont, Font.BOLD, 15));
        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        pos+= 80;

        startBtn = new JButton("Push");
        startBtn.setBounds(40, pos, 180,40);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(arr.size()+1 <= stackSize){
                    String str = input.getText();
                    arr.push(str);
                    stackArray[topPos] = str;
                    arrayColor[topPos] = orangeColor;
                    topPos++;
                    statusText = "Pushed " + str + " into the stack";
                    Update();
                }else{
                    JOptionPane.showMessageDialog(f,"Crosses maximum stack size!!");
                    statusText = "Cannot push because the maximum stack size exceeds";
                    Update();
                }
            }
        });
        startBtn.setBackground(themeColor);
        startBtn.setFont(new Font(mainFont, Font.BOLD, 15));
        startBtn.setForeground(Color.white);
        startBtn.setFocusable(false);
        startBtn.setBorder(null);
        startBtn.setVisible(true);
        //When the button is hovered
        startBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (startBtn.isEnabled())
                    startBtn.setBackground(themeColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                startBtn.setBackground(themeColor);
            }
        });
        pos += 60;

        popBtn = new JButton("Pop");
        popBtn.setBounds(40, pos, 180,40);
        popBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    statusText = "Popped " + arr.peek() + " from the stack";
                    arr.pop();
                    stackArray[topPos-1] = "";
                    arrayColor[topPos-1] = Color.white;
                    topPos--;
                    Update();
                }catch (Exception eee){
                    System.out.println(eee.getMessage());
                    JOptionPane.showMessageDialog(f,"Stack is empty!!! Nothing to pop.");
                    statusText = "Stack is empty!!! Nothing to pop.";
                    Update();
                }
            }
        });
        popBtn.setBackground(themeColor);
        popBtn.setFont(new Font(mainFont, Font.BOLD, 15));
        popBtn.setForeground(Color.white);
        popBtn.setFocusable(false);
        popBtn.setBorder(null);
        popBtn.setVisible(true);
        //When the button is hovered
        popBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (popBtn.isEnabled())
                    popBtn.setBackground(themeColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                popBtn.setBackground(themeColor);
            }
        });
        pos += 60;


        bottomBtn = new JButton("Back");
        bottomBtn.setBounds(40, 600, 180,40);
        bottomBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new DataStructure();
            }
        });
        bottomBtn.setBackground(Color.darkGray.darker());
        bottomBtn.setFont(new Font(mainFont, Font.BOLD, 15));
        bottomBtn.setForeground(Color.white);
        bottomBtn.setFocusable(false);
        bottomBtn.setBorder(null);
        bottomBtn.setVisible(true);
        //When the button is hovered
        bottomBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (bottomBtn.isEnabled())
                    bottomBtn.setBackground(Color.darkGray.darker().darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                bottomBtn.setBackground(Color.darkGray.darker());
            }
        });


        btnPanel.add(resetBtn);
        btnPanel.add(datainputtext);
        btnPanel.add(input);
        btnPanel.add(startBtn);
        btnPanel.add(popBtn);
        btnPanel.add(bottomBtn);

        panel.add(this);

        f.add(btnPanel);
        f.add(panel);

        f.setVisible(true);   //Visible

        Reset(stackSize);
    }

    public static void main(String[] args) {
        Staccc as = new Staccc();
    }

    //Each time repaint is called, this function runs
    @Override
    public void paintComponent(Graphics g){
        Graphics2D graphics = (Graphics2D)g;

        //For smoother edges, turning on Anti Aliasing
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(graphics);

        g.setFont(new Font("Century Gothic", Font.PLAIN, 18));
        graphics.setColor(BGColor);              //BG color
        graphics.fillRect(0,0, WIDTH,HEIGHT);     //Background

        int pos = 300;        //X position of first bar


        graphics.setColor(Color.white);
        graphics.drawString(statusText, 28, 50);
        graphics.drawLine(pos, 100, pos+100, 100);
        graphics.drawLine(pos+300, 100, pos+400, 100);
        graphics.drawLine(pos+100, 100, pos+100, 600);
        graphics.drawLine(pos+300, 100, pos+300, 600);
        graphics.drawLine(pos+100, 600, pos+300, 600);

        pos = 410;
        int yPos = 550;
        for (int i = 0; i<stackSize; i++){
            graphics.setColor(arrayColor[i]);
            if (topPos - 1 == i){
                graphics.setColor(blueColor);
            }
            graphics.drawString(stackArray[i], pos+50,yPos+25);
            graphics.drawRect(pos, yPos, 180, 40);

            if (i == topPos-1){
                graphics.setColor(Color.white);
                graphics.drawLine(pos+200, yPos+20, pos+400, yPos+20);
                graphics.drawString("Top of the stack", pos+420, yPos+25);
            }
            yPos-=50;

        }

    }


    public void Reset(int n){
        this.stackSize = n;       //Space between each bar is 2 pixels
        this.topPos = 0;

        this.arr = new Stack<>();
        this.arrayColor = new Color[n];

        for (int i = 0; i<stackSize; i++){
            arrayColor[i] = Color.white;
            stackArray[i] = "";
        }

        resetColors();

    }
    public void resetColors(){
        for (int i = 0; i<stackSize; i++){
            arrayColor[i] = Color.white;
            Update();
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
    //Mouse scroll event
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.isControlDown())
        {
            if (e.getWheelRotation() < 0) {
                animSpeed+= 100;
            } else {
                if (animSpeed - 100 >= 1)    animSpeed-= 100;
                else    animSpeed = 1;
            }
        }else if(e.isShiftDown()){
            if (e.getWheelRotation() < 0) {
                animSpeed+= 10;
            } else {
                if (animSpeed - 10 >= 1)    animSpeed-= 10;
                else    animSpeed = 1;
            }
        }else{
            if (e.getWheelRotation() < 0) {
                animSpeed+= 1;
            } else {
                if (animSpeed - 1 >= 1)    animSpeed-= 1;
            }
        }
        speedText.setText( "Speed: " + animSpeed + " ms");
    }

}

