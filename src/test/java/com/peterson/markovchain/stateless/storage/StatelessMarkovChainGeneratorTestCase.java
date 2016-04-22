package com.peterson.markovchain.stateless.storage;

import com.peterson.markovchain.stateless.StatelessMarkovChainGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by Ryan Peterson on 4/21/2016.
 */
public class StatelessMarkovChainGeneratorTestCase
{
    private StatelessMarkovChainGenerator<String> markovChainGenerator;
    private MapBasedMarkovChainStorage<String> storage;

    @Before
    public void setUp()
    {
        storage = new MapBasedMarkovChainStorage<>();
        markovChainGenerator = new StatelessMarkovChainGenerator<>(storage);
    }

    @Test
    public void testLearn() throws Exception
    {
        //Arrays.asList returns an AbstractList, and .remove is not supported...

        //watch out for hash collisions when testing
        Queue<String> learningSetOne = new LinkedList<>(Arrays.asList("She sells seashells by the seashore.".split("\\s")));
        Queue<String> learningSetTwo = new LinkedList<>(Arrays.asList("To be or not to be, that is the question".split("\\s")));
        Queue<String> learningSetThree = new LinkedList<>(Arrays.asList("Hello, my name is Ryan".split("\\s")));

        markovChainGenerator.learn(() -> learningSetOne.isEmpty() ? null : learningSetOne.remove());

        markovChainGenerator.learn(() -> learningSetTwo.isEmpty() ? null : learningSetTwo.remove());

        markovChainGenerator.learn(() -> learningSetThree.isEmpty() ? null : learningSetThree.remove());

        List<String> heads = storage.getHeads();

        Assert.assertNotNull(heads);

        List<String> expecteds = Arrays.asList("She sells seashells by the seashore.".split("\\s"));
        String end = expecteds.get(expecteds.size() - 1);
        String seashells = heads.get(0);
        for (String expected : expecteds)
        {
            Assert.assertEquals(expected, seashells);
            if(expected != end) //Legit String equality compare
            {
                seashells = storage.getNext(seashells).get(0);
            }
        }
    }

}