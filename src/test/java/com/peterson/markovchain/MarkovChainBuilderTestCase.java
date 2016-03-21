package com.peterson.markovchain;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by Ryan Peterson on 01/03/2016
 */
public class MarkovChainBuilderTestCase
{
    @Test
    public void testBuilder()
    {
        MarkovChain chain = new AbstractMarkovChain.Builder(false, false).build();
        Assert.assertTrue(chain instanceof BasicMarkovChain);

        chain = new AbstractMarkovChain.Builder(true, false).build();

        Assert.assertTrue(chain instanceof ConcurrentBasicMarkovChain);

        chain = new AbstractMarkovChain.Builder(false, true).build();

        Assert.assertTrue(chain instanceof SeedableMarkovChain);

        chain = new AbstractMarkovChain.Builder(true, true).build();

        Assert.assertTrue(chain instanceof ConcurrentSeedableMarkovChain);

    }

    @Test
    public void testBuilderFieldInitialization()
    {
        //just run a couple gens to make sure things are initialized
        MarkovChain chain = new AbstractMarkovChain.Builder().build();

        for(String s : TestUtil.getTestData())
        {
            chain.addPhrase(s);
        }

        for(int i = 0; i < 1000; i++)
        {
            String phrase = chain.generateSentence();
            Assert.assertNotNull(phrase);
            Assert.assertTrue(!phrase.isEmpty());
        }
    }
}
