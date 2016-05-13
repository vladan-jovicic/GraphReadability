package com.jovicic.vladan;

/**
 * Created by vlada on 3/15/2016.
 */
public class GridGraph {

    private int w,h;
    private Graph g;
    private int [] hash;
    private Graph [] dg;
    public GridGraph(int w, int h)
    {
        this.w = w;
        this.h = h;
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
        for(int i=0;i<w*h;i++)
        {
            System.out.print(hash[i] + " ");
            if((i+1)%w==0)
                System.out.println();
        }
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
    public void decompose()
    {
        dg = new Graph[3];
        //add bicliques to G_1
        dg[0] = new Graph(w*h);
        boolean [][]covered = new boolean[h][w];
        for(int j = 0; j < w-1; j+=2)
        {
            for(int i=(j%4==0)?0:1; i<h-1; i+=2)
            {

                int a = hash[i*w+j];
                int b = hash[i*w+j+1];
                //System.out.println("Add edge between: (" + a +"," +b+")");
                if(a > b)
                {
                    int tmp = a;
                    a = b;
                    b = tmp;
                }
                dg[0].addEdge(a, b);
                covered[i][j] = true;
                covered[i][j+1] = true;
                a = hash[i*w+j];
                b = hash[(i+1)*w+j];
                //System.out.println("Add edge between: (" + a +"," +b+")");
                if(a > b)
                {
                    int tmp = a;
                    a = b;
                    b = tmp;
                }
                dg[0].addEdge(a, b);
                covered[i+1][j] = true;
                a = hash[(i+1)*w+j+1];
                b = hash[i*w+j+1];
                //System.out.println("Add edge between: (" + a +"," +b+")");
                if(a > b)
                {
                    int tmp = a;
                    a = b;
                    b = tmp;
                }
                dg[0].addEdge(a, b);
                covered[i+1][j+1] = true;
                a = hash[(i+1)*w+j+1];
                b = hash[(i+1)*w + j];
                //System.out.println("Add edge between: (" + a +"," +b+")");

                if(a > b)
                {
                    int tmp = a;
                    a = b;
                    b = tmp;
                }
                dg[0].addEdge(a, b);
            }
        }
        //dg[0].printGraph();
        //add matchings on upper and lower line
        //System.out.println("*************************");
        for(int j = 2; j<w-1; j+= 4)
        {
            int a = hash[j];
            int b = hash[j+1];
            if(a > b)
            {
                int tmp = a;
                a = b;
                b = tmp;
            }
            //System.out.println("Add edge between: (" + a +"," +b+")");
            dg[0].addEdge(a,b);
            covered[0][j] = true;
            covered[0][j+1] = true;
            a = hash[(h-1)*w+j];
            b = hash[(h-1)*w + j+1];
            //System.out.println();
            //System.out.println(hash[(h-1)*w+j] + " " + hash[(h-1)*w + j+1]);
            if(a > b)
            {
                int tmp = a;
                a = b;
                b = tmp;
            }
            //System.out.println("Add edge between: (" + a +"," +b+")");
            dg[0].addEdge(a,b);
            covered[h-1][j] = true;
            covered[h-1][j+1] = true;
        }
        //dg[0].printGraph();
        //add matching on the last right line
        //System.out.println("*************************");
        int start = 0;
        if(covered[0][w-2] && covered[1][w-2])
            start = 1;
        for(int i=start; i<h-1; i++)
        {
            int a = hash[i*w + w-1];
            int b = hash[(i+1)*w + w-1];
            if(a > b)
            {
                int tmp = a;
                a = b;
                b = tmp;
            }
            if(!covered[i][w-1] && !covered[i+1][w-1])
            {
                dg[0].addEdge(a,b);
                covered[i][w-1] = true;
                covered[i+1][w-1] = true;
            }
        }

        //dg[0].printGraph();

        //add edges to G_2
        dg[1] = new Graph(w*h);
        for(int i=0; i<h; i++)
        {
            for(int j=1; j<w-1; j+=2)
            {
                int a = hash[i*w+j];
                int b = hash[i*w+j+1];
                if(a > b)
                {
                    int tmp = a;
                    a = b;
                    b = tmp;
                }
                dg[1].addEdge(a,b);
            }
        }

        //the left most line

        for(int i=1; i<h-1; i+=2)
        {
            int a = hash[i*w];
            int b = hash[(i+1)*w];
            if(a > b)
            {
                int tmp = a;
                a = b;
                b = tmp;
            }
            dg[1].addEdge(a,b);
        }

        //add edges to G_3
        dg[2] = new Graph(w*h);
        for(int i=0; i<h-1; i++)
        {
            for(int j=1; j<w-1; j++)
            {
                int a = hash[i*w + j];
                int b = hash[(i+1)*w+j];
                if(a > b)
                {
                    int tmp = a;
                    a = b;
                    b = tmp;
                }
                if(!dg[0].adjMatrix[a][b] && !dg[1].adjMatrix[a][b])
                {
                    dg[2].addEdge(a,b);
                }
            }
        }

        //the right most line

        /*if(w%3 == 0 || w%3 ==1)
        {
            for(int i=0; i<h-1; i++)
            {
                int a = hash[i*w+w-1];
                int b = hash[(i+1)*w+w-1];
                if(a > b)
                {
                    int tmp = a;
                    a = b;
                    b = tmp;
                }
                //System.out.println("Trying " + a + " " + b);
                if(!dg[0].adjMatrix[a][b])
                {
                    dg[1].addEdge(a,b);
                    //System.out.println("Added " + a + " " + b);
                }
            }
        }
        else
        {
            for(int i=0; i<h-1; i++)
            {
                int a = hash[i*w + w-1];
                int b = hash[(i+1)*w + w-1];
                if(a > b)
                {
                    int tmp = a;
                    a = b;
                    b = tmp;
                }
                if(!dg[0].adjMatrix[a][b])
                {
                    dg[2].addEdge(a,b);
                }
            }
        }*/
        for(int i=0; i<h-1; i++)
        {
            int a = hash[i*w + w-1];
            int b = hash[(i+1)*w + w-1];
            if(a > b)
            {
                int tmp = a;
                a = b;
                b = tmp;
            }
            if(!dg[0].adjMatrix[a][b]) {
                if (covered[i][w - 1] && covered[i + 1][w - 1])
                    dg[2].addEdge(a, b);
                else
                    dg[1].addEdge(a, b);
            }
        }

        System.out.println("********G_1***********");
        dg[0].printGraph();
        System.out.println("********G_2***********");
        dg[1].printGraph();
        System.out.println("********G_3***********");
        dg[2].printGraph();
        if(nekiCheck())
            System.out.println("dobro je");
        else
            System.out.println("lose je");

    }
    private boolean nekiCheck()
    {
        boolean [][] adj = new boolean[w*h][w*h];
        for(int u=0; u<(w*h)/2; u++)
        {
            for(int v=(w*h)/2; v<w*h; v++)
            {
                for(int i=0; i<3;i++)
                {
                    if(dg[i].adjMatrix[u][v])
                        adj[u][v] = true;
                }
            }
        }
        //boolean iso = true;
        for (int i=0; i<(w*h)/2; i++)
        {
            for (int j=(w*h)/2; j<w*h; j++)
            {
                if(adj[i][j] != g.adjMatrix[i][j])
                {
                    System.out.println(i + " " + j);
                    return false;
                }
            }
        }
        return true;
    }

    private int reverseHashi(int h)
    {
        return h/w;
    }

    private int reverseHashj(int h)
    {
        return h%w;
    }

    public void labelGrid()
    {
        //label G_1
        g.setReadability(3);
        int c = 1;
        int dist = 3;
        //dg[0].printGraph();
        int [] dx = new int [] {1, -1, 0, 0};
        int [] dy = new int [] {0, 0, 1, -1};
        for(int i=0; i<h;i++)
        {
            for(int j=0; j<w;j++)
            {
                for(int t = 0; t<4; t++)
                {
                    if(i + dx[t] < 0 || i + dx[t] >= h || j + dy[t] < 0 || j + dy[t] >= w)
                        continue;
                    int a = hash[i*w + j];
                    int b = hash[(i+dx[t])*w + (j+dy[t])];
                    if(a > b)
                    {
                        int tmp = a;
                        a = b;
                        b = tmp;
                    }
                    if(dg[0].adjMatrix[a][b])
                    {
                        if(g.getLabel(a,3) == 0 && g.getLabel(b,1) == 0)
                        {
                            g.setLabel(a,3,c);
                            g.setLabel(b,1,c);
                            c++;
                        }
                        else if(g.getLabel(a,3) != 0 && g.getLabel(b,1) == 0)
                        {
                            g.setLabel(b,1,g.getLabel(a,3));
                        }
                        else if(g.getLabel(a,3) == 0 && g.getLabel(b,1) != 0)
                        {
                            g.setLabel(a,3,g.getLabel(b,1));
                        }
                    }
                }
            }
        }
        //g.printVertices();
        for(int u=0; u<(w*h)/2; u++)
        {
            for(int v = (w*h)/2; v<w*h; v++)
            {
                if(dg[1].adjMatrix[u][v])
                {
                    //System.out.println("Correcting " + u + " " + v);
                    g.setLabel(u,2, g.getLabel(v, 1));
                    g.setLabel(v, 2, g.getLabel(u,3));
                }
            }
        }
        //g.printVertices();
        //dg[2].printGraph();
        for(int u=0; u<(w*h)/2; u++)
        {
            for(int v = (w*h)/2; v<w*h; v++)
            {
                if(dg[2].adjMatrix[u][v])
                {
                    //System.out.println("Correcting " + u + " " + v);
                    g.setLabel(u,1, g.getLabel(v, 1));
                    g.setLabel(v, 3, g.getLabel(u,3));
                }
            }
        }
        //g.printVertices();
        for(int i=0; i<h; i++)
        {
            for(int j=0; j<w; j++)
            {
                System.out.print(g.getVertexLabel(hash[i*w+j])+" ");
            }
            System.out.println();
        }

    }

    public boolean isLabellingIsomoprhic()
    {
        boolean [][]adj = new boolean[w*h][w*h];
        for(int u=0; u<(w*h)/2; u++)
        {
            for(int v=(w*h)/2; v<w*h; v++)
            {
                if(g.getLabel(u,3) == g.getLabel(v,1))
                    adj[u][v] = true;
                else if(g.getLabel(u,2) == g.getLabel(v,1) && g.getLabel(u,3) == g.getLabel(v,2))
                    adj[u][v] = true;
                else if(g.getLabel(u,1) == g.getLabel(v,1) && g.getLabel(u,2) == g.getLabel(v,2) && g.getLabel(u,3) == g.getLabel(v,3))
                    adj[u][v] = true;
                else
                    adj[u][v] = false;

            }
        }
        boolean iso = true;
        for(int u=0; u<(w*h)/2; u++)
        {
            for(int v=(w*h)/2; v<w*h; v++)
            {
                if(adj[u][v] != g.adjMatrix[u][v])
                {
                    iso = false;
                    System.out.println(u + " " + v + " " + g.adjMatrix[u][v] + " " + adj[u][v]);
                }
            }
        }
        return iso;
    }

}
