package tests;

import com.peterson.markovchain.BasicMarkovChain;
import com.peterson.markovchain.MarkovChain;
import com.peterson.markovchain.SynchronizedBasicMarkovChain;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.util.SimpleElementVisitor6;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author Peterson, Ryan
 *         Created: 6/9/15
 */
public class SynchronizedBasicMarkovTest extends BasicMarkovTest
{
    public static final int NUM_THREADS = 10000;

    @Before
    @Override
    public void setUp()
    {
        markovChain = new SynchronizedBasicMarkovChain();
    }

    @Test
    public void testConcurrentPhraseAdd() throws BrokenBarrierException, InterruptedException
    {
        Runnable []threads = new Runnable[NUM_THREADS];
        CyclicBarrier barrier = new CyclicBarrier(threads.length + 1);
        for(int i = 0; i < threads.length; i++)
        {
            threads[i] = new SimpleThread(markovChain, barrier);
        }

        ExecutorService pool = Executors.newFixedThreadPool(threads.length);
        for(Runnable t : threads)
            pool.submit(t);
        barrier.await();

        for(Runnable t : threads)
        {
            Assert.assertFalse(((SimpleThread)t).exceptionThrown());
        }

    }


    private class SimpleThread implements Runnable
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
}
