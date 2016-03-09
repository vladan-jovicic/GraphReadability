package com.jovicic.vladan;
import ilog.concert.*;
import ilog.cplex.*;

import java.util.Vector;

/**
 * Created by vlada on 3/8/2016.
 */
public class rReadibilityCalc {

    private Graph g;
    private IloIntVar [][][][] xvar;
    private int r;
    private IloIntVar [][] zvar;
    private IloCplex model;
    private Vector<Touple> tuples;

    public rReadibilityCalc(Graph gg, int rr)
    {
        this.r = rr;
        g = gg;
        xvar = new IloIntVar[g.n][g.n][r+1][];
        zvar = new IloIntVar[g.eSize][];
        try {
            model = new IloCplex();
            model.addMaximize(model.constant(1));
            for (int u = 0; u < g.n; u++) {
                for (int v = 0; v < g.n; v++) {
                    for (int i = 0; i <= r; i++) {
                        xvar[u][v][i] = model.boolVarArray(r+1);
                    }
                }
            }
            for(int i=0; i<g.eSize; i++)
            {
                zvar[i] = model.boolVarArray(r+1);
            }
        }
        catch (Exception e)
        {
            System.out.println("Failed to initialize model!");
            e.printStackTrace();
        }
    }
    public boolean isrReadibility()
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
                        IloLinearIntExpr expr = model.linearIntExpr();
                        //sad za svaku poziciju i=r ... 1
                        for(int i=r; i>=1; i--)
                        {
                            //dodaj sve varijable
                            expr.addTerm(1, zvar[eCnt][i]);
                            IloLinearIntExpr expr1 = model.linearIntExpr();
                            int othSide = 1;
                            for(int j=i; j>=1; j--)
                            {
                                //xvars[u][v][r-j+1][j]
                                expr1.addTerm(1, xvar[u][v][r-j+1][othSide++]);
                            }
                            model.addGe(model.sum(expr1,model.prod(-i, zvar[eCnt][i])),0);
                        }
                        eCnt++;
                        model.addGe(expr, 1);
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
                            IloLinearIntExpr expr = model.linearIntExpr();
                            int othSide = 1;
                            for(int j = i; j>= 1; j--)
                            {
                                expr.addTerm(-1, xvar[u][v][r-j+1][othSide++]);
                            }
                            model.addGe(model.sum(model.constant(i), expr), 1);
                        }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                        System.out.println("Failed to initialize expression for non-edge");
                    }
                }
            }
        }
        //sad tranzitivnost
        /*for(int u=0; u<g.n/2; u++)
        {
            for(int v = g.n/2; v < g.n; v++)
            {
                for(int w = 0; w<g.n/2; w++)
                {

                    for(int i=1; i<=r; i++)
                    {
                        for(int j=1; j<=r; j++)
                        {
                            for(int l=1; l<=r; l++)
                            {
                                try {
                                    model.addGe(model.sum(model.constant(2), model.prod(-1, xvar[u][v][i][j]),
                                            model.prod(-1,xvar[v][w][j][l]), model.prod(1, xvar[u][w][i][l])),1);
                                } catch (Exception e)
                                {
                                    System.out.println("Failed to add transitivity constraints");
                                }

                            }
                        }
                    }
                }
            }
        }*/
        System.out.println("Adding transitivity constraints");
        for(int u=0; u<g.n/2; u++)
        {
            for(int v = g.n/2; v < g.n; v++)
            {
                for(int w = 0; w<g.n/2; w++)
                {
                    for(int q = g.n/2; q < g.n; q++) {

                        for (int i = 1; i <= r; i++) {
                            for (int j = 1; j <= r; j++) {
                                for (int k = 1; k <= r; k++) {
                                    for(int l = 1; l<=r; l++)
                                    {
                                        try {
                                            model.addGe(model.sum(model.constant(3), model.prod(-1, xvar[u][v][i][j]),
                                                    model.prod(-1, xvar[v][w][k][l]), model.prod(-1, xvar[w][q][k][l]),
                                                    model.prod(1, xvar[u][q][i][l])),1);
                                        } catch (Exception e)
                                        {
                                            System.out.println("Failed to add transitivity constraints");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //sad simetricnost
        System.out.println("Adding equivalence constraints");
        for(int u = 0; u < g.n/2; u++)
        {
            for(int v = g.n/2; v < g.n; v++)
            {
                for(int i=1; i<=r; i++)
                {
                    for(int j=1; j<=r; j++)
                    {

                        // a                    b
                        //vars[u][v][i][j] => vars[v][u][j][i] and vars[v][u][j][i] => vars[u][v][i][j]
                        //(not a or b) and (not b or a)
                        try {
                            model.addGe(model.sum(model.constant(1), model.prod(-1, xvar[u][v][i][j]),
                                    model.prod(1, xvar[v][u][j][i])), 1);
                            model.addGe(model.sum(model.constant(1), model.prod(-1, xvar[v][u][j][i]),
                                    model.prod(1, xvar[u][v][i][j])), 1);
                        } catch(Exception e)
                        {
                            System.out.println("Failed to add simetricity constraints");
                        }
                    }
                }
            }
        }
        try {
            System.out.println("Calculation finished! Trying to solve a model ....");
            model.solve();
            System.out.println(model.getStatus().toString());
            if(model.getStatus() == IloCplex.Status.Optimal)
            {
                tuples = new Vector<Touple>();
                for(int u=0; u<g.n; u++)
                {
                    for(int v = 0; v < g.n; v++)
                    {
                        for(int i=1; i<=r; i++)
                        {
                            for(int j=1; j<=r; j++)
                            {
                                //System.out.println(model.getValue(xvar[u][v][i][j]));
                                if(model.getValue(xvar[u][v][i][j]) == 1)
                                {
                                    Touple t = new Touple(4);
                                    t.setTouple(new int[] {u,v,i,j});
                                    tuples.add(t);
                                    //if()
                                    //System.out.println("("+u+","+i+") ("+v+","+j+")");
                                }
                            }
                        }
                    }
                }
                System.out.println("Graph has readibility " + r); //tu jos treba da ispitas koji su jednaki
                return true;
            }
            else if(model.getStatus() == IloCplex.Status.Infeasible)
            {
                System.out.println("Graph has no readibility " + r);
                return false;
            }
        } catch (Exception e)
        {
            System.out.println("Failed in solving model!");
        }
        return false;
    }

    public Vector<Touple> getTuples ()
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
