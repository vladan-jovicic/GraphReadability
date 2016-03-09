package com.jovicic.vladan;

import java.util.Random;

/**
 * Created by vlada on 3/8/2016.
 */
public class RandomGraph {
    private Graph g;
    private int n;
    private int p;
    private Random rand;
    public RandomGraph(int nn)
    {
        n = nn;
        g = new Graph(n);
        p = 50;
        rand = new Random();
    }

    public RandomGraph(int nn, int pp)
    {
        n = nn;
        g = new Graph(n);
        p = pp;
        rand = new Random();
    }

    public Graph getNewRandomGraph()
    {
        g = new Graph(n);
        for(int u=0; u<g.n/2; u++)
        {
            for(int v=g.n/2; v<g.n; v++)
            {

                if(p >= rand.nextInt(100))
                {
                    g.addEdge(u,v);
                }
                //g.addEdge(u,v);
            }
        }
        return g;
    }
}
