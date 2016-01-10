package com.peterson.markovchain.io;

import com.peterson.markovchain.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Ryan Peterson on 01/10/2016
 */
public class MarkovSerializerTestCase
{

    public void setUp(MarkovChain chain)
    {
        chain.addPhrase("Test");
        chain.addPhrase("Hello World");
        chain.addPhrase("She sell seashells by the seashore");
    }

    @Test
    public void testSerializer()
    {
        BasicMarkovChain chain = new BasicMarkovChain();
        setUp(chain);
        String serialized = chain.toJSON();

        Assert.assertEquals(JSONTestHelper.BASIC_CHAIN, serialized);
    }

    @Test
    public void testSerialzerConcurrentChain()
    {
        //the actual hash map should have the same data as the concurrent version
        ConcurrentBasicMarkovChain chain = new ConcurrentBasicMarkovChain();
        setUp(chain);
        String serialized = chain.toJSON();

        Assert.assertEquals(JSONTestHelper.BASIC_CHAIN, serialized);
    }
}
