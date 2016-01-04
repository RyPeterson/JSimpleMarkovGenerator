package com.peterson.markovchain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * A basic synchronized version of the TraversableMarkovChain.
 * This class forms the base of synchronized access by using a
 * Synchronized map and lists.
 * @author Peterson, Ryan
 *         Created: 6/8/15
 */
public class ConcurrentTraversableMarkovChain extends TraversableMarkovChain
{
    /**
     * Constructs the SynchronizedTraversableMarkovChain.
     */
    public ConcurrentTraversableMarkovChain()
    {
        super();
        super.markovChain = new ConcurrentHashMap<>(super.markovChain);
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
        TraversableMarkovChain copy = new TraversableMarkovChain(pcopy);
        copy.markovChain = new ConcurrentHashMap<>(this.markovChain);
        copy.transformer = this.transformer;
        return copy;
    }
}
