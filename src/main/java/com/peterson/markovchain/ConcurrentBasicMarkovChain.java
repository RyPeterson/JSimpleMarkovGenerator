package com.peterson.markovchain;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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
        super();
        super.markovChain = new ConcurrentHashMap<>(super.markovChain);
    }

    /**
     * Constructs a synchronized version of the generator using an initial set of phrases
     * @param phrases the initial phrases to seed the generator with
     */
    public ConcurrentBasicMarkovChain(String... phrases)
    {
        this();
        for(String s : phrases)
            super.addPhrase(s);
    }

    @Override
    protected List<String> newList()
    {
        //lord have mercy
        return new CopyOnWriteArrayList<>();
    }
}
