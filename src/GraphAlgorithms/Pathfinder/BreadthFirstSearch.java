package GraphAlgorithms.Pathfinder;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstSearch {
    private final List<Point> path = new ArrayList<Point>();

    int[][] dist = new int[630/30][990/30];
    Point[][] prev = new Point[630/30][990/30];

    public BreadthFirstSearch(GraphVisualize g){
        g.whichAlgorithm = "bfs";
        g.resetValues();
        double start = System.nanoTime();
        g.status = BFS(g);
        System.out.println((System.nanoTime() - start)/10E8);

        //If path is found, then show the path
        if (g.status){
            if (g.willAnimate)
                g.sleep(250);
            Point crawl = new Point(g.targetX, g.targetY);
            path.add(crawl);

            while (prev[crawl.y][crawl.x] != null){
                path.add(crawl);
                crawl = prev[crawl.y][crawl.x];
            }

            for (int i = path.size() - 1; i >= 0 ; i--) {
                g.grid[path.get(i).y][path.get(i).x] = 3;
                g.Update();
                if (g.willAnimate)
                    g.sleep(20);
            }
            g.pathPlace = path.size() - 1;

        }

        g.Update();
    }

    public boolean BFS(GraphVisualize g){
        Queue<Point> q = new LinkedList<>();
        //g.grid[g.sourceY][g.sourceX] = 3;

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
            if (!g.willFind) {
                return false;
            }
            g.iterations++;
            //Pop the first item of the queue since its neighbours are visited
            Point v = q.remove();
            g.grid[v.y][v.x] = 5;
            g.place++;
            if (g.grid[v.y][v.x] == 0)
                g.level[v.y][v.x] = g.place;


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
                g.level[v.y-1][v.x] = g.level[v.y][v.x]+1;
            }

            //right
            if (g.grid[v.y][v.x+1] == 0){
                g.grid[v.y][v.x+1] = 69;
                dist[v.y][v.x+1] = dist[v.y][v.x] + 1;
                prev[v.y][v.x+1] = new Point(v.x, v.y);
                q.add(new Point(v.x+1, v.y));
                g.level[v.y][v.x+1] = g.level[v.y][v.x]+1;
            }

            //bottom
            if (g.grid[v.y+1][v.x] == 0){
                g.grid[v.y+1][v.x] = 69;
                dist[v.y+1][v.x] = dist[v.y][v.x] + 1;
                prev[v.y+1][v.x] = new Point(v.x, v.y);
                q.add(new Point(v.x, v.y+1));
                g.level[v.y+1][v.x] = g.level[v.y][v.x]+1;
            }

            //left
            if (g.grid[v.y][v.x-1] == 0){
                g.grid[v.y][v.x-1] = 69;
                dist[v.y][v.x-1] = dist[v.y][v.x] + 1;
                prev[v.y][v.x-1] = new Point(v.x, v.y);
                q.add(new Point(v.x-1, v.y));
                g.level[v.y][v.x-1] = g.level[v.y][v.x]+1;
            }

            //If reached the target
            //Checking whether neighbour cell is the target cell also counts as iteration. Hence, incrementing it
            if (v.x == g.targetX && v.y == g.targetY){
                g.iterations++;
                return true;
            }else if (v.x+1 == g.targetX && v.y == g.targetY){
                g.iterations++;
                dist[v.y][v.x+1] = dist[v.y][v.x] + 1;
                prev[v.y][v.x+1] = new Point(v.x, v.y);
                return true;
            }else if (v.x-1 == g.targetX && v.y == g.targetY){
                g.iterations++;
                dist[v.y][v.x-1] = dist[v.y][v.x] + 1;
                prev[v.y][v.x-1] = new Point(v.x, v.y);
                return true;
            }else if (v.x == g.targetX && v.y-1 == g.targetY){
                g.iterations++;
                dist[v.y-1][v.x] = dist[v.y][v.x] + 1;
                prev[v.y-1][v.x] = new Point(v.x, v.y);
                return true;
            }else if (v.x == g.targetX && v.y+1 == g.targetY){
                g.iterations++;
                dist[v.y+1][v.x] = dist[v.y][v.x] + 1;
                prev[v.y+1][v.x] = new Point(v.x, v.y);
                return true;
            }

            g.Update();
            if (g.willAnimate)
                g.sleep(g.getAnimSpeed);

        }

        //If no path is found
        return false;
    }


}
