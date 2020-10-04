package SortingAlgorithms;

import javax.swing.*;
import java.awt.*;
//import java.math.*;

public class SortArray extends JPanel {
    public static final int WIDTH = 1280;       //Width of screen
    public static final int HEIGHT = 720;       //Height of screen
    public static final int win_WIDTH = 1000;   //Width of visualizer

    public int comparisons = 0;                 //Counts the no of comparisons in sort
    public int animSpeed = 250;                   //Initial speed of the sorting animation

    public boolean isSorting = false;

    public Color sortedColor = new Color(110,217,161);
    //public Color BGColor  = new Color(45, 52, 54);
    public Color BGColor  = Color.darkGray;
    public Color blueColor  = new Color(77, 171, 213);
    public Color pinkColor  = new Color(226, 86, 186);
    public Color purpleColor  = new Color(128, 86, 226);
    public Color orangeColor  = new Color(226, 160, 86);


    int arrSize;
    int[] arr;
    Color[] barColor;       //Stores color of each bar representing array elements while sorting

    int BAR_WIDTH;      //Total space each bar will take including original bar and space between each bar
    int BAR_OGWIDTH;    //Only width of the bar without any space

    public SortArray(int n){
        GenerateRandomArray(n);
    }

    public void increaseComparison(){
        comparisons++;
    }

    public void GenerateRandomArray(int n){
        this.arrSize = n;
        this.BAR_WIDTH =  win_WIDTH/this.arrSize;       //So that all bars fill up the space irrespective of array sizes
        this.BAR_OGWIDTH  = this.BAR_WIDTH - 2;         //Space between each bar is 2 pixels

        this.arr = new int[n];
        this.barColor = new Color[n];

        int min = 80;       //Minimum value of array element
        int max = 700;      //Maximum value of array element
        for (int i = 0; i<arrSize; i++){
            this.arr[i] = (int)(Math.random() * (max - min + 1) + min);
            barColor[i] = Color.white;
            sleep(2);
            Update();
        }

        //Array generating animation speed will depend on the size of the array
        if (arrSize == 5) animSpeed = 500;
        else if (arrSize <= 10) animSpeed = 500;
        else if (arrSize <= 25) animSpeed = 150;
        else if (arrSize <= 50) animSpeed = 35;
        else if (arrSize <= 100) animSpeed = 20;
        else if (arrSize <= 200) animSpeed = 10;
        else if (arrSize <= 300) animSpeed = 5;

    }

    //Each time repaint is called, this function runs
    @Override
    public void paintComponent(Graphics g){
        Graphics2D graphics = (Graphics2D)g;
        super.paintComponent(graphics);

        graphics.setColor(BGColor);              //BG color
        graphics.fillRect(0,0, WIDTH,HEIGHT);     //Background

        int pos = 5;        //X position of first bar

        for (int i = 0; i<arrSize; i++){
            graphics.setColor(barColor[i]);
            graphics.fillRect(pos, HEIGHT-arr[i], BAR_OGWIDTH, arr[i]);
            pos += BAR_WIDTH;       //X position of next array element
        }

    }

    public int getValue(int index){
        return arr[index];
    }

    public void swap(int a, int b){
        int temp=arr[a];
        arr[a]=arr[b];
        arr[b]=temp;

        //Turns the swapping elements into red color, then waits for a while then turn back into white
        barColor[a] = Color.red;
        barColor[b] = Color.red;

        sleep(animSpeed);

        barColor[a] = Color.white;
        barColor[b] = Color.white;
    }

    public void setValueatIndex(int a, int b){
        arr[a] = arr[b];
    }

    public void setValue(int index, int value){
        arr[index] = value;
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

    public boolean IsSorted(){
        for (int i = 1 ; i<arrSize; i++){
            if (arr[i-1]>arr[i]){
                return false;
            }
        }
        return true;
    }

    //Animation when full sorting is done
    public void sortAnim(){
        sleep(500);
        for (int i = 0; i<arrSize; i++){
            //Checks if stop button is pressed while this animation is happening
            if(!isSorting)
                return;

            barColor[i] = sortedColor;

            //animation speed will depend on the size of the array
            if (arrSize == 5) sleep(100);
            else if (arrSize <= 10) sleep(100);
            else if (arrSize <= 25) sleep(50);
            else if (arrSize <= 50) sleep(20);
            else if (arrSize <= 100) sleep(10);
            else if (arrSize <= 200) sleep(8);
            else if (arrSize <= 300) sleep(5);

            Update();
        }

        isSorting = false;      //So that all buttons become active again
    }
}
