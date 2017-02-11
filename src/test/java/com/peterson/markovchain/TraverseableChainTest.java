package com.peterson.markovchain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Peterson, Ryan
 *         Created: 6/9/15
 */
public class TraverseableChainTest extends BasicMarkovTest
{
    @Before
    @Override
    public void setUp()
    {
        markovChain = new SeedableMarkovChain();
    }


    @Test
    public void testGenerateOnSeedStart()
    {
        testOnSeed("To");
    }

    @Test
    public void testGenerateOnSeedMid()
    {
        testOnSeed("that");
    }

    @Test
    public void testGenerateOnSeedEnd()
    {
        testOnSeed("question");
    }

    @Test
    public void testGenerateOnSeedNotExistent()
    {
        testOnSeed("foobar");
    }

    @Test
    public void testGenerateOnNullSeed()
    {
        testOnSeed(null);
    }

    private void testOnSeed(String seed)
    {
        String result = null;
        boolean thrown = false;

        try
        {
            result = ((AbstractSeedableMarkovChain)markovChain).generateSentence(seed);
        }
        catch (Exception e)
        {
            thrown = true;
            e.printStackTrace();
        }

        Assert.assertFalse(thrown);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.length() != 0);
    }

}
