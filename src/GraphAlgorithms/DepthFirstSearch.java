package GraphAlgorithms;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DepthFirstSearch {
    private final List<Integer> path = new ArrayList<Integer>();


    public DepthFirstSearch(GraphVisualize g, Point source){
        g.status = DFS(g, source.x, source.y, path);

        g.Update();
    }

    public boolean DFS(GraphVisualize g, int x, int y, List<Integer> path){
        if(x<1 || x>= g.grid[0].length - 1 ||y<1 || y>= g.grid.length - 1 ){
            return false;
        }
        //Reached the target
        if (g.grid[y][x] == 4){
            path.add(x);    path.add(y);
            return true;
        }
        if (g.grid[y][x] == 2) {
            g.grid[y][x] = -1;
            g.Update();
            g.sleep(15);
        }

        if (g.grid[y][x] == -1){
            return false;
        }

        if (g.grid[y][x] == 0 || g.grid[y][x] == 2){
            if (g.grid[y][x] == 0)
            {
                g.grid[y][x] = 5;
                if (g.level[y][x]==0){
                    g.place++;
                    g.level[y][x] = g.place;
                }

            }

            g.Update();
            g.sleep(15);


            if (DFS(g, x+1, y, path)){
                path.add(x);    path.add(y);
                g.grid[y][x] = 3;
                g.Update();
                g.sleep(15);
                return true;
            }
            if (DFS(g, x-1, y, path)){
                path.add(x);    path.add(y);
                g.grid[y][x] = 3;
                g.Update();
                g.sleep(15);
                return true;
            }
            if (DFS(g, x, y+1, path)){
                path.add(x);    path.add(y);
                g.grid[y][x] = 3;
                g.Update();
                g.sleep(15);
                return true;
            }
            if (DFS(g, x, y-1, path)){
                path.add(x);    path.add(y);
                g.grid[y][x] = 3;
                g.Update();
                g.sleep(15);
                return true;
            }

        }
        return false;
    }

    public static void main(String[] args) {

    }
}
