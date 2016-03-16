package com.jovicic.vladan;
import ilog.concert.*;
import ilog.cplex.*;

import java.util.Vector;

/**
 * Created by vlada on 2/26/2016.
 */
public class ReadibilityCalculator {
    public Graph g;
    private int ub;
    private int lb = 1;

    public ReadibilityCalculator(Graph gg, int upb)
    {
        g = gg;
        ub = upb;
    }

    public ReadibilityCalculator(Graph gg, int upb, int lwb)
    {
        g = gg;
        ub = upb;
        lb = lwb;
    }

    public boolean calculate()
    {
        rReadibilityCalc calc;
        int lo = lb, hi = ub;
        while(lo<hi)
        {
            int mid = (lo+hi)/2;
            System.out.println("Provjeravam za "+mid);
            calc = new rReadibilityCalc(g, mid);
            if(calc.isrReadibility())
            {
                hi = mid;
            }
            else
            {
                lo = mid+1;
            }
        }
        calc = new rReadibilityCalc(g, hi);
        if (calc.isrReadibility())
        {
            //nasao sam ga
            g.setReadibility(hi);
            calc.setLabeling(g);
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean isReadibilityExactly(int r, boolean label)
    {
        rReadibilityCalc calc = new rReadibilityCalc(g, r);
        if(calc.isrReadibility())
        {
            if(label)
            {
                g.setReadibility(r);
                calc.setLabeling(g);
            }
            return true;
        }
        return false;
    }
}
