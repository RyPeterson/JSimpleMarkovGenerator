package com.peterson.markovchain;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * @author Peterson, Ryan
 *         Created 12/25/2015
 */
public abstract class AbstractMarkovChain implements MarkovChain
{
    protected transient Random rand;

    protected Pattern splitPattern;

    protected WordTransformer transformer;

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

    protected void setWordTransformer(WordTransformer transformer)
    {
        this.transformer = transformer;
    }

    protected int randInt(int upper)
    {
        if(rand == null)
        {
            rand = new Random();
        }

        return rand.nextInt(upper);
    }

    public class Builder
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
         * @param concurrent true to have a more thread safe generator
         * @param traversable true to have a generator that can build based on a seed
         */
        public Builder(boolean concurrent, boolean traversable)
        {
            if(concurrent)
            {
                if(traversable)
                {
                    instance = new ConcurrentTraversableMarkovChain();
                }
                else
                {
                    instance = new ConcurrentBasicMarkovChain();
                }
            }
            else
            {
                if(traversable)
                {
                    instance = new TraversableMarkovChain();
                }
                else
                {
                    instance = new BasicMarkovChain();
                }
            }
        }

        public void setPattern(Pattern regexPattern)
        {
            instance.setSplitPattern(regexPattern);
        }

        public void setTransformer(WordTransformer transformer)
        {
            instance.setWordTransformer(transformer);
        }

        public void setRandom(Random rand)
        {
            instance.setRand(rand);
        }

        public MarkovChain build()
        {
            return instance;
        }
    }
}
