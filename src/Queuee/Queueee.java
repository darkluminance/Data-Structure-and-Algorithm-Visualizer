package Queuee;


import MenuScreens.DataStructure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;

public class Queueee extends JPanel{
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

    //Stack<String> arr = new Stack<>();
    Queue<String> arr = new LinkedList<>();
    String[] queueArray = new String[10];
    Queue<Queuepair> ar = new LinkedList<>();

    Toolkit t=Toolkit.getDefaultToolkit();

    Image[] queueImages = {
            t.getImage("./queues/1.png"),
            t.getImage("./queues/2.png"),
            t.getImage("./queues/3.png"),
            t.getImage("./queues/4.png"),
            t.getImage("./queues/5.png"),
    };
    Image deskgirl = t.getImage("./queues/deskgirl.png");
    Image speechbubble = t.getImage("./queues/speechbubble.png");

    public int queueSize = 10;
    public int topPos = 0;
    public int queueS = 0;
    public int numtoact = 0;
    public int animSpeed = 1000;                   //Initial speed of the sorting animation


    public String statusText = "";

    JFrame f;
    JPanel panel, btnPanel;
    JButton startBtn, resetBtn, bottomBtn, popBtn;
    JTextField input;
    JLabel  datainputtext, actiontext, speedText;

    public String mainFont = "Century Gothic";

    public class Queuepair{
        Image im;
        String val;

        public Queuepair(Image i, String v){
            this.im = i;
            this.val = v;
        }
    }

    public Queueee(){
        f = new JFrame("Queue Visualization");
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
                statusText = "Queue has been reset";
                Reset(queueSize);
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

        datainputtext = new JLabel("Enter Value to add: ");
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

        startBtn = new JButton("Enqueue");
        startBtn.setBounds(40, pos, 180,40);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = input.getText();
                if(str.length() > 6){
                    JOptionPane.showMessageDialog(f,"Data size is too big!!");
                    Update();
                    return;
                }
                if(str.equals("")){
                    JOptionPane.showMessageDialog(f,"There is no data!!!");
                    Update();
                    return;
                }
                if(arr.size()+1 <= queueSize){
                    arr.add(str);
                    int rand = (int)(Math.random() * (4 + 1) + 0);
                    ar.add(new Queuepair(queueImages[rand], str));
                    statusText = "Added " + str + " into the queue";
                    Update();
                }else{
                    JOptionPane.showMessageDialog(f,"Crosses maximum queue size!!");
                    statusText = "Cannot add because the maximum stack size exceeds";
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

        popBtn = new JButton("Dequeue");
        popBtn.setBounds(40, pos, 180,40);
        popBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!arr.isEmpty()){
                    statusText = "Removed " + arr.peek() + " from the queue";
                    arr.remove();
                    ar.remove();
                    Update();
                }else{
                    JOptionPane.showMessageDialog(f,"Queue is empty!!! Nothing to remove.");
                    statusText = "Queue is empty!!! Nothing to remove.";
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

        Reset(queueSize);
    }

    public static void main(String[] args) {
        Queuee.Queueee as = new Queuee.Queueee();
    }

    //Each time repaint is called, this function runs
    @Override
    public void paintComponent(Graphics g){
        Graphics2D graphics = (Graphics2D)g;

        //For smoother edges, turning on Anti Aliasing
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(graphics);
        g.setFont(new Font("Century Gothic", Font.PLAIN, 13));
        graphics.setColor(themeColor);              //BG color
        graphics.fillRect(0,0, WIDTH,HEIGHT);     //Background

        int pos = 300;        //X position of first bar

        graphics.drawImage(deskgirl,50,250,deskgirl.getWidth(this)/3,deskgirl.getHeight(this)/3,this);

        graphics.setColor(Color.black);
        pos = 180;
        int yPos = 280;
        for (Queuepair item: ar) {
            Image qi = item.im;
            String va = item.val;
            graphics.drawImage(qi, pos, yPos,qi.getWidth(this)/3,qi.getHeight(this)/3, this);
            graphics.drawImage(speechbubble, pos+10, yPos-65,speechbubble.getWidth(this)/2,speechbubble.getHeight(this)/2,this);
            graphics.drawString(va, pos+20, yPos-42);
            pos+=80;
        }


    }


    public void Reset(int n){
        this.queueSize = n;       //Space between each bar is 2 pixels
        this.topPos = 0;

        this.arr = new LinkedList<>();
        this.arrayColor = new Color[n];

        for (int i = 0; i<queueSize; i++){
            arrayColor[i] = Color.white;
            queueArray[i] = "";
        }

        resetColors();

    }
    public void resetColors(){
        for (int i = 0; i<queueSize; i++){
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

