package GraphAlgorithms.Pathfinder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DepthFirstSearch {
    private final List<Integer> path = new ArrayList<Integer>();


    public DepthFirstSearch(GraphVisualize g, Point source){
        g.whichAlgorithm = "dfs";
        g.resetValues();
        double start = System.nanoTime();
        g.status = DFS(g, source.x, source.y, path);
        System.out.println((System.nanoTime() - start)/10E8);
        g.Update();
    }

    public boolean DFS(GraphVisualize g, int x, int y, List<Integer> path){
        if (!g.willFind) {
            return false;
        }
        //If the current cell is part of the boundary or not
        if(x<1 || x>= g.grid[0].length - 1 ||y<1 || y>= g.grid.length - 1 ){
            return false;
        }

        g.iterations++;


        //Reached the target
        if (g.grid[y][x] == 4){
            path.add(x);    path.add(y);
            g.iterations++;
            if (g.willAnimate)
                g.sleep(280);
            return true;
        }
        if (g.grid[y][y] != 1 && g.grid[y][y] != 4 && g.grid[y][y] != 2){
            g.current.x = x;
            g.current.y = y;
            g.Update();
            if (g.willAnimate)
                g.sleep(g.getAnimSpeed);
        }
        /*if (g.grid[y][x] == 2) {
            g.grid[y][x] = -1;
            g.Update();
            g.sleep(15);
        }

        if (g.grid[y][x] == -1){
            return false;
        }*/


        //Whether the current cell is possible to visit
        //0 = Unvisited
        //2 = Source
        //69= Currently visiting
        if (g.grid[y][x] == 0 || g.grid[y][x] == 2){
            if (g.grid[y][x] == 0)
            {
                g.grid[y][x] = 5;
                if (g.level[y][x]==0){
                    g.place++;
                    g.level[y][x] = g.place;
                }

            }else if(g.grid[y][x] == 69){
                g.grid[y][x] = 5;
            }


            g.Update();
            if (g.willAnimate)
                g.sleep(g.getAnimSpeed);


            if (DFS(g, x, y-1, path)){
                path.add(x);    path.add(y);
                g.grid[y][x] = 3;
                g.pathPlace++;
                g.pathPos.put(new Point(y, x), g.pathPlace);
                g.Update();
                if (g.willAnimate)
                    g.sleep(15);
                return true;
            }
            if (DFS(g, x+1, y, path)){
                path.add(x);    path.add(y);
                g.grid[y][x] = 3;
                g.pathPlace++;
                g.pathPos.put(new Point(y, x), g.pathPlace);
                g.Update();
                if (g.willAnimate)
                    g.sleep(15);
                return true;
            }
            if (DFS(g, x, y+1, path)){
                path.add(x);    path.add(y);
                g.grid[y][x] = 3;
                g.pathPlace++;
                g.pathPos.put(new Point(y, x), g.pathPlace);
                g.Update();
                if (g.willAnimate)
                    g.sleep(15);
                return true;
            }
            if (DFS(g, x-1, y, path)){
                path.add(x);    path.add(y);
                g.grid[y][x] = 3;
                g.pathPlace++;
                g.pathPos.put(new Point(y, x), g.pathPlace);
                g.Update();
                if (g.willAnimate)
                    g.sleep(15);
                return true;
            }

        }
        return false;
    }
}
