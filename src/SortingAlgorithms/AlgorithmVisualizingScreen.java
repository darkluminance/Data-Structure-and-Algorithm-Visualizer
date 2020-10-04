package SortingAlgorithms;

import GraphAlgorithms.GraphVisualizingScreen;
import MenuScreens.Algorithms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AlgorithmVisualizingScreen implements ActionListener {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int win_WIDTH = 1000;

    public Color themeColor  = new Color(110,217,161);

    public int arraySize = 20;

    JFrame f;
    JPanel panel, btnPanel;
    JLabel statusText, textboxText, comparisonText, slow, fast, speedText;
    JButton startBtn, generateArrayBtn, bottomBtn;
    SortArray sortarray;
    JComboBox<String> jComboBox;
    JTextField jTextField;
    JLabel speedSlider;

    String[] algorithms = {
            "Bubble sort",
            "Insertion sort",
            "Selection sort",
            "Merge sort",
            "Quick sort (Not added)",
            "Counting sort (Not added)",
    };

    public String mainFont = "Century Gothic";

    public AlgorithmVisualizingScreen(){
        f = new JFrame("Sorting Visualization");

        sortarray = new SortArray(arraySize);

        panel = new JPanel();
        panel.setBounds(0,0, win_WIDTH+10, HEIGHT);
        panel.setBackground(Color.darkGray);
        panel.setLayout(new GridLayout(1,100));        //Cols will be equal to max array size
        panel.setVisible(true);
        panel.addMouseWheelListener(this::mouseWheelMoved);

        btnPanel = new JPanel();
        btnPanel.setBounds(win_WIDTH+10,0, WIDTH - 10 - win_WIDTH, HEIGHT);
        btnPanel.setBackground(sortarray.BGColor.darker());
        btnPanel.setLayout(null);
        btnPanel.setVisible(true);
        btnPanel.addMouseWheelListener(this::mouseWheelMoved);


        f.setSize(WIDTH, HEIGHT);   //Setting dimensions

        //Using no layout managers.
        // DON'T EVEN THINK OF TOUCHING THIS.
        // PROGRAM DOESN'T WORK FOR SOME REASON IF COMMENTED
        f.setLayout(null);

        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Will close when close BUTTON pressed
        f.setResizable(false);      //Resizing will destroy the ratio of grids

        textboxText = new JLabel("<html>Enter array size<br>(5-300):</html>");
        textboxText.setBounds(40, 30, 180, 60);
        textboxText.setFont(new Font(mainFont, Font.PLAIN, 20));
        textboxText.setForeground(Color.white);

        jTextField = new JTextField(Integer.toString(arraySize));
        jTextField.setBorder(BorderFactory.createLineBorder(Color.white));
        jTextField.setBounds(40, 100, 180, 50);
        jTextField.setBackground(new Color(102,102,102));
        jTextField.setForeground(Color.white);
        jTextField.setFont(new Font(mainFont, Font.BOLD, 20));
        jTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int a = Integer.parseInt(jTextField.getText());

                    if (Integer.parseInt(jTextField.getText())>=5 && Integer.parseInt(jTextField.getText())<=300){
                        arraySize = Integer.parseInt(jTextField.getText());
                        System.out.println(arraySize);
                        startBtn.setEnabled(true);
                        statusText.setText("Press Generate");
                        comparisonText.setText("");
                    }else{
                        throw new NumberFormatException("Invalid number");
                    }
                }catch (NumberFormatException ne){
                    System.out.println("Parbona");
                    startBtn.setEnabled(false);
                    statusText.setText("Invalid size format");
                    comparisonText.setText("");
                }
            }
        });

        generateArrayBtn = new JButton("Generate");
        generateArrayBtn.setBounds(40, 170, 180,50);
        generateArrayBtn.addActionListener(this);
        generateArrayBtn.setBackground(themeColor);
        generateArrayBtn.setFont(new Font(mainFont, Font.BOLD, 18));
        generateArrayBtn.setForeground(Color.white);
        generateArrayBtn.setFocusable(false);
        generateArrayBtn.setBorder(null);
        generateArrayBtn.setVisible(true);
        //When the button is hovered
        generateArrayBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (generateArrayBtn.isEnabled())
                    generateArrayBtn.setBackground(themeColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                generateArrayBtn.setBackground(themeColor);
            }
        });

        startBtn = new JButton("Start");
        startBtn.setBounds(40, 240, 180,50);
        startBtn.addActionListener(this);
        startBtn.setBackground(themeColor);
        startBtn.setFont(new Font(mainFont, Font.BOLD, 18));
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

        jComboBox = new JComboBox<String>(algorithms);
        jComboBox.setBounds(40, 305, 180, 50);
        jComboBox.setFont(new Font(mainFont, Font.BOLD, 15));
        jComboBox.setBackground(themeColor);
        jComboBox.setForeground(Color.white);
        /*jComboBox.setBackground(Color.darkGray);
        //jComboBox.setForeground(new Color(110,217,161));
        jComboBox.setForeground(Color.white);*/
        //jComboBox.setUI(ColorArrowUI.createUI(jComboBox));
        jComboBox.setRenderer(new MyListCellRenderer(themeColor));
        jComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                /*if (jComboBox.getSelectedItem() == "Merge sort"){
                    speedText.setText( "Speed: " + sortarray.animSpeed*2 + " ms");
                }
                else*/
                    speedText.setText( "Speed: " + sortarray.animSpeed + " ms");
            }
        });

        jComboBox.setSelectedIndex(0);
        //jComboBox.setFocusable(false);

        //jComboBox.addItemListener(this);
        jComboBox.setVisible(true);


        speedSlider = new JLabel("<html>Use the mouse scroll wheel to change the animation delay.<br>" +
                " Use the Ctrl or Shift keys along with the scroll wheel to change by bigger amount.</html>");
        speedSlider.setBounds(40, 350, 180, 150);
        speedSlider.setFont(new Font(mainFont, Font.PLAIN, 13));
        speedSlider.setForeground(Color.white);

        speedText = new JLabel( "Speed: " + sortarray.animSpeed + " ms");
        speedText.setBounds(40, 480, 180, 50);
        speedText.setFont(new Font(mainFont, Font.PLAIN, 18));
        speedText.setForeground(Color.white);

        statusText = new JLabel("Unsorted");
        statusText.setBounds(40, 520, 180, 50);
        statusText.setFont(new Font(mainFont, Font.BOLD, 20));
        statusText.setForeground(Color.white);

        comparisonText = new JLabel("");
        comparisonText.setBounds(40, 550, 180, 50);
        comparisonText.setFont(new Font(mainFont, Font.PLAIN, 18));
        comparisonText.setForeground(Color.white);

        bottomBtn = new JButton("Back");
        bottomBtn.setBounds(40, 620, 180,50);
        bottomBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //f.setVisible(false);
                f.dispose();
                new Algorithms();
                System.out.println("Came here");
            }
        });
        bottomBtn.setBackground(Color.darkGray.darker());
        bottomBtn.setFont(new Font(mainFont, Font.BOLD, 18));
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

        btnPanel.add(jTextField);
        btnPanel.add(generateArrayBtn);
        btnPanel.add(startBtn);
        btnPanel.add(jComboBox);
        btnPanel.add(statusText);
        btnPanel.add(textboxText);
        btnPanel.add(comparisonText);
        btnPanel.add(speedSlider);
        btnPanel.add(speedText);
        btnPanel.add(bottomBtn);

        panel.add(sortarray);


        f.add(btnPanel);
        f.add(panel);

        f.setVisible(true);   //Visible

    }

    public static void main(String[] args) {
        AlgorithmVisualizingScreen avs = new AlgorithmVisualizingScreen();
    }


    //Mouse scroll event
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.isControlDown())
        {
            if (e.getWheelRotation() < 0) {
                sortarray.animSpeed+= 100;
            } else {
                if (sortarray.animSpeed - 100 >= 1)    sortarray.animSpeed-= 100;
                else    sortarray.animSpeed = 1;
            }
        }else if(e.isShiftDown()){
            if (e.getWheelRotation() < 0) {
                sortarray.animSpeed+= 10;
            } else {
                if (sortarray.animSpeed - 10 >= 1)    sortarray.animSpeed-= 10;
                else    sortarray.animSpeed = 1;
            }
        }else{
            if (e.getWheelRotation() < 0) {
                sortarray.animSpeed+= 1;
            } else {
                if (sortarray.animSpeed - 1 >= 1)    sortarray.animSpeed-= 1;
            }
        }
        speedText.setText( "Speed: " + sortarray.animSpeed + " ms");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //This part of the code is added so that the program will keep painting
        //even when the algorithm is called
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                // Call complicated code here
                generateArrayBtn.setEnabled(false);     //So that user can't press Generate during any operation
                jTextField.setEnabled(false);           //So that user can't use it during any operation

                /*if (jComboBox.getSelectedItem() == "Merge sort"){
                    speedText.setText( "Speed: " + sortarray.animSpeed*2 + " ms");
                }
                else*/
                    speedText.setText( "Speed: " + sortarray.animSpeed + " ms");

                if (e.getSource() == startBtn){
                    /*
                    Check if the program is sorting when the start/stop button is pressed.
                    If true, then stop the sorting by changing the boolean and change the button text
                    If false, then change status to sorting..., start the sorting algorithm code and change
                    button text to 'Stop'
                    */
                    if (sortarray.isSorting){
                        startBtn.setText("Start");
                        statusText.setText("Stopped");
                        sortarray.isSorting = false;

                        return null;
                    }else{
                        System.out.println("Sort started");
                        statusText.setText("Sorting...");
                        startBtn.setText("Stop");
                        //jComboBox.setEnabled(false);


                        //Sort based on the dropdown menu selection
                        if (jComboBox.getSelectedItem() == "Bubble sort"){
                            new SortingAlgorithms().bubbleSort(sortarray);
                        }else if (jComboBox.getSelectedItem() == "Insertion sort"){
                            new SortingAlgorithms().insertionSort(sortarray);
                        }else if (jComboBox.getSelectedItem() == "Selection sort"){
                            new SortingAlgorithms().selectionSort(sortarray);
                        }else if (jComboBox.getSelectedItem() == "Merge sort"){
                            new SortingAlgorithms().sortdeMerge(sortarray);
                        }else if (jComboBox.getSelectedItem() == "Quick sort"){
                            //new SortingAlgorithms.SortingAlgorithms().sortdeMerge(sortarray);
                        }else if (jComboBox.getSelectedItem() == "Counting sort"){
                            //new SortingAlgorithms.SortingAlgorithms().sortdeMerge(sortarray);
                        }
                    }
                }else if (e.getSource() == generateArrayBtn){
                    try{
                        int a = Integer.parseInt(jTextField.getText());

                        if (Integer.parseInt(jTextField.getText())>=5 && Integer.parseInt(jTextField.getText())<=300){
                            arraySize = Integer.parseInt(jTextField.getText());
                            System.out.println(arraySize);
                            //Generate new array
                            statusText.setText("Generating...");
                            startBtn.setEnabled(false);
                            sortarray.GenerateRandomArray(arraySize);

                            statusText.setText("Unsorted");
                            startBtn.setEnabled(true);
                            comparisonText.setText("");
                        }else{
                            throw new NumberFormatException("Invalid number");
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Parbona");
                        startBtn.setEnabled(false);
                        statusText.setText("Invalid size format");
                        comparisonText.setText("");
                    }

                }
                return null;
                // If you want to return something other than null, change
                // the generic type to something other than Void.
                // This method's return value will be available via get() once the
                // operation has completed.
            }


            @Override
            protected void done() {
                // get() would be available here if you want to use it

                //After the operation is done, reenable the items again
                generateArrayBtn.setEnabled(true);
                jTextField.setEnabled(true);
                //jComboBox.setEnabled(true);
                startBtn.setText("Start");
                sortarray.isSorting = false;
                speedText.setText( "Speed: " + sortarray.animSpeed + " ms");

                //Change the status text
                if (e.getSource() == startBtn){
                    if (sortarray.IsSorted()){
                        statusText.setText("Sorted");
                        comparisonText.setText(sortarray.comparisons + " comparisons");
                    }
                }else if(e.getSource() == generateArrayBtn){
                    //statusText.setText("Unsorted");
                    startBtn.setEnabled(true);
                }
            }
        };
        worker.execute();       //Keep executing the codes while operation is being performed

    }
}

//Custom dropdown menu settings
class MyListCellRenderer extends DefaultListCellRenderer {
    public Color themeColor;

    public MyListCellRenderer(Color t) {
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
