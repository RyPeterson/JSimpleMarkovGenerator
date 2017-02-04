package com.peterson.markovchain;

import com.peterson.markovchain.io.PostDeserializationStrategy;
import com.peterson.markovchain.io.TrainingInterceptor;
import com.peterson.markovchain.stateless.random.BasicRandomNumberStrategy;
import com.peterson.markovchain.stateless.random.RandomNumberStrategy;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Peterson, Ryan
 *         Created 12/25/2015
 */
public abstract class AbstractMarkovChain implements MarkovChain
{
    protected RandomNumberStrategy rand;

    protected Pattern splitPattern;

    protected List<TrainingInterceptor> trainingInterceptors;

    public AbstractMarkovChain()
    {
        setRand(new BasicRandomNumberStrategy());
        this.trainingInterceptors = new ArrayList<>();
    }

    protected void setRand(RandomNumberStrategy rand)
    {
        this.rand = rand;
    }

    protected void setSplitPattern(Pattern pattern)
    {
        this.splitPattern = pattern;
    }

    @Override
    public void acceptInterceptor(TrainingInterceptor interceptor)
    {
        this.trainingInterceptors.add(interceptor);
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

        public Builder setRandom(RandomNumberStrategy rand)
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
        this.trainingInterceptors.forEach((interceptor -> interceptor.intercept(key, current)));
        List<String> list = chains.computeIfAbsent(key, (s) -> newList());
        list.add(current);
    }

    public static class AbstractMarkovChainSerializationStrategy implements PostDeserializationStrategy
    {
        private final RandomNumberStrategy randomNumberStrategy;
        private final Pattern splitPattern;

        public AbstractMarkovChainSerializationStrategy(RandomNumberStrategy randomNumberStrategy)
        {
            this(randomNumberStrategy, Pattern.compile(MarkovChain.WORD_REGEX));
        }

        public AbstractMarkovChainSerializationStrategy(RandomNumberStrategy randomNumberStrategy, Pattern splitPattern)
        {
            this.randomNumberStrategy = randomNumberStrategy;
            this.splitPattern = splitPattern;
        }

        @Override
        public void postDeserializationInitialization(MarkovChain markovChain)
        {
            if(markovChain instanceof AbstractMarkovChain)
            {
                AbstractMarkovChain abstractMarkovChain = (AbstractMarkovChain) markovChain;
                abstractMarkovChain.trainingInterceptors = new ArrayList<>();
                abstractMarkovChain.setRand(randomNumberStrategy);
                abstractMarkovChain.setSplitPattern(splitPattern);
            }
        }
    }
}
