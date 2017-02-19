package com.peterson.markovchain.random;

import java.util.Random;

/**
 * Created by Ryan Peterson on 4/22/2016.
 */
public class BasicRandomNumberStrategy implements RandomNumberStrategy
{
    private final Random rand;

    public BasicRandomNumberStrategy()
    {
        this(System.currentTimeMillis());
    }

    public BasicRandomNumberStrategy(long seed)
    {
        rand = new Random(seed);
    }

    @Override
    public int nextInt(int upperBound)
    {
        return rand.nextInt(upperBound);
    }
}
