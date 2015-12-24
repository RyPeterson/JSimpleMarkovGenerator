package com.peterson.markovchain;

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
    protected MarkovChain markovChain;
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
}
