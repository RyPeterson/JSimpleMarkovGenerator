package com.peterson.markovchain;

import com.peterson.markovchain.io.TrainingInterceptor;
import com.peterson.markovchain.random.RandomNumberStrategy;

import java.util.Collection;
import java.util.List;

/**
 * Created by Ryan on 6/15/2017.
 */
public class StringMarkovChain implements MarkovChain<String>
{
    private final AbstractMarkovChain<String> internalChain;

    public StringMarkovChain()
    {
        this(true);
    }

    public StringMarkovChain(boolean concurrent)
    {
        internalChain = concurrent ? new ConcurrentBasicMarkovChain<>(MarkovChainConstants.ChainDefaultValues.CHAIN_START.toString(),
                MarkovChainConstants.ChainDefaultValues.CHAIN_END.toString()) : new BasicMarkovChain<>(MarkovChainConstants.ChainDefaultValues.CHAIN_START.toString(),
                MarkovChainConstants.ChainDefaultValues.CHAIN_END.toString());
    }

    @Override
    public void addPhrase(List<String> phrase)
    {
        this.internalChain.addPhrase(phrase);
    }

    @Override
    public Collection<String> generateSentence()
    {
        return this.internalChain.generateSentence();
    }

    @Override
    public void acceptInterceptor(TrainingInterceptor<String> interceptor)
    {
        this.internalChain.acceptInterceptor(interceptor);
    }

    void setRand(RandomNumberStrategy numberStrategy)
    {
        this.internalChain.setRand(numberStrategy);
    }
}
