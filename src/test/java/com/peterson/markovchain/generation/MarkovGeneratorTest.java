package com.peterson.markovchain.generation;

import com.peterson.markovchain.random.RandomNumberStrategy;
import com.peterson.markovchain.state.BasicMarkovState;
import com.peterson.markovchain.state.MarkovState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Ryan on 2/19/2017.
 */
public class MarkovGeneratorTest
{
    MarkovState<String> state;
    MarkovGenerator<String> generator;
    MockRandomStrategy randomNumberStrategy;

    @Before
    public void setUp() throws Exception
    {
        state = new BasicMarkovState<>();
        randomNumberStrategy = new MockRandomStrategy();
        generator = new MarkovGenerator<>(randomNumberStrategy);
        //0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    }

    @Test
    public void testApply()
    {
        //test npe
        Assert.assertFalse(generator.apply("", null).isPresent());
        //Note: ConcurrentHashMaps blow up with null, but the chain object should prevent this
        Assert.assertFalse(generator.apply(null, state).isPresent());

        state.put("current1", "next1");

        randomNumberStrategy.enqueue(0);
        Assert.assertEquals("next1", generator.apply("current1", state).get());

        state.put("current1", "next2");
        //grab second value in the list
        randomNumberStrategy.enqueue(1);
        Assert.assertEquals("next2", generator.apply("current1", state).get());
        //Get the first
        randomNumberStrategy.enqueue(0);
        Assert.assertEquals("next1", generator.apply("current1", state).get());

        state.put("current2", "next3");
        //grab the only one associated with current2
        randomNumberStrategy.enqueue(0);
        Assert.assertEquals("next3", generator.apply("current2", state).get());

        state.put("current2", "next1");
        randomNumberStrategy.enqueue(1);
        Assert.assertEquals("next1", generator.apply("current2", state).get());
    }

    private static class MockRandomStrategy implements RandomNumberStrategy
    {
        private final Queue<Integer> nextInts;

        private MockRandomStrategy()
        {
            this.nextInts = new LinkedList<>();
        }

        @Override
        public int nextInt(int upperBound)
        {
            return nextInts.remove();
        }

        public void enqueue(int value)
        {
            this.nextInts.add(value);
        }

        public void enqueue(Integer ...values)
        {
            this.nextInts.addAll(Arrays.asList(values));
        }
    }

}