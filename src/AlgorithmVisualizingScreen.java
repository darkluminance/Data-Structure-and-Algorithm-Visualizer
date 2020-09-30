import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class AlgorithmVisualizingScreen implements ActionListener {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int win_WIDTH = 1000;

    public Color themeColor  = new Color(110,217,161);

    public int arraySize = 100;

    JFrame f;
    JPanel panel, btnPanel;
    JLabel statusText, textboxText, comparisonText, slow, fast, speedText;
    JButton startBtn, generateArrayBtn;
    SortArray sortarray;
    JComboBox<String> jComboBox;
    JTextField jTextField;
    JSlider speedSlider;

    String[] algorithms = {
            "Bubble sort",
            "Insertion sort",
            "Selection sort",
            "Merge sort"
    };

    public AlgorithmVisualizingScreen(){
        f = new JFrame("Sorting Visualization");

        sortarray = new SortArray(arraySize);

        panel = new JPanel();
        panel.setBounds(0,0, win_WIDTH+10, HEIGHT);
        panel.setBackground(Color.darkGray);
        panel.setLayout(new GridLayout(1,100));        //Cols will be equal to max array size
        panel.setVisible(true);

        btnPanel = new JPanel();
        btnPanel.setBounds(win_WIDTH+10,0, WIDTH - 10 - win_WIDTH, HEIGHT);
        btnPanel.setBackground(sortarray.BGColor);
        btnPanel.setLayout(null);
        btnPanel.setVisible(true);

        f.setSize(WIDTH, HEIGHT);   //Setting dimensions

        //Using no layout managers.
        // DON'T EVEN THINK OF TOUCHING THIS.
        // PROGRAM DOESN'T WORK FOR SOME REASON IF COMMENTED
        f.setLayout(null);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Will close when close BUTTON pressed
        f.setResizable(false);      //Resizing will destroy the ratio of grids

        textboxText = new JLabel("<html>Enter array size<br>(5-300):</html>");
        textboxText.setBounds(30, 30, 180, 60);
        textboxText.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        textboxText.setForeground(Color.white);

        jTextField = new JTextField(Integer.toString(arraySize));
        jTextField.setBorder(BorderFactory.createLineBorder(Color.white));
        jTextField.setBounds(30, 100, 200, 50);
        jTextField.setBackground(new Color(102,102,102));
        jTextField.setForeground(Color.white);
        jTextField.setFont(new Font("Century Gothic", Font.BOLD, 20));
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
        generateArrayBtn.setBounds(30, 170, 200,50);
        generateArrayBtn.addActionListener(this);
        generateArrayBtn.setBackground(themeColor);
        generateArrayBtn.setFont(new Font("Century Gothic", Font.BOLD, 20));
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
        startBtn.setBounds(30, 240, 200,50);
        startBtn.addActionListener(this);
        startBtn.setBackground(themeColor);
        startBtn.setFont(new Font("Century Gothic", Font.BOLD, 20));
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
        jComboBox.setBounds(30, 305, 200, 50);
        jComboBox.setFont(new Font("Century Gothic", Font.BOLD, 18));
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
                if (jComboBox.getSelectedItem() == "Merge sort"){
                    speedText.setText( "Speed: " + speedSlider.getValue()*2 + " ms");
                }
                else
                    speedText.setText( "Speed: " + speedSlider.getValue() + " ms");
            }
        });

        jComboBox.setSelectedIndex(0);
        //jComboBox.setFocusable(false);

        //jComboBox.addItemListener(this);
        jComboBox.setVisible(true);


        speedSlider = new JSlider(1,500);
        speedSlider.setBounds(30, 380, 200, 30);
        speedSlider.setBorder(null);
        speedSlider.setBackground(themeColor);
        speedSlider.setValue(2);
        speedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                sortarray.animSpeed = speedSlider.getValue();

                if (jComboBox.getSelectedItem() == "Merge sort"){
                    speedText.setText( "Speed: " + speedSlider.getValue()*2 + " ms");
                }
                else
                    speedText.setText( "Speed: " + speedSlider.getValue() + " ms");
            }
        });

        slow = new JLabel("Slow");
        slow.setBounds(220, 392, 50, 50);
        slow.setFont(new Font("Century Gothic", Font.PLAIN, 10));
        slow.setForeground(Color.white);

        fast = new JLabel("Fast");
        fast.setBounds(20, 392, 50, 50);
        fast.setFont(new Font("Century Gothic", Font.PLAIN, 10));
        fast.setForeground(Color.white);

        speedText = new JLabel( "Speed: " + speedSlider.getValue() + " ms");
        speedText.setBounds(30, 420, 200, 50);
        speedText.setFont(new Font("Century Gothic", Font.PLAIN, 18));
        speedText.setForeground(Color.white);

        statusText = new JLabel("Unsorted");
        statusText.setBounds(30, 470, 180, 50);
        statusText.setFont(new Font("Century Gothic", Font.BOLD, 20));
        statusText.setForeground(Color.white);

        comparisonText = new JLabel("");
        comparisonText.setBounds(30, 510, 180, 50);
        comparisonText.setFont(new Font("Century Gothic", Font.PLAIN, 18));
        comparisonText.setForeground(Color.white);




        btnPanel.add(jTextField);
        btnPanel.add(generateArrayBtn);
        btnPanel.add(startBtn);
        btnPanel.add(jComboBox);
        btnPanel.add(statusText);
        btnPanel.add(textboxText);
        btnPanel.add(comparisonText);
        btnPanel.add(speedSlider);
        btnPanel.add(slow);
        btnPanel.add(fast);
        btnPanel.add(speedText);

        panel.add(sortarray);


        f.add(btnPanel);
        f.add(panel);
        //new SortingAlgorithms().bubbleSort(sortarray);


        f.setVisible(true);   //Visible
    }

    public static void main(String[] args) {
        AlgorithmVisualizingScreen avs = new AlgorithmVisualizingScreen();
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

                if (jComboBox.getSelectedItem() == "Merge sort"){
                    speedText.setText( "Speed: " + speedSlider.getValue()*2 + " ms");
                }
                else
                    speedText.setText( "Speed: " + speedSlider.getValue() + " ms");

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
                        jComboBox.setEnabled(false);


                        //Sort based on the dropdown menu selection
                        if (jComboBox.getSelectedItem() == "Bubble sort"){
                            new SortingAlgorithms().bubbleSort(sortarray);
                        }else if (jComboBox.getSelectedItem() == "Insertion sort"){
                            new SortingAlgorithms().insertionSort(sortarray);
                        }else if (jComboBox.getSelectedItem() == "Selection sort"){
                            new SortingAlgorithms().selectionSort(sortarray);
                        }else if (jComboBox.getSelectedItem() == "Merge sort"){
                            new SortingAlgorithms().sortdeMerge(sortarray);
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
                            speedSlider.setValue(sortarray.animSpeed);

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

                //After the operation is done, reenable the generate button again
                generateArrayBtn.setEnabled(true);
                jComboBox.setEnabled(true);
                startBtn.setText("Start");
                sortarray.isSorting = false;


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
