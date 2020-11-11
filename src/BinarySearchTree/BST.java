package BinarySearchTree;

import MenuScreens.DataStructure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class BST extends JPanel{
    int level = 1;
    public class node{
        node left;
        node right;
        node parent;
        int value;
        int posX, posY;


        public node(int val){
            this.value = val;
        }
        public node(){
            this.value = -1;
        }

        public void addN(node n){
            current = n;
            n.posY += 180;
            //sleep(500);
            Update();
            level++;
            if (level > 4){
                JOptionPane.showMessageDialog(f,"Height limit reached !!!");
                return;
            }
            if(n.value <= this.value){
                n.posX -= 420 / level;
                if (this.left == null){
                    n.parent = this;
                    this.left = n;
                }
                else
                    this.left.addN(n);
            }else{
                n.posX += 420 / level;
                if(this.right == null){
                    n.parent = this;
                    this.right = n;
                }
                else
                    this.right.addN(n);
            }
            level--;
        }
    }
    public class Tree{
        public node root = null;

        public void addNode(int val){
            node n = new node(val);
            if (root == null){
                Update();
                root = n;
                root.posX = win_WIDTH/2;
                root.posY = 80;
            }else{
                n.posX = tree.root.posX;
                n.posY = tree.root.posY;
                root.addN(n);
                //sleep(500);
                Update();
            }
        }
    }
    public void inorderRec(node zroot)
    {
        if (zroot != null) {
            inorderRec(zroot.left);
            traversal = zroot;
            statusText += zroot.value + " ";
            System.out.print(zroot.value + " ");
            Update();
            sleep(1000);
            inorderRec(zroot.right);
        }
    }

    public void inorderDeletion(node zroot)
    {
        tree.root = null;
        Update();
    }

    Tree tree = new Tree();
    node current = null;
    node traversal = null;

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int win_WIDTH = 1000;

    public Color themeColor  = new Color(110,217,161);
    public Color BGColor  = Color.darkGray;
    public Color blueColor  = new Color(77, 171, 213);
    public Color pinkColor  = new Color(226, 86, 186);
    public Color purpleColor  = new Color(128, 86, 226);
    public Color orangeColor  = new Color(226, 160, 86);

    public int numtoact = 0;
    public int animSpeed = 1000;                   //Initial speed of the sorting animation


    public String statusText = "";

    JFrame f;
    JPanel panel, btnPanel;
    JButton startBtn, resetBtn, bottomBtn, popBtn;
    JTextField input;
    JLabel  datainputtext, actiontext, speedText;

    public String mainFont = "Century Gothic";

    public BST(){
        f = new JFrame("Binary Search Tree Visualization");
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
                statusText = "Tree has been Reset";
                Reset();
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


        startBtn = new JButton("Insert");
        startBtn.setBounds(40, pos, 180,40);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int v;
                try{
                    v = Integer.parseInt(input.getText());
                    level = 1;
                    tree.addNode(v);
                    statusText = v + " added to the tree";
                    Update();
                }catch (Exception exception){
                    statusText = "Error: Failed to add the data";
                    Update();
                }
                //inorderRec(tree.root);
                //System.out.println("In order traversal done");
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

        popBtn = new JButton("Traverse");
        popBtn.setBounds(40, pos, 180,40);
        popBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popBtn.setEnabled(false);
                input.setEnabled(false);
                startBtn.setEnabled(false);
                bottomBtn.setEnabled(false);
                statusText = "";
                SwingWorker sw1 = new SwingWorker()
                {

                    @Override
                    protected String doInBackground() throws Exception
                    {
                        // define what thread will do here
                        inorderRec(tree.root);

                        String res = "Finished Execution";
                        return res;
                    }

                    @Override
                    protected void done()
                    {
                        // this method is called when the background
                        // thread finishes execution

                        popBtn.setEnabled(true);
                        input.setEnabled(true);
                        startBtn.setEnabled(true);
                        bottomBtn.setEnabled(true);
                        traversal = null;
                        Update();
                    }
                };

                // executes the swingworker on worker thread
                sw1.execute();
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

        Reset();
    }

    public static void main(String[] args) {
        BST bst = new BST();
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

        int posX = win_WIDTH/2;        //X position of root
        int posY = 80;                 //Y position of root

        graphics.setColor(Color.white);

        Stack<node> s = new Stack<node>();
        node curr = tree.root;

        graphics.drawString(statusText, 28, 80);

        // traverse the tree
        while (curr != null || s.size() > 0)
        {
            /* Reach the left most Node of the
            curr Node */
            while (curr !=  null)
            {
                /* place pointer to a tree node on
                   the stack before traversing
                  the node's left subtree */
                s.push(curr);
                curr = curr.left;
            }

            /* Current must be NULL at this point */
            curr = s.pop();

            if(curr.parent != null)
            {
                graphics.setColor(Color.white);
                graphics.drawLine(curr.posX, curr.posY, curr.parent.posX, curr.parent.posY);
            }
            if (curr == current){
                graphics.setColor(orangeColor);
            }else{
                graphics.setColor(blueColor);
            }
            if (curr == traversal){
                graphics.setColor(pinkColor);
            }

            graphics.fillOval(curr.posX-8, curr.posY - 30, 45,45);
            graphics.setColor(Color.black);
            graphics.drawString(Integer.toString(curr.value), curr.posX, curr.posY);


            /* we have visited the node and its
               left subtree.  Now, it's right
               subtree's turn */
            curr = curr.right;

        }


    }


    public void Reset(){
        inorderDeletion(tree.root);
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


