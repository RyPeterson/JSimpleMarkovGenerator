package com.peterson.markovchain;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A basic synchronized version of the SeedableMarkovChain.
 * This class forms the base of synchronized access by using a
 * Synchronized map and lists.
 * @author Peterson, Ryan
 *         Created: 6/8/15
 */
public class ConcurrentSeedableMarkovChain extends SeedableMarkovChain
{
    private ReadWriteLock readWriteLock;

    /**
     * Constructs the SynchronizedTraversableMarkovChain.
     */
    public ConcurrentSeedableMarkovChain()
    {
        super();
        readWriteLock = new ReentrantReadWriteLock();
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

    @Override
    public String generateSentence()
    {
        readWriteLock.readLock().lock();
        try
        {
            return super.generateSentence();
        }
        finally
        {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public String generateSentence(String seed)
    {
        readWriteLock.readLock().lock();
        try
        {
            return super.generateSentence(seed);
        }
        finally
        {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public void addPhrase(String phrase)
    {
        readWriteLock.writeLock().lock();
        try
        {
            super.addPhrase(phrase);
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }
    }
}
