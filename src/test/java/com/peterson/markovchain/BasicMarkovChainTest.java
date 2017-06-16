package com.peterson.markovchain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by Ryan on 6/15/2017.
 */
public class BasicMarkovChainTest
{
    BasicMarkovChain<Integer> chain;
    MockRandomNumberStrategy randomNumberStrategy;

    @Before
    public void setUp() throws Exception
    {
        chain = new BasicMarkovChain<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Test
    public void testAddPhrase()
    {
        chain.addPhrase(null);

        chain.addPhrase(new ArrayList<>());

        chain.addPhrase(Collections.singletonList(1));

        chain.addPhrase(Arrays.asList(1, 2, 3));

        for(int i = 2; i < 1000; i++)
        {
            int[] vals = new Random().ints(1, i).limit(i).toArray();
            List<Integer> boxed = new ArrayList<>(vals.length);
            for(int number : vals)
            {
                boxed.add(number);
            }
            chain.addPhrase(boxed);
        }
    }

    @Test
    public  void testGenerate()
    {
        chain.addPhrase(null);
        chain.addPhrase(new ArrayList<>());


        chain.addPhrase(Arrays.asList(1, 2, 3));
        Assert.assertEquals(Arrays.asList(1, 2, 3), chain.generateSentence());

        randomNumberStrategy = new MockRandomNumberStrategy(0, 0, 0);
        chain.setRand(randomNumberStrategy);

        chain.addPhrase(Arrays.asList(1, 5, 7));
        Assert.assertEquals(Arrays.asList(1, 2, 3), chain.generateSentence());

        //1 -> 5 -> 7
        randomNumberStrategy = new MockRandomNumberStrategy(1, 1, 0, 0, 0, 0, 0);
        chain.setRand(randomNumberStrategy);

        Assert.assertEquals(Arrays.asList(1, 5, 7), chain.generateSentence());

        chain.addPhrase(Arrays.asList(1, 5, 6));

        randomNumberStrategy = new MockRandomNumberStrategy(2, 2, 1, 0, 0, 0, 0);
        chain.setRand(randomNumberStrategy);

        Assert.assertEquals(Arrays.asList(1, 5, 6), chain.generateSentence());

        chain.addPhrase(Arrays.asList(Integer.MAX_VALUE, 0, Integer.MAX_VALUE));
        randomNumberStrategy = new MockRandomNumberStrategy(3, 2, 2, 0, 0, 0, 0);
        chain.setRand(randomNumberStrategy);

        //Yea, the generator gets a little screwy if one of the placeholders is used.
        Assert.assertEquals(Arrays.asList(Integer.MAX_VALUE, 6), chain.generateSentence());
    }

}