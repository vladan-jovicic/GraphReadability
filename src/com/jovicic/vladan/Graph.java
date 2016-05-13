package com.jovicic.vladan;

import java.io.*;
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
    private int dt = -1;

    public Graph ()
    {

    }

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
    public void setReadability(int r)
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

    public int getDT()
    {
        if(dt != -1)
            return dt;
        dt = n;
        for(int u=0; u<n/2; u++)
        {
            for(int v=0; v<n/2; v++)
            {
                if(u==v)
                    continue;
                int cnt1 = 0;
                int cnt2 = 0;
                int cnt3 = 0;
                int cnt4 = 0;
                for(int w = 0; w<n/2; w++)
                {
                    if(adjMatrix[u][n/2+w] && !adjMatrix[v][n/2+w])
                        cnt1++;
                    if(adjMatrix[v][n/2+w] && !adjMatrix[u][n/2+w])
                        cnt2++;
                    if(adjMatrix[w][n/2+u] && !adjMatrix[w][n/2+v])
                        cnt3++;
                    if(adjMatrix[w][n/2+v] && !adjMatrix[w][n/2+u])
                        cnt4++;
                }
                dt = Math.min(dt, Math.min(Math.max(cnt1,cnt2),Math.max(cnt3, cnt4)));
            }
        }
        return dt;
    }
    public  int getReadability()
    {
        return  readibility;
    }
    //public void
    public void setLabel(int u, int ind, int c)
    {
        //System.out.println("Setting element ("+u+","+ind+") to "+c);
        label[u][ind] = c;
    }
    public int getLabel(int u, int ind)
    {
        return label[u][ind];
    }
    public String getVertexLabel(int u)
    {
        String ret = "";
        for(int i=1; i<=readibility; i++)
        {
            ret += ("(" + label[u][i] + ")");
        }
        return  ret;
    }
    public void printVertices()
    {
        for(int i=0;i<n;i++)
        {
            for(int j=1; j<readibility+1;j++)
            {
                if(label[i][j]==0)
                    System.out.print("(0)");
                else
                    System.out.print("("+label[i][j]+")");
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

    public void printGraphToFile(String filename)
    {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            out.write(n+" "+edges.size()+"\n");
            for (int u = 0; u < n / 2; u++) {
                for (int v = n / 2; v < n; v++) {
                    if (adjMatrix[u][v])
                        out.write(u + " " + v + "\n");

                }
            }
            out.close();
        }catch (Exception e)
        {
            System.out.println("Failed to print");
        }
    }

    public void printGraph()
    {
        for (int u = 0; u < n / 2; u++) {
            for (int v = n / 2; v < n; v++) {
                if (adjMatrix[u][v])
                    System.out.print(u + " " + v + "\n");
            }
        }
    }

    public void readGraphFromFile(String filename)
    {
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String [] read = in.readLine().split(" ");
            n = Integer.parseInt(read[0]);
            int m = Integer.parseInt(read[1]);
            edges = new Vector<Par>();
            adjMatrix = new boolean[n][n];
            label = new int[n][];
            eSize = 0;
            for (int i=0; i<m; i++)
            {
                read = in.readLine().split(" ");
                addEdge(Integer.parseInt(read[0]), Integer.parseInt(read[1]));
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}