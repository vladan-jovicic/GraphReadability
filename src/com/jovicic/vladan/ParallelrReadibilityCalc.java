package com.jovicic.vladan;

import ilog.concert.IloIntVar;
import ilog.cplex.IloCplex;

import java.util.Vector;

/**
 * Created by vlada on 3/9/2016.
 */
public class ParallelrReadibilityCalc implements Runnable {

    private Graph g;
    private IloIntVar[][][][] xvar;
    private int r;
    private IloIntVar [][] zvar;
    private IloCplex model;
    private Vector<Tuple> tuples;
    //private int lnstart, lnend, rnstart, rnend, rstart, rend;
    private int [] lncoord, rncoord, lrcoord, rrcoord;
    private char type = 'T';

    public ParallelrReadibilityCalc(Graph gg, int rr, IloIntVar[][][][] xtvar, IloCplex mmodel, int [] lncoord, int [] rncoord, int [] lrcoord, int [] rrcoord)
    {
        g = gg;
        r = rr;
        xvar = xtvar;
        model = mmodel;
        this.lncoord = lncoord;
        this.rncoord = rncoord;
        this.lrcoord = lrcoord;
        this.rrcoord = rrcoord;
    }

    public void run()
    {
        if (type == 'T')
        {
            transitivity();
        }
    }

    public void transitivity()
    {
        for(int u = lncoord[0]; u<lncoord[1]; u++ )
        {
            for(int v = rncoord[0]; v<rncoord[1]; v++)
            {
                for (int w = lncoord[0]; w<lncoord[1]; w++)
                {
                    for(int q = rncoord[0]; q<rncoord[1]; q++)
                    {
                        for(int i = lrcoord[0]; i<= lrcoord[1]; i++)
                        {
                            for (int j = rrcoord[0]; j<= rrcoord[1]; j++)
                            {
                                for(int k = lrcoord[0]; k<= lrcoord[1]; k++)
                                {
                                    for(int l = rrcoord[0]; l<= rrcoord[1]; l++)
                                    {
                                        try {
                                            model.addGe(model.sum(model.constant(3), model.prod(-1, xvar[u][v][i][j]),
                                                    model.prod(-1, xvar[v][w][k][l]), model.prod(-1, xvar[w][q][k][l]),
                                                    model.prod(1, xvar[u][q][i][l])),1);
                                        }catch (Exception e)
                                        {
                                            System.out.println("Failed to add transitivity constraint");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
