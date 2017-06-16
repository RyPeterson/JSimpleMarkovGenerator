package com.peterson.markovchain;

import com.peterson.markovchain.random.RandomNumberStrategy;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * @author Peterson, Ryan
 *         Created: 6/9/15
 */
public class StringBasedMarkovChainTestCase
{
    protected StringMarkovChain markovChain;
    private static Random rand = new Random();

    @Before
    public void setUp()
    {
        markovChain = new StringMarkovChain(false);
    }

    @After
    public void resetObject()
    {
        setUp();
    }

    private void loadChain() throws IOException
    {
        String []phrases = TestUtil.getTestData();
        markovChain.addPhrase(new ArrayList<>(Arrays.asList(phrases)));
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
            markovChain.addPhrase(new ArrayList<>());
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
                markovChain.addPhrase(Arrays.asList(toSpam));
            }
        }
        catch (Exception e)
        {
            exceptionThrown = true;
        }

        Assert.assertFalse(exceptionThrown);
    }


    @Test
    public void practicalTest()
    {
        RandomNumberStrategy rand = new MockRandomNumberStrategy(0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        markovChain.setRand(rand);

        markovChain.addPhrase(Arrays.asList("She sells seashells by the seashore.".split(" ")));
        markovChain.addPhrase(Arrays.asList("She sells oddball by the seashore.".split(" ")));
        markovChain.addPhrase(Arrays.asList("She sells seashells by the dozen.".split(" ")));
        markovChain.addPhrase(Arrays.asList("She sells seashells by the ounce.".split(" ")));
        markovChain.addPhrase(Arrays.asList("He sells barbells by the redbull.".split(" ")));

        //fine with a spaces on either end
        Collection<String> result = markovChain.generateSentence();


        Assert.assertEquals("She sells seashells by the seashore.", toString(result));

        rand = new MockRandomNumberStrategy(0,0,1,0,0,0,0,0,0,0,0,0,0,0);
        markovChain.setRand(rand);
        result = markovChain.generateSentence();

        Assert.assertEquals("She sells oddball by the seashore.", toString(result));

        rand = new MockRandomNumberStrategy(0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0);
        markovChain.setRand(rand);
        result = markovChain.generateSentence();

        Assert.assertEquals("She sells seashells by the dozen.", toString(result));

        rand = new MockRandomNumberStrategy(0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0);
        markovChain.setRand(rand);
        result = markovChain.generateSentence();

        Assert.assertEquals("She sells seashells by the ounce.", toString(result));

        rand = new MockRandomNumberStrategy(4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        markovChain.setRand(rand);
        result = markovChain.generateSentence();

        Assert.assertEquals("He sells seashells by the seashore.", toString(result));

        rand = new MockRandomNumberStrategy(4,0,4,0,0,4,0,0,0,0,0,0,0,0,0,0);
        markovChain.setRand(rand);
        result = markovChain.generateSentence();

        Assert.assertEquals("He sells barbells by the redbull.", toString(result));

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
                Collection<String> temp = markovChain.generateSentence();
                Assert.assertNotNull(temp);
                Assert.assertTrue(temp.size() > 0);
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

    private List<String> generateSentence()
    {
        final int sentenceLen = rand.nextInt(100) + 50;
        List<String> b = new ArrayList<>();
        for(int i = 0; i < sentenceLen; i++)
        {
            b.add(genString());
        }
        return b;
    }


    private static String toString(Collection<String> collection)
    {
        StringBuilder builder = new StringBuilder();
        List<String> listView = new ArrayList<>(collection);
        for(int i = 0; i < listView.size(); i++)
        {
            builder.append(listView.get(i));
            if(i < listView.size() - 1)
            {
                builder.append(" ");
            }
        }
        System.out.println(builder);
        return builder.toString();
    }
}
