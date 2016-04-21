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
        Graph g = new Graph();
        g.readGraphFromFile("inputGraph.in");
        ReadibilityCalculator rb = new ReadibilityCalculator(g, 4, 2);
        if(rb.calculate())
        {
            System.out.println("Izracunao");
            g.printVerticesToFile("output.out");
        }
        else
        {
            System.out.println("Something went wrong");
        }
    }
}
