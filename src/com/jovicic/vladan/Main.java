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
        for(int p = 10; p<=90; p+=10) {
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
        }
    }
}
