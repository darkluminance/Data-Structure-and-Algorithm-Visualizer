package GraphAlgorithms.Pathfinder;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Dijkstra {
    private final List<Point> path = new ArrayList<Point>();

    int[][] dist = new int[630/30][990/30];
    Point[][] prev = new Point[630/30][990/30];

    public Dijkstra(GraphVisualize g){
        g.whichAlgorithm = "bfs";
        g.resetValues();
        double start = System.nanoTime();
        g.status = Djk(g);
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

    public boolean Djk(GraphVisualize g){
        Map<Point, Integer> unvisited = new HashMap<Point, Integer>();
        Point cur = new Point(g.sourceX, g.sourceY);

        //Initializing path and distances of the grid points
        for (int i = 0; i<g.grid.length; i++){
            for (int j = 0; j < g.grid[0].length; j++) {
                dist[i][j] = 99999;
                prev[i][j] = null;
            }
        }

        dist[g.sourceY][g.sourceX] = 0;     //Distance of source from the source is 0
        unvisited.put(new Point(g.sourceX, g.sourceY), dist[g.sourceY][g.sourceX]);


        while(!unvisited.isEmpty()){
            if (!g.willFind) {
                return false;
            }

            g.iterations++;

            Point v = cur;
            g.grid[v.y][v.x] = 5;
            g.place++;
            if (g.grid[v.y][v.x] == 0)
                g.level[v.y][v.x] = dist[v.y][v.x];



            //Visiting all the neighbours of the current point
            //1. Set the neighbour cell as visiting
            //2. Increase distance of neighbour cell from source by 1 from the current cell
            //3. Set the parent cell of neighbour cell as the current cell
            //4. Add the neighbour to the queue
            //5. Increase the level of neighbour cell by 1 from the current cell (Level means how deep it is)

            //top
            if (g.grid[v.y-1][v.x] == 0 || g.grid[v.y-1][v.x] == 69 || g.grid[v.y-1][v.x] == 4){
                g.grid[v.y-1][v.x] = 69;
                if(dist[v.y][v.x] + 1 < dist[v.y-1][v.x]){
                    dist[v.y-1][v.x] = dist[v.y][v.x] + 1;
                    prev[v.y-1][v.x] = cur;
                    g.level[v.y-1][v.x] =  dist[v.y-1][v.x];
                }
                unvisited.put(new Point(v.x, v.y-1), dist[v.y-1][v.x]);
            }

            //right
            if (g.grid[v.y][v.x+1] == 0 || g.grid[v.y][v.x+1] == 69 || g.grid[v.y][v.x+1] == 4){
                g.grid[v.y][v.x+1] = 69;
                if(dist[v.y][v.x] + 1 < dist[v.y][v.x+1]){
                    dist[v.y][v.x+1] = dist[v.y][v.x] + 1;
                    prev[v.y][v.x+1] = cur;
                    g.level[v.y][v.x+1] =  dist[v.y][v.x+1];
                }
                unvisited.put(new Point(v.x+1, v.y), dist[v.y][v.x+1]);
            }

            //bottom
            if (g.grid[v.y+1][v.x] == 0 || g.grid[v.y+1][v.x] == 69 || g.grid[v.y+1][v.x] == 4){
                g.grid[v.y+1][v.x] = 69;
                if(dist[v.y][v.x] + 1 < dist[v.y+1][v.x]){
                    dist[v.y+1][v.x] = dist[v.y][v.x] + 1;
                    prev[v.y+1][v.x] = cur;
                    g.level[v.y+1][v.x] =  dist[v.y+1][v.x] ;
                }
                unvisited.put(new Point(v.x, v.y+1), dist[v.y+1][v.x]);
            }

            //left
            if (g.grid[v.y][v.x-1] == 0 || g.grid[v.y][v.x-1] == 69 || g.grid[v.y][v.x-1] == 4){
                g.grid[v.y][v.x-1] = 69;
                if(dist[v.y][v.x] + 1 < dist[v.y][v.x-1]){
                    dist[v.y][v.x-1] = dist[v.y][v.x] + 1;
                    prev[v.y][v.x-1] = cur;
                    g.level[v.y][v.x-1] =  dist[v.y][v.x-1];
                }
                unvisited.put(new Point(v.x-1, v.y), dist[v.y][v.x-1]);
            }


            unvisited.remove(cur);


            g.Update();
            if (g.willAnimate)
                g.sleep(g.getAnimSpeed);

            //If reached the target
            //Since looking at the target cell also counts as iteration. Hence, incrementing it
            if (v.x == g.targetX && v.y == g.targetY){
                g.iterations++;
                return true;
            }

            //Checks if min dist is INF
            cur = minDist(unvisited);
            if (cur == null) return false;
        }

        return false;
    }

    public Point minDist(Map<Point, Integer> u){
        int min = 99999;
        Point cp = null;
        Point mincp = null;
        int cd;
        for (Map.Entry<Point, Integer> entry: u.entrySet()
             ) {
            cp = entry.getKey();
            cd = entry.getValue();
            if (cd < min){
                min = cd;
                mincp = cp;
            }
        }
        if (min != 99999)   return mincp;
        else return null;
    }

}
