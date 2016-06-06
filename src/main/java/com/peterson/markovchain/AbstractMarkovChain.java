package com.peterson.markovchain;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Peterson, Ryan
 *         Created 12/25/2015
 */
public abstract class AbstractMarkovChain implements MarkovChain
{
    protected Random rand;

    protected Pattern splitPattern;

    public AbstractMarkovChain()
    {
        setRand(new Random());
    }

    protected void setRand(Random rand)
    {
        this.rand = rand;
    }

    protected void setSplitPattern(Pattern pattern)
    {
        this.splitPattern = pattern;
    }


    protected int randInt(int upper)
    {
        return rand.nextInt(upper);
    }

    public static class Builder
    {
        private AbstractMarkovChain instance;

        /**
         * Create a basic, non-threadsafe MarkovChain
         */
        public Builder()
        {
            this(false, false);
        }


        /**
         * Set up the builder.
         *
         * @param concurrent  true to have a more thread safe generator
         * @param traversable true to have a generator that can build based on a seed
         */
        public Builder(boolean concurrent, boolean traversable)
        {
            if (concurrent)
            {
                if (traversable)
                {
                    instance = new ConcurrentSeedableMarkovChain();
                }
                else
                {
                    instance = new ConcurrentBasicMarkovChain();
                }
            }
            else
            {
                if (traversable)
                {
                    instance = new SeedableMarkovChain();
                }
                else
                {
                    instance = new BasicMarkovChain();
                }
            }
        }

        public Builder setPattern(Pattern regexPattern)
        {
            instance.setSplitPattern(regexPattern);
            return this;
        }

        public Builder setRandom(Random rand)
        {
            instance.setRand(rand);
            return this;
        }

        public MarkovChain build()
        {
            return instance;
        }
    }

    protected Map<String, List<String>> newMap()
    {
        return new HashMap<>();
    }

    protected List<String> newList()
    {
        return new ArrayList<>();
    }

    protected void put(String key, String current, Map<String, List<String>> chains)
    {
        List<String> list = chains.get(key);
        if(list == null)
        {
            list = newList();
            chains.put(key, list);
        }
        list.add(current);
    }
}
