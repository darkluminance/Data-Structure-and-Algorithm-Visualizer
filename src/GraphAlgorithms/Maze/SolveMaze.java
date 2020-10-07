package GraphAlgorithms.Maze;

import GraphAlgorithms.Pathfinder.GraphVisualize;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SolveMaze {
    private final List<Point> path = new ArrayList<Point>();

    int[][] dist;
    Point[][] prev;

    public SolveMaze(MazeVisualize g){
        dist  = new int[640/g.gridSIZE][1280/g.gridSIZE];
        prev  = new Point[640/g.gridSIZE][1280/g.gridSIZE];
        g.resetValues();
        g.status = BFS(g);
        JFrame f = new JFrame();

        //If path is found, then show the path
        if (g.status){
            Point crawl = new Point(g.targetX, g.targetY);
            path.add(crawl);

            while (prev[crawl.y][crawl.x] != null){
                path.add(crawl);
                crawl = prev[crawl.y][crawl.x];
            }

            for (int i = path.size() - 1; i >= 0 ; i--) {
                g.grid[path.get(i).y][path.get(i).x] = 3;
            }
            JOptionPane.showMessageDialog(f,"<html>Maze saved in mazes/path" + new SaveMaze().Save(g, true) + ".png" + "<br>Path length is: " + path.size() + "</html>");

        }else
            JOptionPane.showMessageDialog(f, "No path found");

        g.Update();
    }

    public boolean BFS(MazeVisualize g){
        Queue<Point> q = new LinkedList<>();

        //Initializing path and distances of the grid points
        for (int i = 0; i<g.grid.length; i++){
            for (int j = 0; j < g.grid[0].length; j++) {
                dist[i][j] = 99999;
                prev[i][j] = null;
            }
        }

        //Add the source to the queue
        q.add(new Point(g.sourceX, g.sourceY));
        dist[g.sourceY][g.sourceX] = 0;     //Distance of source from the source is 0

        //Loop will occur as long as there is a possible cell to visit
        while (!q.isEmpty()){
            //Pop the first item of the queue since its neighbours are visited
            Point v = q.remove();
            g.grid[v.y][v.x] = 5;


            //Visiting all the neighbours of the current point
            //1. Set the neighbour cell as visiting
            //2. Increase distance of neighbour cell from source by 1 from the current cell
            //3. Set the parent cell of neighbour cell as the current cell
            //4. Add the neighbour to the queue
            //5. Increase the level of neighbour cell by 1 from the current cell (Level means how deep it is)
            //top
            if (g.grid[v.y-1][v.x] == 0){
                g.grid[v.y-1][v.x] = 69;
                dist[v.y-1][v.x] = dist[v.y][v.x] + 1;
                prev[v.y-1][v.x] = new Point(v.x, v.y);
                q.add(new Point(v.x, v.y-1));
            }

            //right
            if (g.grid[v.y][v.x+1] == 0){
                g.grid[v.y][v.x+1] = 69;
                dist[v.y][v.x+1] = dist[v.y][v.x] + 1;
                prev[v.y][v.x+1] = new Point(v.x, v.y);
                q.add(new Point(v.x+1, v.y));
            }

            //bottom
            if (g.grid[v.y+1][v.x] == 0){
                g.grid[v.y+1][v.x] = 69;
                dist[v.y+1][v.x] = dist[v.y][v.x] + 1;
                prev[v.y+1][v.x] = new Point(v.x, v.y);
                q.add(new Point(v.x, v.y+1));
            }

            //left
            if (g.grid[v.y][v.x-1] == 0){
                g.grid[v.y][v.x-1] = 69;
                dist[v.y][v.x-1] = dist[v.y][v.x] + 1;
                prev[v.y][v.x-1] = new Point(v.x, v.y);
                q.add(new Point(v.x-1, v.y));
            }

            //If reached the target
            //Checking whether neighbour cell is the target cell also counts as iteration. Hence, incrementing it
            if (v.x == g.targetX && v.y == g.targetY){
                return true;
            }else if (v.x+1 == g.targetX && v.y == g.targetY){
                dist[v.y][v.x+1] = dist[v.y][v.x] + 1;
                prev[v.y][v.x+1] = new Point(v.x, v.y);
                return true;
            }else if (v.x-1 == g.targetX && v.y == g.targetY){
                dist[v.y][v.x-1] = dist[v.y][v.x] + 1;
                prev[v.y][v.x-1] = new Point(v.x, v.y);
                return true;
            }else if (v.x == g.targetX && v.y-1 == g.targetY){
                dist[v.y-1][v.x] = dist[v.y][v.x] + 1;
                prev[v.y-1][v.x] = new Point(v.x, v.y);
                return true;
            }else if (v.x == g.targetX && v.y+1 == g.targetY){
                dist[v.y+1][v.x] = dist[v.y][v.x] + 1;
                prev[v.y+1][v.x] = new Point(v.x, v.y);
                return true;
            }
        }

        //If no path is found
        return false;
    }


}
