package com.peterson.markovchain;

import com.peterson.markovchain.stateless.random.RandomNumberStrategy;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

/**
 * @author Peterson, Ryan
 *         Created: 6/9/15
 */
public class BasicMarkovTest
{
    protected AbstractMarkovChain markovChain;
    private static Random rand = new Random();

    @Before
    public void setUp()
    {
        markovChain = new BasicMarkovChain();
    }

    @After
    public void resetObject()
    {
        setUp();
    }

    private void loadChain() throws IOException
    {
        String []phrases = TestUtil.getTestData();
        for(String s : phrases)
            markovChain.addPhrase(s);
    }

    @Test
    public void testAddPhrase()
    {
        boolean exceptionThrown = false;

        try
        {
            loadChain();
        }
        catch (Exception e)
        {
            //file read fail means a class I am using for test isn't loading properly
            if(e instanceof IOException)
            {
                e.printStackTrace();
            }
            else
            {
                exceptionThrown = true;
            }
        }
        Assert.assertFalse(exceptionThrown);
    }

    @Test
    public void testNullInput()
    {
        boolean exceptionThrown = false;

        try
        {
            markovChain.addPhrase(null);
        }
        catch (NullPointerException e)
        {
            exceptionThrown = true;
        }

        Assert.assertFalse(exceptionThrown);
    }

    @Test
    public void testEmptyInput()
    {
        boolean exceptionThrown = false;

        try
        {
            markovChain.addPhrase("");
        }
        catch (StringIndexOutOfBoundsException e)
        {
            exceptionThrown = true;
        }

        Assert.assertFalse(exceptionThrown);
    }

    @Test
    public void testSpammedInput()
    {
        String []toSpam;

        toSpam = TestUtil.getTestData();


        boolean exceptionThrown = false;
        try
        {
            for (int i = 0; i < 100; i++)
            {
                for (String s : toSpam)
                    markovChain.addPhrase(s);
            }
        }
        catch (Exception e)
        {
            exceptionThrown = true;
        }

        Assert.assertFalse(exceptionThrown);
    }

    @Test
    public void testInputSaved()
    {
        try
        {
            loadChain();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        String target = "Ryan";

        boolean foundTarget = false;
        int missCount = 0;

        for(int i = 0; i < 1000; i++)
        {
            if(markovChain.generateSentence().contains(target))
            {
                foundTarget = true;
                break;
            }
            else
            {
                missCount++;
            }
        }

        Assert.assertTrue(foundTarget);
    }

    @Test
    public void practicalTest()
    {
        RandomNumberStrategy rand = new MockRandomNumberStrategy(0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        markovChain.setRand(rand);

        markovChain.addPhrase("She sells seashells by the seashore.");
        markovChain.addPhrase("She sells oddball by the seashore.");
        markovChain.addPhrase("She sells seashells by the dozen.");
        markovChain.addPhrase("She sells seashells by the ounce.");
        markovChain.addPhrase("He sells barbells by the redbull.");

        //fine with a spaces on either end
        String result = markovChain.generateSentence().trim();

        Assert.assertEquals("She sells seashells by the seashore.", result);

        rand = new MockRandomNumberStrategy(0,0,1,0,0,0,0,0,0,0,0,0,0,0);
        markovChain.setRand(rand);
        result = markovChain.generateSentence().trim();

        Assert.assertEquals("She sells oddball by the seashore.", result);

        rand = new MockRandomNumberStrategy(0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0);
        markovChain.setRand(rand);
        result = markovChain.generateSentence().trim();

        Assert.assertEquals("She sells seashells by the dozen.", result);

        rand = new MockRandomNumberStrategy(0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0);
        markovChain.setRand(rand);
        result = markovChain.generateSentence().trim();

        Assert.assertEquals("She sells seashells by the ounce.", result);

        rand = new MockRandomNumberStrategy(4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        markovChain.setRand(rand);
        result = markovChain.generateSentence().trim();

        Assert.assertEquals("He sells seashells by the seashore.", result);

        rand = new MockRandomNumberStrategy(4,0,4,0,0,4,0,0,0,0,0,0,0,0,0,0);
        markovChain.setRand(rand);
        result = markovChain.generateSentence().trim();

        Assert.assertEquals("He sells barbells by the redbull.", result);

    }


    @Test
    public void loadTest()
    {
        final int chunkMult = 10;
        int size = 10;

        for(int i = 0; i < 4; i++) //watch i unless you want to be here a while...
        {
            for(int j = 0; j < size; j++)
            {
                markovChain.addPhrase(generateSentence());
            }

            for(int j = 0; j < size; j++)
            {
                String temp = markovChain.generateSentence();
                Assert.assertNotNull(temp);
                Assert.assertTrue(temp.length() > 0);
            }

            size *= chunkMult;
        }
    }

    private String genString()
    {
        final int stringLen = rand.nextInt(50);
        char[] string = new char[stringLen];

        for(int i = 0; i < stringLen; i++)
        {
            string[i] = (char)(rand.nextInt(26) + 65);
        }

        return new String(string);
    }

    private String generateSentence()
    {
        final int sentenceLen = rand.nextInt(100) + 50;
        StringBuilder b = new StringBuilder();
        for(int i = 0; i < sentenceLen; i++)
        {
            b.append(genString()).append(" ");
        }
        return b.toString().trim();
    }

    static class MockRandomNumberStrategy implements RandomNumberStrategy
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
}
