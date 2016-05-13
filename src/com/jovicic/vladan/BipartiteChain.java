package com.jovicic.vladan;

/**
 * Created by vlada on 5/13/2016.
 */
public class BipartiteChain {

    private int n; //n in total => n%2 = 0
    private Graph g;
    public BipartiteChain(int n)
    {
        this.n = n;
        g = new Graph(n);
        addEdges();
    }

    private void addEdges()
    {
        for(int u=0;u<n/2;u++)
        {
            for(int v = 0; v<=u; v++)
            {
                g.addEdge(u, v+n/2);
            }
        }
    }

    public Graph getBipartiteChain()
    {
        return this.g;
    }

}
