package com.peterson.markovchain;

import java.util.Arrays;
import java.util.Collection;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * A fake RNG for unit testing
 */
public class MockRandom extends Random
{
    private Queue<Integer> randoms;

    public MockRandom(Integer ...randList)
    {
        this(Arrays.asList(randList));
    }

    public MockRandom(Collection<Integer> ints)
    {
        //TODO: will this be needed for concurrent tests?
        randoms = new ArrayBlockingQueue<>(ints.size(), true);
        randoms.addAll(ints);
    }

    @Override
    public int nextInt(int bound)
    {
        int rand = randoms.remove();
        if(rand > bound)
        {
            rand = bound;
        }
        return rand;
    }
}
