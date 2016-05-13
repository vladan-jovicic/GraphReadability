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
    public static void main(String [] args)
    {
        //Graph g = new GridGraph(3,4).getGridGraph();
        /*for(int p = 10; p<=90; p+=10) {
            for (int i = 0; i < 10; i++) {
                Graph g = new RandomGraph(14, p).getNewRandomGraph();
                g.printGraphToFile("../test/inputGraph" + (p + i));
                ReadibilityCalculator rb = new ReadibilityCalculator(g, 6, 2);
                if (rb.calculate()) {
                    g.printVerticesToFile("../test/outputGraph" + (p + i));
                } else {
                    System.out.println("Could not calculate");
                }
            }
        }*/
        /*Graph g = new GridGraph(100,100).getGridGraph();
        System.out.println(g.getDT());*/

        /*Graph g = new Graph();
        g.readGraphFromFile("testGraph.in");
        ReadibilityCalculator rb = new ReadibilityCalculator(g,6,2);
        if(rb.isReadibilityExactly(3,true))
        {
            g.printVertices();
            System.out.println("nekaj");
        }*/
        Graph g = new BipartiteChain(12).getBipartiteChain();
        ReadibilityCalculator rb = new ReadibilityCalculator(g, 6, 2);
        if(rb.isReadibilityExactly(3, true))
        {
            g.printVerticesToFile("bc4.out");
        }
        else
        {
            System.out.println("krucina");
        }

        /*GridGraph g = new GridGraph(100,100);
        g.decompose();
        g.labelGrid();
        if(g.isLabellingIsomoprhic())
            System.out.println("excellent");
        else
            System.out.println("bad bad");*/

        /*ReadibilityCalculator rb = new ReadibilityCalculator(g, 8,2);
        if(rb.isReadibilityExactly(7,true))
        {
            System.out.println("Done!");
            g.printVerticesToFile("grid46.out");
        }*/

    }
}
