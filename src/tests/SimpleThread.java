package tests;

import com.peterson.markovchain.MarkovChain;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Peterson, Ryan
 *         Created: 6/9/15
 */
public class SimpleThread implements Runnable
{
    private MarkovChain sharedInstance;
    private CyclicBarrier barrierInstance;
    private String []phrases;
    private boolean exceptionThrown;

    public SimpleThread(MarkovChain markInstance, CyclicBarrier barrier)
    {
        sharedInstance = markInstance;
        barrierInstance = barrier;
        try
        {
            phrases = TestUtil.loadFile();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
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
            for (String s : phrases)
                sharedInstance.addPhrase(s);
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
