package com.jovicic.vladan;

import ilog.concert.IloIntVar;
import ilog.cplex.IloCplex;

import java.util.Vector;
import java.util.concurrent.CountDownLatch;

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
    private CountDownLatch latch;
    private rReadibilityCalc calc;

    public ParallelrReadibilityCalc(Graph gg, int rr, IloIntVar[][][][] xtvar, IloCplex mmodel, int [] lncoord, int [] rncoord, int [] lrcoord, int [] rrcoord, CountDownLatch bar, rReadibilityCalc ccalc)
    {
        g = gg;
        r = rr;
        xvar = xtvar;
        model = mmodel;
        this.lncoord = lncoord;
        this.rncoord = rncoord;
        this.lrcoord = lrcoord;
        this.rrcoord = rrcoord;
        latch = bar;
        calc = ccalc;
    }

    public void run()
    {
        if (type == 'T')
        {
            transitivity();
        }
        latch.countDown();
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
                                            if(u == 2 && i == 2 && v == 6 && j == 1 && w == 1 && k == 2 && q == 5 && l == 1)
                                            {
                                                System.out.println("Hm pa koji ti je moj");
                                            }
                                            //System.out.println("Adding transitivity for ("+u+","+i+") ("+v+","+j+") ("+w+","+k+") ("+q+","+l+") (");
                                            calc.model.addGe(calc.model.sum(calc.model.constant(3), calc.model.prod(-1, calc.xvar[u][v][i][j]),
                                                    calc.model.prod(-1, calc.xvar[w][v][l][k]), calc.model.prod(-1, calc.xvar[w][q][k][l]),
                                                    calc.model.prod(1, calc.xvar[u][q][i][l])),1);
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
