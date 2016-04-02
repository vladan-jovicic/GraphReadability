package com.jovicic.vladan;
import ilog.concert.*;
import ilog.cplex.*;

import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;

/**
 * Created by vlada on 3/8/2016.
 */
public class rReadibilityCalc {

    private Graph g;
    public IloIntVar [][] xvar;
    private int r;
    private IloIntVar [][] zvar;
    public IloCplex model;
    private Vector<Tuple> tuples;
    private ParallelrReadibilityCalc [] threads;
    private CountDownLatch latch;

    public rReadibilityCalc(Graph gg, int rr)
    {
        this.r = rr;
        g = gg;
        xvar = new IloIntVar[g.n][];
        zvar = new IloIntVar[g.eSize][];
        try {
            model = new IloCplex();
            model.addMaximize(model.constant(1));
            int [] lowerBound = new int[rr+1];
            int [] upperBound = new int[rr+1];
            for(int i=0; i<=rr; i++)
            {
                lowerBound[i] = 0;
                upperBound[i] = 100;
            }
            for (int u = 0; u < g.n; u++) {
                        xvar[u] = model.intVarArray(r+1, lowerBound, upperBound); //size of array, lower bound upper bound
            }
            for(int i=0; i<g.eSize; i++)
            {
                zvar[i] = model.intVarArray(r+1, lowerBound, upperBound);
            }
        }
        catch (Exception e)
        {
            System.out.println("Failed to initialize model!");
            e.printStackTrace();
        }
    }
    public int isrReadibility()
    {
        int eCnt = 0;
        for(int u=0; u<g.n/2; u++)
        {
            for(int v = g.n/2; v<g.n; v++)
            {
                if(g.adjMatrix[u][v])
                {
                    //System.out.println("Dodajem za povezavu " + u + "," + v);
                    //model.addGe(asdas, 1);
                    try {
                        IloLinearNumExpr expr = model.linearNumExpr();

                        //dodam z_e1 + z_e2 + ... + z_er > 0
                        for(int i=1; i<=r; i++)
                        {
                            expr.addTerm(1, zvar[eCnt][i]);
                            int sumCnt = 0;
                            IloIntExpr [] eSum = new IloIntExpr[r-i+1];
                            for(int k=i; k<=r; k++)
                            {
                                ////sum_{k=i}^{r}{(x[u][k]-x[v][k-i+1])^2}+1 > 0
                                //i i k su mi fiksni
                                eSum[sumCnt++] = model.prod(model.diff(xvar[u][k], xvar[v][k-i+1]),  model.diff(xvar[u][k], xvar[v][k-i+1]));
                                model.addEq(model.sum(model.prod(zvar[eCnt][i],xvar[u][k]),model.prod(-1, model.prod(zvar[eCnt][i],xvar[v][k-i+1]))),0);
                            }
                            model.addGe(model.sum(zvar[eCnt][i], model.sum(eSum)),1);
                        }
                        model.addGe(expr, 1);
                        eCnt++;
                    } catch (Exception e)
                    {
                        System.out.println("Failed to initialize linear expression");
                    }

                }
                else
                {
                    //ako nema povezave
                    //moram uzeti negaciju
                    //a to je
                    try {
                        for (int i = r; i >= 1; i--) //za svaku poziciju
                        {
                            IloIntExpr [] eSum = new IloIntExpr[r-i+1];
                            int sumCnt = 0;
                            for(int k=i; k<=r; k++)
                            {
                                eSum[sumCnt++] = model.prod(model.diff(xvar[u][k], xvar[v][k-i+1]),  model.diff(xvar[u][k], xvar[v][k-i+1]));
                            }
                            model.addGe(model.sum(eSum), 1);
                        }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                        System.out.println("Failed to initialize expression for non-edge");
                    }
                }
            }
        }

        try
        {
            System.out.println("Calculation finished! Trying to solve a model ....");
            //model.setParam(IloCplex.IntParam.RootAlg, IloCplex.Algorithm.Auto);
            //model.setParam(IloCplex.IntParam.NodeLim, 15);
            //System.out.println(model.getAlgorithm());
            model.solve();
            if(model.getStatus() == IloCplex.Status.Optimal)
            {
                return  1;
            }
            else if(model.getStatus() == IloCplex.Status.Infeasible)
            {
                System.out.println("Graph has no readibility " + r);
                return 0;
            }
            else
            {
                return -1;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Failed in solving model!");
        }
        model.end();
        return -1;
    }

    public Vector<Tuple> getTuples ()
    {
        return tuples;
    }
    public void setLabeling(Graph g)
    {
        g.setReadibility(r);
        char current = 'a';
        for(int i=0; i<tuples.size(); i++)
        {
            int u = tuples.elementAt(i).getiThCoordinate(0);
            int v = tuples.elementAt(i).getiThCoordinate(1);
            int j = tuples.elementAt(i).getiThCoordinate(2);
            int l = tuples.elementAt(i).getiThCoordinate(3);
            if(g.getLabel(u,j) == 0 && g.getLabel(v,l) == 0)
            {
                g.setLabel(u,j,current);
                g.setLabel(v,l, current);
                current++;
            }
            else if(g.getLabel(u,j) != 0 && g.getLabel(v,l) == 0)
            {
                g.setLabel(v,l, (char) g.getLabel(u,j));
            }
            else
            {
                g.setLabel(u,j,(char) g.getLabel(v,l));
            }
        }

    }

}
