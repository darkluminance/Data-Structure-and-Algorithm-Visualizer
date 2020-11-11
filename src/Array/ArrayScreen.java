package Array;


import MenuScreens.DataStructure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ArrayScreen extends JPanel{
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

    int[] arr;

    public int arraySize = 15;
    public int curpos = 0;
    public int numtoact = 0;
    public int targetindex = 0;
    public int comparisons = 0;                 //Counts the no of comparisons in sort
    public int animSpeed = 1000;                   //Initial speed of the sorting animation

    public boolean isSearching = false;
    public boolean IsFound = false;

    public String searchingText = "";

    JFrame f;
    JPanel panel, btnPanel;
    JButton startBtn, resetBtn, bottomBtn;
    JComboBox<String> actiontypeBox;
    JTextField input, indexInput;
    JLabel indexinputtext, speedText, dateinputtext, actiontext;


    String[] actiontype = {
            "Add item",
            "Show at index",
    };

    int[] dataSize = {
            4,
            1,
            8,
    };

    public String mainFont = "Century Gothic";

    public ArrayScreen(){
        f = new JFrame("Array Visualization");
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
                searchingText = "Array has been reset";
                ResetArray(arraySize);
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

        actiontext = new JLabel("Action type");
        actiontext.setBounds(40, pos, 280, 50);
        actiontext.setFont(new Font(mainFont, Font.PLAIN, 15));
        actiontext.setForeground(Color.white);
        pos+= 50;

        actiontypeBox = new JComboBox<String>(actiontype);
        actiontypeBox.setBounds(40, pos, 180, 50);
        actiontypeBox.setFont(new Font(mainFont, Font.BOLD, 15));
        actiontypeBox.setBackground(themeColor);
        actiontypeBox.setForeground(Color.white);
        actiontypeBox.setRenderer(new MyListCellsRenderer(themeColor));
        actiontypeBox.setSelectedIndex(0);
        actiontypeBox.setVisible(true);
        pos += 80;

        indexinputtext = new JLabel("Enter Index: ");
        indexinputtext.setBounds(40, pos, 280, 50);
        indexinputtext.setFont(new Font(mainFont, Font.PLAIN, 15));
        indexinputtext.setForeground(Color.white);
        pos+= 50;

        indexInput = new JTextField(Integer.toString(targetindex));
        indexInput.setBorder(BorderFactory.createLineBorder(Color.white));
        indexInput.setBounds(40, pos, 180, 40);
        indexInput.setBackground(new Color(102,102,102));
        indexInput.setForeground(Color.white);
        indexInput.setFont(new Font(mainFont, Font.BOLD, 15));
        pos+= 35;

        dateinputtext = new JLabel("Enter Value to add: ");
        dateinputtext.setBounds(40, pos, 280, 50);
        dateinputtext.setFont(new Font(mainFont, Font.PLAIN, 15));
        dateinputtext.setForeground(Color.white);
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

        startBtn = new JButton("Perform Action");
        startBtn.setBounds(40, pos, 180,40);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actiontypeBox.getSelectedIndex() == 0){
                    resetColors();
                    int item = Integer.parseInt(input.getText());
                    int ind = Integer.parseInt(indexInput.getText());
                    addItem(item, ind);
                }else if (actiontypeBox.getSelectedIndex() == 1){
                    //Show the array
                    resetColors();
                    int index = Integer.parseInt(indexInput.getText());
                    searchingText = "Index is i = " + index + ". " + "\n" + "So memory location for " +
                            "that index will be: 100 + (i * 4) = " + (100 + (index*4));
                    arrayColor[index] = orangeColor;
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

        /*speedSlider = new JLabel("<html>Use the mouse scroll wheel to change the animation delay.<br>" +
                " Use the Ctrl or Shift keys along with the scroll wheel to change by bigger amount.</html>");
        speedSlider.setBounds(40, pos, 180, 150);
        speedSlider.setFont(new Font(mainFont, Font.PLAIN, 13));
        speedSlider.setForeground(Color.white);
        pos+= 135;

        speedText = new JLabel( "Speed: " + animSpeed + " ms");
        speedText.setBounds(40, pos, 180, 50);
        speedText.setFont(new Font(mainFont, Font.PLAIN, 18));
        speedText.setForeground(Color.white);
        pos+=69;*/


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
        btnPanel.add(actiontext);
        btnPanel.add(actiontypeBox);
        btnPanel.add(indexinputtext);
        btnPanel.add(indexInput);
        btnPanel.add(dateinputtext);
        btnPanel.add(input);
        btnPanel.add(startBtn);
        btnPanel.add(bottomBtn);

        panel.add(this);

        f.add(btnPanel);
        f.add(panel);

        f.setVisible(true);   //Visible

        ResetArray(arraySize);

    }


    public static void main(String[] args) {
        ArrayScreen as = new ArrayScreen();
    }

    public void addItem(int item, int indx){
        if (curpos>=arraySize){

        }
        try{
            arr[indx] = item;
            searchingText = "Added " + item + " to memory location " + (100 + 4* indx) + " or index " + indx;
            arrayColor[indx] = blueColor;
            curpos++;
            Update();
        }catch (Exception e){
            JOptionPane.showMessageDialog(f,e.getMessage());
            searchingText = "Index crosses array size!!!";
            Update();
        }
    }


    public void ResetArray(int n){
        this.arraySize = n;       //Space between each bar is 2 pixels
        this.curpos = 0;

        this.arr = new int[n];
        this.arrayColor = new Color[n];

        int min = 0;       //Minimum value of array element
        int max = 99;      //Maximum value of array element
        for (int i = 0; i<arraySize; i++){
            this.arr[i] = -1;
            arrayColor[i] = Color.white;
            Update();
        }
    }
    public void resetColors(){
        for (int i = 0; i<arraySize; i++){
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




    //Each time repaint is called, this function runs
    @Override
    public void paintComponent(Graphics g){
        Graphics2D graphics = (Graphics2D)g;

        //For smoother edges, turning on Anti Aliasing
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(graphics);

        graphics.setColor(BGColor);              //BG color
        graphics.fillRect(0,0, WIDTH,HEIGHT);     //Background

        int pos = 50;        //X position of first bar

        for (int i = 0; i<arraySize; i++){
            graphics.setColor(arrayColor[i]);
            graphics.fillRect(pos, HEIGHT/2, (win_WIDTH-100)/arraySize, (win_WIDTH-100)/arraySize);
            graphics.setColor(Color.black);
            graphics.drawRect(pos, HEIGHT/2, (win_WIDTH-100)/arraySize, (win_WIDTH-100)/arraySize);
            g.setFont(new Font("Century Gothic", Font.PLAIN, 18));
            if (arrayColor[i] == pinkColor){
                graphics.setColor(Color.white);
                g.setFont(new Font("Century Gothic", Font.PLAIN, 15));
            }
            if(arr[i] != -1)
                graphics.drawString(Integer.toString(arr[i]), pos+8, (HEIGHT/2)+20);

            graphics.setColor(Color.white);
            graphics.drawString("Memory Location: " , 50, (HEIGHT/2)-65);
            graphics.drawString(Integer.toString(100+i*dataSize[0]), pos+8, (HEIGHT/2)-20);
            graphics.drawString(Integer.toString(i), pos+5, (HEIGHT/2)+80);

            graphics.drawString(searchingText, 50, (HEIGHT/2)-220);
            pos += (win_WIDTH-100)/arraySize;       //X position of next array element

            g.setFont(new Font("Century Gothic", Font.BOLD, 28));

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



//Custom dropdown menu settings
class MyListCellsRenderer extends DefaultListCellRenderer {
    public Color themeColor;

    public MyListCellsRenderer(Color t) {
        setOpaque(true);  //Not transparent
        this.themeColor = t;
    }

    public Component getListCellRendererComponent(JList jc, Object val, int idx,
                                                  boolean isSelected, boolean cellHasFocus) {
        setText(val.toString());        //Set the texts to string
        setForeground(Color.white);     //Text color to white
        jc.setSelectionBackground(themeColor);      //Sets the BG of selected item
        jc.setSelectionForeground(Color.white);     //Sets the font color of selected item

        if (isSelected)
            setBackground(themeColor);      //Sets the BG of hovered item
        else
            setBackground(Color.darkGray);      //Sets the BG of non hovered items
        return this;
    }
}
