package com.peterson.markovchain.io;

import com.peterson.markovchain.BasicMarkovChain;
import com.peterson.markovchain.MarkovChain;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Ryan Peterson on 11/07/2015
 */
public class GSONSerializationTestCase
{
    private MarkovChain markovChain;
    private GSONMarkovIO io;

    @Before
    public void setUp()
    {
        markovChain = new BasicMarkovChain();
        markovChain.addPhrase("She sells sea shells by the seashore");
        markovChain.addPhrase("To be or not to be, that is the question");
        markovChain.addPhrase("Hello, my name is Ryan");
        markovChain.addPhrase("To err is human; to forgive, divine");
        //not testing file io; just the loading and unloading the chain as JSON
        io = new LocalFileGSONMarkovIO(BasicMarkovChain.class, null);
    }

    @Test
    public void testSerialization()
    {
        String json = io.getJSON(markovChain);
        Assert.assertEquals(true, json.contains("Hello"));
        Assert.assertEquals(true, json.contains("Ryan"));
        Assert.assertEquals(true, json.contains("seashore"));
        Assert.assertEquals(true, json.contains("divine"));
        Assert.assertEquals(true, json.contains("human"));
        Assert.assertEquals(true, json.contains("sells"));
        Assert.assertEquals(true, json.contains("pattern"));
    }

    @Test
    public void testDeserialization() throws IOException
    {
        String json = io.getJSON(markovChain);
        MarkovChain newChain = new TestIO(BasicMarkovChain.class, json).load();

        Assert.assertEquals(markovChain.getClass(), newChain.getClass());

        String newJson = io.getJSON(newChain);
        Assert.assertEquals(json, newJson);
    }

    /**
     * Dummy class that allows testing...
     */
    private class TestIO extends LocalFileGSONMarkovIO
    {
        private String json;
        /**
         */
        public TestIO(Class<? extends MarkovChain> instanceType, String json)
        {
            super(instanceType, null);
            this.json = json;
        }

        @Override
        public byte[] getChainBytes()
        {
            return json.getBytes();
        }
    }
}
