package com.jovicic.vladan;

import java.util.Vector;

/**
 * Created by vlada on 2/26/2016.
 */
public class UnionFind {
    private int[] parents;
    public boolean [] used;
    private int n;
    public UnionFind(int n)
    {
        parents = new int[n];
        this.n = n;
        used = new boolean[n];
        for(int i=0; i<n; i++)
        {
            parents[i] = i;
            used[i] = false;
        }
    }

    public int getParent(int x)
    {
        return parents[x];
    }

    public int Find(int x)
    {
        if (x == parents[x])
            return x;
        else
        {
            int p = Find(parents[x]);
            parents[x] = p;
            return  p;
        }
    }

    public void Union(int x, int y)
    {
        used[x] = true;
        used[y] = true;
        int xroot = Find(x);
        int yroot = Find(y);
        if(xroot != yroot)
        {
            parents[xroot] = yroot;
        }
    }

    public Vector<Vector<Integer> > getComponents()
    {
        Vector<Vector<Integer> > comps = new Vector<>();
        for(int i=0; i<n; i++)
        {
            comps.add(new Vector<>());
        }
        for(int i=0; i<n; i++)
        {
            if(used[i])
                comps.elementAt(Find(i)).add(i);
        }
        return comps;

    }
}
