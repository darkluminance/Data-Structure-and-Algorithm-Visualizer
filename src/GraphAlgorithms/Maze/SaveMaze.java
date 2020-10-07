package GraphAlgorithms.Maze;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SaveMaze {

    public int Save(MazeVisualize gv, boolean isPaththere){
        int v = 0;
        gv.grid[gv.sourceY][gv.sourceX] = 2;
        gv.grid[gv.targetY][gv.targetX] = 4;
        /*BufferedImage image = new BufferedImage(gv.grid[0].length, gv.grid.length, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < gv.grid.length; x++) {
            for (int y = 0; y < gv.grid[0].length; y++) {
                if (gv.grid[x][y] == 0)     image.setRGB(y,x, Color.white.getRGB());
                else if (gv.grid[x][y] == 1)     image.setRGB(y,x,Color.black.getRGB());
                else if (gv.grid[x][y] == 2)     image.setRGB(y,x,Color.red.getRGB());
                else if (gv.grid[x][y] == 4)     image.setRGB(y,x,Color.green.getRGB());
            }
        }*/

        BufferedImage image = new BufferedImage(1280, 720-80, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 720-80; x+=gv.gridSIZE) {
            for (int y = 0; y < 1280; y+=gv.gridSIZE) {
                Color c = Color.white;
                if (gv.grid[x/gv.gridSIZE][y/gv.gridSIZE] == 0)     c = Color.white;
                else if (gv.grid[x/gv.gridSIZE][y/gv.gridSIZE] == 1)     c = Color.black;
                else if (gv.grid[x/gv.gridSIZE][y/gv.gridSIZE] == 2  || gv.grid[x/gv.gridSIZE][y/gv.gridSIZE] == -1)     c = Color.red;
                else if (gv.grid[x/gv.gridSIZE][y/gv.gridSIZE] == 4)     c = Color.green;
                else if (gv.grid[x/gv.gridSIZE][y/gv.gridSIZE] == 3)     c = Color.yellow;
                else if (gv.grid[x/gv.gridSIZE][y/gv.gridSIZE] == 5  || gv.grid[x/gv.gridSIZE][y/gv.gridSIZE] == 69)     c = Color.white;

                for (int i = x; i < x+gv.gridSIZE; i++) {
                    for (int j = y; j < y + gv.gridSIZE; j++) {
                        //Color bakap = c;
                        //if (i == x || i == x+19)  c = Color.black;
                        //else if (j == y || j == y+19)  c = Color.black;
                        image.setRGB(j,i,c.getRGB());
                        //c = bakap;
                    }
                }
            }
        }

        String path;
        File ImageFile;

        if (isPaththere) {
            path = "./mazes/paths/path" + v +  ".png";
            ImageFile = new File(path);
            while (ImageFile.exists()){
                v++;
                path = "./mazes/paths/path" + v +  ".png";
                ImageFile = new File(path);
            }
        }else {
            path = "./mazes/maze" + v +  ".png";
            ImageFile = new File(path);
            while (ImageFile.exists()){
                v++;
                path = "./mazes/maze" + v +  ".png";
                ImageFile = new File(path);
            }
        }

        try {
            ImageIO.write(image, "png", ImageFile);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return v;
    }
}
