package com.peterson.markovchain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        super(true);
    }

    @Override
    protected List<Link> newList()
    {
        return Collections.synchronizedList(new ArrayList<>());
    }
}