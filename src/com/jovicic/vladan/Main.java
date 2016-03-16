package com.jovicic.vladan;

import java.util.Vector;

/**
 * Created by vlada on 2/26/2016.
 */
public class Main {
    private static rReadibilityCalc calc;
    public static void main(String [] args)
    {
        Graph g;/* = new Graph(12);
        g.addEdge(0,6);
        g.addEdge(0,7);
        g.addEdge(1,6);
        g.addEdge(1,8);
        g.addEdge(2,6);
        g.addEdge(2,7);
        g.addEdge(2,8);
        g.addEdge(2,9);
        g.addEdge(3,7);
        g.addEdge(3,9);
        g.addEdge(3,10);
        g.addEdge(4,8);
        g.addEdge(4,9);
        g.addEdge(4,11);
        g.addEdge(5,9);
        g.addEdge(5,10);
        g.addEdge(5,11);*/
        GridGraph gridg = new GridGraph(3,4);
        g = gridg.getGridGraph();

        ReadibilityCalculator rb = new ReadibilityCalculator(g,4,2);
        if (rb.isReadibilityExactly(3, true)) {
            g.printGraphToFile("inputGraph.txt");
            g.printVerticesToFile("graphRandom.txt");
        } else {
            System.out.println("Upper bound should be greater");
        }

    }
}
