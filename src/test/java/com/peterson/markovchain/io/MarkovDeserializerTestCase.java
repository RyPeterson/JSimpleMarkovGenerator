package com.peterson.markovchain.io;

import com.peterson.markovchain.BasicMarkovChain;
import com.peterson.markovchain.ConcurrentBasicMarkovChain;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Ryan Peterson on 01/10/2016
 */
public class MarkovDeserializerTestCase
{
    @Test
    public void testDeserialize()
    {
        BasicMarkovChain chain = new BasicMarkovChain();
        chain.fromJSON(JSONTestHelper.BASIC_CHAIN);
        boolean found = false;
        String toSearch = "Hello";
        for(int i = 0; i < 100; i++)
        {
            if(chain.generateSentence().contains(toSearch))
            {
                found = true;
                break;
            }
        }

        Assert.assertTrue(found);
    }

    @Test
    public void testConcurrentChainDeserialize()
    {
        BasicMarkovChain chain = new ConcurrentBasicMarkovChain();
        chain.fromJSON(JSONTestHelper.BASIC_CHAIN);
        boolean found = false;
        String toSearch = "Hello";
        for(int i = 0; i < 100; i++)
        {
            if(chain.generateSentence().contains(toSearch))
            {
                found = true;
                break;
            }
        }

        Assert.assertTrue(found);
    }
}
