package com.peterson.markovchain;

import com.peterson.markovchain.generation.MarkovGenerator;
import com.peterson.markovchain.random.ThreadLocalRandomNumberStrategy;
import com.peterson.markovchain.state.ConcurrentMarkovState;

import java.util.regex.Pattern;

/**
 * Extension of the BasicMarkovChain to provide synchronized access to the generator.
 * This overrides the basic structure of the generator to use a synchronized map and lists.
 * @author Peterson, Ryan
 *         Created: 6/8/15
 */
public class ConcurrentBasicMarkovChain extends AbstractMarkovChain
{

    /**
     * Constructs a synchronized version of the generator.
     * This provides all the basic functionality to generate
     * markov chains, as described in the parent class.
     */
    public ConcurrentBasicMarkovChain()
    {
        super(new MarkovGenerator<>(new ThreadLocalRandomNumberStrategy()), new ConcurrentMarkovState<>());
    }

    public ConcurrentBasicMarkovChain(Pattern splitPattern)
    {
        super(new MarkovGenerator<>(new ThreadLocalRandomNumberStrategy()), new ConcurrentMarkovState<>());
        setSplitPattern(splitPattern);
    }
}
