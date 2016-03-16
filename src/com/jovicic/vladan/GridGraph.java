package com.jovicic.vladan;

/**
 * Created by vlada on 3/15/2016.
 */
public class GridGraph {

    private int w,h;
    private Graph g;
    private int [] hash;
    public GridGraph(int w, int h)
    {
        g = new Graph(w*h);
        hash = new int[w*h];
        int label1 = 0;
        int label2 = (w*h)/2;
        for(int i=0; i<h; i++)
        {
            for(int j=0; j<w; j++)
            {
                if(i%2==0)
                    hash[i*w + j] = (j%2==0)?(label1++):(label2++);
                else
                    hash[i*w + j] = (j%2==0)?(label2++):(label1++);
            }
        }
        int [] dx = new int[] {1, -1, 0, 0};
        int [] dy = new int[] {0, 0, 1, -1};
        /*for(int i=0;i<w*h;i++)
        {
            System.out.print(hash[i] + " ");
            if((i+1)%w==0)
                System.out.println();
        }*/
        for(int i=0; i<h; i++)
        {
            for(int j=(i%2==0)?0:1; j<w; j+=2)
            {
                for(int k=0; k<4; k++)
                {
                    int u = i*w + j;
                    if(i+dy[k] >=0 && i+dy[k] < h && j+dx[k] >=0 && j+dx[k] < w)
                    {
                        int v = (i+dy[k])*w + (j+dx[k]);
                        //System.out.println("Adding edge ("+hash[u]+","+hash[v]+")");
                        g.addEdge(hash[u],hash[v]);
                    }
                }
            }
        }
    }
    public Graph getGridGraph()
    {
        return g;
    }
}
