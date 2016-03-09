package com.jovicic.vladan;

import java.util.Vector;

/**
 * Created by vlada on 2/26/2016.
 */
public class Main {
    private static rReadibilityCalc calc;
    public static void main(String [] args)
    {
        int n = 10;
        Graph g = new Graph(n);
        //simple path graph
        /*g.addEdge(0,4);
        g.addEdge(0,5);
        g.addEdge(1,5);
        g.addEdge(1,6);
        g.addEdge(2,6);
        g.addEdge(2,7);
        g.addEdge(3,7);*/
        //graph from notebook
        /*g.addEdge(0,4);
        g.addEdge(1,4);
        g.addEdge(1,7);
        g.addEdge(2,5);
        g.addEdge(2,6);
        g.addEdge(3,4);
        g.addEdge(3,5);
        g.addEdge(0,6);
        g.addEdge(1,5);
        g.addEdge(1,6);
        g.addEdge(1,9);
        g.addEdge(2,6);
        g.addEdge(2,8);
        g.addEdge(3,7);
        g.addEdge(3,9);
        g.addEdge(4,6);*/

        RandomGraph randGraph = new RandomGraph(10);
        g = randGraph.getNewRandomGraph();
        ReadibilityCalculator rb = new ReadibilityCalculator(g, 5);
        if (rb.calculate()) {
            g.printVerticesToFile("graphRandom.txt");
        } else {
            System.out.println("Upper bound should be greater");
        }
        /*for(int i=0; i<10; i++) {
            g = randGraph.getNewRandomGraph();
            ReadibilityCalculator rb = new ReadibilityCalculator(g, 10);
            if (rb.calculate()) {
                g.printVerticesToFile("graph"+i+".txt");
            } else {
                System.out.println("Upper bound should be greater");
            }
        }*/

    }
}
