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
        boolean found [] = new boolean[ub+1];
        boolean calculated [] = new boolean[ub+1];
        for(int i=0; i<ub+1; i++) {
            found[i] = false;
            calculated[i] = false;
        }
        while(lo<=hi)
        {
            int mid = (lo+hi)/2;
            if(calculated[mid])
                break;
            calculated[mid] = true;
            System.out.println("Provjeravam za "+mid);
            calc = new rReadibilityCalc(g, mid);
            int result = calc.isrReadibility();
            if(result == 1)
            {
                found[mid] = true;
                hi = mid-1;
            }
            else if(result == 0)
            {
                lo = mid+1;
            }
            else
            {
                //probably not
                lo = mid+1;
            }
        }
        boolean isInterval = false;
        for(int i=1; i<ub+1; i++)
        {
            if(found[i] == true)
            {
                isInterval = true;
                System.out.println("Lower bound: " + i);
                //g.setReadability(i);
                //calc.setLabeling(g);
                break;
            }
        }
        if(isInterval)
            return true;
        else
            return false;
    }
    public boolean isReadibilityExactly(int r, boolean label)
    {
        rReadibilityCalc calc = new rReadibilityCalc(g, r);
        if(calc.isrReadibility() == 1)
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