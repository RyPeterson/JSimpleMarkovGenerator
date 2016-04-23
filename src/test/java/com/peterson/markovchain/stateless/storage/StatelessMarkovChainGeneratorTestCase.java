package com.peterson.markovchain.stateless.storage;

import com.google.common.collect.ImmutableSet;
import com.peterson.markovchain.stateless.StatelessMarkovChainGenerator;
import com.peterson.markovchain.stateless.functions.MarkovStateTransitionFunction;
import com.peterson.markovchain.stateless.functions.StateTransitionFunction;
import com.peterson.markovchain.stateless.random.BasicRandomNumberStrategy;
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

    @Test
    public void testGenerator()
    {
        Queue<String> learningSetOne = new LinkedList<>(Arrays.asList("She sells seashells by the seashore.".split("\\s")));
        markovChainGenerator.learn(() -> learningSetOne.isEmpty() ? null : learningSetOne.remove());
        List<String> expectedResults = Arrays.asList("She sells seashells by the seashore.".split("\\s"));
        StateTransitionFunction<String, String> transition = new MarkovStateTransitionFunction<>(new BasicRandomNumberStrategy(), storage);

        Assert.assertEquals(expectedResults, markovChainGenerator.generateChain(transition));

        Queue<String> learningSetTwo = new LinkedList<>(Arrays.asList("To be or not to be, that is the question".split("\\s")));
        Queue<String> learningSetThree = new LinkedList<>(Arrays.asList("Hello, my name is Ryan".split("\\s")));
        markovChainGenerator.learn(() -> learningSetTwo.isEmpty() ? null : learningSetTwo.remove());

        markovChainGenerator.learn(() -> learningSetThree.isEmpty() ? null : learningSetThree.remove());

        Set<String> usedStrings = ImmutableSet.<String>builder()
                .addAll(Arrays.asList("She sells seashells by the seashore.".split("\\s")))
                .addAll(Arrays.asList("To be or not to be, that is the question".split("\\s")))
                .addAll(Arrays.asList("Hello, my name is Ryan".split("\\s")))
                .build();

        Map<String, Integer> counts = new HashMap<>();
        usedStrings.forEach((s) -> counts.put(s, 0));

        for(int i = 0; i < 1000; i++)
        {
            Collection<String> results = markovChainGenerator.generateChain(transition);

            Assert.assertNotNull(results);
            for(String s : results)
            {
                Assert.assertNotNull(s);
                Assert.assertTrue(counts.containsKey(s));
                Integer v = counts.get(s);
                v++;
                counts.put(s, v);
            }
        }
    }

}