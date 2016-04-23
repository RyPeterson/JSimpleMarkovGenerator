package com.peterson.markovchain.stateless.random;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ryan Peterson on 4/22/2016.
 */
public class ThreadLocalRandomNumberStrategy implements RandomNumberStrategy
{
    @Override
    public int nextInt(int upperBound)
    {
        return ThreadLocalRandom.current().nextInt(upperBound);
    }
}
