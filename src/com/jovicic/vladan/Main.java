package com.jovicic.vladan;

import java.io.*;

/**
 * Created by vlada on 2/26/2016.
 */
public class Main {
    private static rReadibilityCalc calc;
    private static Graph g;
    private static String outputGraph, outputVertices;
    private static int exact = -1;
    private static BufferedWriter logWriter;
    private static BufferedReader in;
    public static void main(String [] args)
    {
        g = new RandomGraph(30, 50).getNewRandomGraph();
        ReadibilityCalculator rb = new ReadibilityCalculator(g, 4,2);
        if(rb.isReadibilityExactly(3,false))
        {
            System.out.println("Bice da radi");
            //g.printVerticesToFile("outputGraph.out");
        }
        else
        {
            //g.printGraphToFile("justgraph.in");
            System.out.println("Ne radi :(");
        }
        /*int cnt = 0;

        boolean success = true;

        try {
            logWriter = new BufferedWriter(new FileWriter("logFile.log"));
            in = new BufferedReader(new FileReader("test/StartIndex.txt"));
            cnt = Integer.parseInt(in.readLine());
        } catch (IOException e)
        {
            //e.printStackTrace();
            success = false;
        }
        if(success) {
            for(int i = cnt; i<18; i++)
            {
                g = new Graph();
                g.readGraphFromFile("test/RandomGraph"+i+".in");
                ReadibilityCalculator rb = new ReadibilityCalculator(g, 6, 2);
                if(rb.calculate())
                {
                    g.printVerticesToFile("OutPutGraph"+i+".out");
                }
                else
                {
                    try {
                        logWriter.write("Couldnt calculate for graph " + i);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            logWriter.close();
            in.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }*/

    }

    public static Graph choose()
    {
        Graph g = null;
        System.out.println("1. Random graph with given probability");
        System.out.println("2. Grid graph with given size");
        System.out.println("3. Graph defined in a file");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Type your choice: ");
        try {
            int c = Integer.parseInt(in.readLine());
            if(c == 1)
            {
                System.out.print("Enter number of vertices n = ");
                int n = Integer.parseInt(in.readLine());
                System.out.print("Probability for an edge p = ");
                int p = Integer.parseInt(in.readLine());
                g = new RandomGraph(n,p).getNewRandomGraph();
                //return g;
            }
            else if(c == 2)
            {
                System.out.print("Enter width:");
                int w = Integer.parseInt(in.readLine());
                System.out.print("Enter height:");
                int h = Integer.parseInt(in.readLine());
                g = new GridGraph(w,h).getGridGraph();
                //return g;
            }
            else
            {
                System.out.println("Not available! Try again");
                return null;
            }
            System.out.print("Enter name of a file to output: ");
            outputVertices = in.readLine();
            System.out.println("Enter name of file to output edges (leave blank if you do not want to output)");
            outputGraph = in.readLine();
            System.out.println("Type -1 if you want to calculate by algorithm or enter value if you want to check for exact readability");
            exact = Integer.parseInt(in.readLine());
        } catch (IOException e)
        {
            System.out.println("Wrong input!");
        }
        return g;
    }
}
