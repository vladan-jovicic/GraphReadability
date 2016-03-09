package com.jovicic.vladan;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.Vector;

/**
 * Created by vlada on 2/26/2016.
 */
public class Graph {

    public int n;
    public Vector<Par> edges;
    public boolean[][] adjMatrix;
    private int readibility;
    private int [][] label;
    public int eSize;

    public Graph (int nn)
    {
        n = nn;
        edges = new Vector<Par>();
        adjMatrix = new boolean[n][n];
        label = new int[n][];
        eSize = 0;
    }

    public void addEdge(int u, int v)
    {
        edges.add(new Par(u,v));
        adjMatrix[u][v] = true;
        eSize++;
    }
    public void setReadibility(int r)
    {
        readibility = r;
        for(int i=0; i<n; i++)
        {
            label[i] = new int[r+1];
        }
        for(int i=0; i<n; i++)
        {
            for(int j=1; j<=r; j++)
            {
                label[i][j] = 0;
            }
        }
    }
    public  int getReadibility()
    {
        return  readibility;
    }
    //public void
    public void setLabel(int u, int ind, char c)
    {
        //System.out.println("Setting element ("+u+","+ind+") to "+c);
        label[u][ind] = c;
    }
    public int getLabel(int u, int ind)
    {
        return label[u][ind];
    }
    public void printVertices()
    {
        for(int i=0;i<n;i++)
        {
            for(int j=1; j<readibility+1;j++)
            {
                if(label[i][j]==0)
                    System.out.print("0");
                else
                    System.out.print((char)label[i][j]);
            }
            System.out.println();
        }
    }

    public void printVerticesToFile(String filename)
    {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            for(int i=0;i<n;i++)
            {
                for(int j=1; j<readibility+1;j++)
                {
                    if(label[i][j]==0)
                        out.write("0");
                    else
                        out.write((char)label[i][j]);
                }
                out.newLine();
            }
            out.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
