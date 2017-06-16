package com.peterson.markovchain;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Peterson, Ryan
 *         Created: 6/9/15
 */
public class SimpleThread implements Runnable
{
    private MarkovChain<String> sharedInstance;
    private CyclicBarrier barrierInstance;
    private String []phrases;
    private boolean exceptionThrown;

    public SimpleThread(MarkovChain<String> markInstance, CyclicBarrier barrier)
    {
        sharedInstance = markInstance;
        barrierInstance = barrier;
        phrases = TestUtil.getTestData();

    }

    @Override
    public void run()
    {
        try
        {
            barrierInstance.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (BrokenBarrierException e)
        {
            e.printStackTrace();
        }

        try
        {
            this.sharedInstance.addPhrase(Arrays.asList(phrases));
        }
        catch (Exception e)
        {
            exceptionThrown = true;
        }
    }

    public boolean exceptionThrown()
    {
        return exceptionThrown;
    }
}
