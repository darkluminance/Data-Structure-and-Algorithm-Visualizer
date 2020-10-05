package GraphAlgorithms.Maze;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class LoadMaze {
    public LoadMaze(MazeVisualize gv){
        JFrame f = new JFrame();

        try {
            // Upload the image
            BufferedImage image = ImageIO.read(new File("mazes/mazex.png"));
            int width = image.getWidth();
            int height = image.getHeight();
            int size = width/(1280/20);
            if((float)width/(float)height != 1280.0f/640.0f)
                throw new Exception("File dimension not supported. Ratio must be 2:1");

            for (int i = 0; i < height; i+=size) {
                for (int j = 0; j < width; j+=size) {
                    if (image.getRGB(j,i) == Color.white.getRGB()){
                        gv.grid[i/size][j/size] = 0;
                    }else if (image.getRGB(j,i) == Color.black.getRGB()){
                        gv.grid[i/size][j/size] = 1;
                    }else if (image.getRGB(j,i) == Color.red.getRGB()){
                        gv.grid[gv.sourceY][gv.sourceX] = 0;
                        gv.grid[i/size][j/size] = 2;
                        gv.sourceX = j/size;
                        gv.sourceY = i/size;
                    }else if (image.getRGB(j,i) == Color.green.getRGB()){
                        gv.grid[gv.targetY][gv.targetX] = 0;
                        gv.grid[i/size][j/size] = 4;
                        gv.targetX = j/size;
                        gv.targetY = i/size;
                    }
                    gv.Update();
                }
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(f, exc.getMessage());
        }
    }
}
