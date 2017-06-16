package com.peterson.markovchain;

import com.peterson.markovchain.random.RandomNumberStrategy;

/**
 * Created by Ryan on 6/15/2017.
 */
public class MockRandomNumberStrategy implements RandomNumberStrategy
{
    private MockRandom rand;

    public MockRandomNumberStrategy(Integer... randoms)
    {
        rand = new MockRandom(randoms);
    }

    @Override
    public int nextInt(int upperBound)
    {
        return rand.nextInt(upperBound);
    }
}
