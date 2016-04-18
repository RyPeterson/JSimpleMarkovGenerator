package com.peterson.markovchain;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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
    }

    @Override
    protected List<Link> newLinkList()
    {
        return new CopyOnWriteArrayList<>();
    }

    @Override
    protected Map<String, List<Link>> newSeedableMap()
    {
        return new ConcurrentHashMap<>();
    }
}
