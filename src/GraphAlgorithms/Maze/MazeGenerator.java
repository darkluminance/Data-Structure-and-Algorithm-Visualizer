package GraphAlgorithms.Maze;

import java.awt.*;
import java.util.Vector;

public class MazeGenerator {
    public int speed= 25;
    public MazeGenerator(MazeVisualize gv){
        //All walls
        for (int i = 0; i < gv.gridRows; i++) {
            for (int j = 0; j < gv.gridCols; j++) {
                gv.grid[i][j] = 1;
            }
        }

        DFS(gv, new Point(gv.sourceX, gv.sourceY));

        for (int i = 0; i < gv.gridRows; i++) {
            for (int j = 0; j < gv.gridCols; j++) {
                if (gv.grid[i][j] == 5)
                    gv.grid[i][j] = 0;
            }
        }
        gv.Update();
    }

    public void DFS(MazeVisualize g, Point p){
        Point cur = p;

        //If the current cell is part of the boundary or not
        if(p.x<1 || p.x>= g.grid[0].length - 1 ||p.y<1 || p.y>= g.grid.length - 1 ){
            return;
        }
        g.grid[p.y][p.x] = 0;

        g.Update();
        g.sleep(speed);

        Vector<Point> n = new Vector<Point>(4);
        n.add(new Point(1,0));
        n.add(new Point(-1, 0));
        n.add(new Point(0,1));
        n.add(new Point(0,-1));
        int nsize = 4;

        while(nsize > 0){
            int r =(int) Math.round(Math.random() * (nsize-1) + 0);

            //Check if the neighbour is visited
            //If not, then generate its neighbours
            //Check if they are visited, if even one

            Point thisN = addPoints(cur, n.elementAt(r));

            n.remove(r);
            nsize--;

            if(thisN.x<1 || thisN.x>= g.grid[0].length - 1 ||thisN.y<1 || thisN.y>= g.grid.length - 1 ){
                continue;
            }

            if (g.grid[thisN.y][thisN.x] == 1){
                Vector<Point> pp = new Vector<Point>(4);
                pp.add(new Point(0,-1));
                pp.add(new Point(1,0));
                pp.add(new Point(0,1));
                pp.add(new Point(-1, 0));


                boolean flag = true;
                for (int f = 0; f < 4; f++) {
                    Point peepee = addPoints(thisN, pp.elementAt(f));
                    if (peepee.x == p.x && peepee.y == p.y) continue;

                    if (g.grid[peepee.y][peepee.x] == 0 || g.grid[peepee.y][peepee.x] == 5){
                        flag = false;
                        break;
                    }

                }

                if (flag)
                    DFS(g, thisN);
            }

        }


        g.grid[p.y][p.x] = 5;
        g.Update();
        g.sleep(speed);

    }

    public Point addPoints(Point a, Point b){
        return new Point(a.x+b.x, a.y+b.y);
    }
}
