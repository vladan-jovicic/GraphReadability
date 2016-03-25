package com.jovicic.vladan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by vlada on 2/26/2016.
 */
public class Main {
    private static rReadibilityCalc calc;
    private static Graph g;
    private static String outputGraph, outputVertices;
    private static int exact = -1;
    public static void main(String [] args)
    {
        g = choose();
        if(g != null)
        {
            ReadibilityCalculator rb = new ReadibilityCalculator(g, 4, 2);
            if(exact > 1)
            {
                if(rb.isReadibilityExactly(exact, true))
                {
                    g.printVerticesToFile(outputVertices);
                    if(outputGraph != null && outputGraph != "")
                    {
                        g.printGraphToFile(outputGraph);
                    }
                }
            }
            else
            {
                rb.calculate();
                g.printVerticesToFile(outputVertices);
                if(outputGraph != null && outputGraph != "")
                {
                    g.printGraphToFile(outputGraph);
                }
            }
        }

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
