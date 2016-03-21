package com.peterson.markovchain;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimaps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A basic synchronized version of the SeedableMarkovChain.
 * This class forms the base of synchronized access by using a
 * Synchronized map and lists.
 * @author Peterson, Ryan
 *         Created: 6/8/15
 */
public class ConcurrentSeedableMarkovChain extends SeedableMarkovChain
{
    /**
     * Constructs the SynchronizedTraversableMarkovChain.
     */
    public ConcurrentSeedableMarkovChain()
    {
        super();
        super.markovChain = Multimaps.synchronizedListMultimap(ArrayListMultimap.create());
    }

    @Override
    protected List<Link> newList()
    {
        return Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public MarkovChain copy()
    {
        final Pattern pcopy = Pattern.compile(super.splitPattern.pattern());
        SeedableMarkovChain copy = new SeedableMarkovChain(pcopy);
        copy.markovChain = Multimaps.synchronizedListMultimap(this.markovChain);
        copy.transformer = this.transformer;
        return copy;
    }
}
