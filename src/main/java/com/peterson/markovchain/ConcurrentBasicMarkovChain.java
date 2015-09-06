package com.peterson.markovchain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Extension of the BasicMarkovChain to provide synchronized access to the generator.
 * This overrides the basic structure of the generator to use a synchronized map and lists.
 * @author Peterson, Ryan
 *         Created: 6/8/15
 */
public class ConcurrentBasicMarkovChain extends BasicMarkovChain
{
    /**
     * Constructs a synchronized version of the generator.
     * This provides all the basic functionality to generate
     * markov chains, as described in the parent class.
     */
    public ConcurrentBasicMarkovChain()
    {
        super(true);
    }

    /**
     * Constructs a synchronized version of the generator using an initial set of phrases
     * @param phrases the initial phrases to seed the generator with
     */
    public ConcurrentBasicMarkovChain(String... phrases)
    {
        super(true);
        for(String s : phrases)
            super.addPhrase(s);
    }

    @Override
    protected List<String> newList()
    {
        return Collections.synchronizedList(new ArrayList<>());
    }
}
