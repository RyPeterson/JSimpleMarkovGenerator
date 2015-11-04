package com.peterson.markovchain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Peterson, Ryan
 *         Created: 6/9/15
 */
public class BasicMarkovTest
{
    protected MarkovChain markovChain;

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
    public void testGenerateOnSeedStart()
    {
        testOnSeed("To");
    }

    public void testGenerateOnSeedMid()
    {
        testOnSeed("that");
    }

    public void testGenerateOnSeedEnd()
    {
        testOnSeed("question");
    }

    public void testGenerateOnSeedNoExistant()
    {
        testOnSeed("foobar");
    }

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
            result = markovChain.generateSentence(seed);
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
