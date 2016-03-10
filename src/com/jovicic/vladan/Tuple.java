package com.jovicic.vladan;

public class Tuple {
    private int [] vec;
    private int size;
    public Tuple(int n)
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
