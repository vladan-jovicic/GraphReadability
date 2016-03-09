package com.jovicic.vladan;

/**
 * Created by vlada on 3/8/2016.
 */
public class Touple {
    private int [] vec;
    private int size;
    public Touple(int n)
    {
        size = n;
        vec =  new int[n];
    }


    public int getiThCoordinate(int i)
    {
        return vec[i];
    }

    public void setTouple(int[] tmp)
    {
        for(int i=0; i<size; i++)
        {
            vec[i] = tmp[i];
        }
    }
}
