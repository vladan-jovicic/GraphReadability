package com.jovicic.vladan;

import java.util.Vector;

/**
 * Created by vlada on 2/26/2016.
 */
public class Main {
    private static rReadibilityCalc calc;
    public static void main(String [] args)
    {
        Graph g;

        RandomGraph randGraph = new RandomGraph(10);
        g = randGraph.getNewRandomGraph();
        ReadibilityCalculator rb = new ReadibilityCalculator(g, 5);
        if (rb.calculate()) {
            g.printGraphToFile("inputGraph.txt");
            g.printVerticesToFile("graphRandom.txt");
        } else {
            System.out.println("Upper bound should be greater");
        }

    }
}
