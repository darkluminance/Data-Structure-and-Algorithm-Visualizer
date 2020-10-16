package SearchAlgorithms;

import javax.swing.*;
import java.awt.*;

public class Search extends JPanel {

    public static final int WIDTH = 1280;       //Width of screen
    public static final int HEIGHT = 720;       //Height of screen
    public static final int win_WIDTH = 1000;   //Width of visualizer

    public int comparisons = 0;                 //Counts the no of comparisons in sort
    public int animSpeed = 1000;                   //Initial speed of the sorting animation
    public int numtofind = 0;

    public boolean isSearching = false;
    public boolean IsFound = false;

    public Color sortedColor = new Color(110,217,161);
    //public Color BGColor  = new Color(45, 52, 54);
    public Color BGColor  = Color.darkGray;
    public Color blueColor  = new Color(77, 171, 213);
    public Color pinkColor  = new Color(226, 86, 186);
    public Color purpleColor  = new Color(128, 86, 226);
    public Color orangeColor  = new Color(226, 160, 86);


    int arrSize;
    int[] arr;
    Color[] arrayColor;       //Stores color of each item representing array elements while searching

    public String algo = "linear";

    public Search(int n){
        GenerateRandomArray(n);
    }

    public void increaseComparison(){
        comparisons++;
    }


    public void GenerateRandomArray(int n){
        this.arrSize = n;       //Space between each bar is 2 pixels

        this.arr = new int[n];
        this.arrayColor = new Color[n];

        int min = 0;       //Minimum value of array element
        int max = 99;      //Maximum value of array element
        for (int i = 0; i<arrSize; i++){
            this.arr[i] = (int)(Math.random() * (max - min + 1) + min);
            arrayColor[i] = Color.white;
            Update();
        }
    }
    public void resetColors(){
        for (int i = 0; i<arrSize; i++){
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

    public void swap(int a, int b){
        int temp=arr[a];
        arr[a]=arr[b];
        arr[b]=temp;
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

        for (int i = 0; i<arrSize; i++){
            graphics.setColor(arrayColor[i]);
            graphics.fillRect(pos, HEIGHT/2, (win_WIDTH-100)/arrSize, (win_WIDTH-100)/arrSize);
            graphics.setColor(Color.black);
            graphics.drawRect(pos, HEIGHT/2, (win_WIDTH-100)/arrSize, (win_WIDTH-100)/arrSize);
            g.setFont(new Font("Century Gothic", Font.PLAIN, 18));
            if (arrayColor[i] == pinkColor){
                graphics.setColor(Color.white);
                g.setFont(new Font("Century Gothic", Font.PLAIN, 15));
            }
            graphics.drawString(Integer.toString(arr[i]), pos+8, (HEIGHT/2)+20);
            pos += (win_WIDTH-100)/arrSize;       //X position of next array element

            graphics.setColor(Color.white);
            g.setFont(new Font("Century Gothic", Font.BOLD, 28));

            if (algo == "linear"){
                if (arrayColor[i] == blueColor){
                    graphics.drawLine(pos-20,(HEIGHT/2)+180, pos-20, (HEIGHT/2)+48);
                    graphics.drawString("Is position " + Integer.toString(i+1) + " equal to " + numtofind + "?", 500, (HEIGHT/2)-80);
                    graphics.drawString("No", 500, (HEIGHT/2)-45);
                }
                if (arrayColor[i] == sortedColor){
                    graphics.drawString("Is position " + Integer.toString(i+1) + " equal to " + numtofind + "?", 500, (HEIGHT/2)-80);
                    graphics.drawString("Yes!!!", 500, (HEIGHT/2)-45);
                    graphics.setColor(sortedColor);
                    graphics.drawLine(pos-20,(HEIGHT/2)+180, pos-20, (HEIGHT/2)+48);
                }
            }else if(algo == "binary" && arrayColor[i] == orangeColor){
                graphics.drawString("Is " + numtofind + " greater than or less than position " + Integer.toString(i+1) + "?",
                        380, 280);
                if (numtofind > arr[i]){
                    graphics.drawString("Greater. So ignoring left half", 380, 320);
                }else{
                    graphics.drawString("Lesser. So ignoring right half", 380, 320);
                }

            }
            if (algo == "binary"){
                if (arrayColor[i] == blueColor){
                    graphics.drawString("Left Position: " + Integer.toString(i+1), 420, 100);
                }else if(arrayColor[i] == purpleColor){
                    graphics.drawString("Right Position: " + Integer.toString(i+1), 420, 150);
                }else if(arrayColor[i] == orangeColor){
                    graphics.drawString("Mid Position: " + Integer.toString(i+1), 420, 200);
                }
            }

        }

    }

}
