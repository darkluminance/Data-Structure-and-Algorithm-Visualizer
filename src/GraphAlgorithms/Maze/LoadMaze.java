package GraphAlgorithms.Maze;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class LoadMaze {
    public LoadMaze(MazeVisualize gv, String path){
        JFrame f = new JFrame();

        try {
            // Upload the image
            BufferedImage image = ImageIO.read(new File(path));
            String ext = path.charAt(path.length() - 3) + "" + path.charAt(path.length() - 2) +  path.charAt(path.length() - 1) ;
            if (!ext.toLowerCase().toString().equals("png") && !ext.toLowerCase().toString().equals("jpg")){
                throw new Exception("File format not supported. Supports only png and jpg");
            }
            int width = image.getWidth();
            int height = image.getHeight();
            int size = width/(1280/gv.gridSIZE);
            if((float)width/(float)height != 2.0f)
                throw new Exception("<html>File dimension not supported. Ratio must be 2:1<br>" +
                        "See the sample images for reference</html>");


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
                    }else {
                        gv.grid[i/size][j/size] = 0;
                    }
                    gv.Update();
                }
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(f, exc.getMessage());
        }
    }
}
