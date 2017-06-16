package com.peterson.markovchain;

import com.peterson.markovchain.generation.MarkovGenerator;
import com.peterson.markovchain.io.TrainingInterceptor;
import com.peterson.markovchain.random.RandomNumberStrategy;
import com.peterson.markovchain.state.MarkovState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Peterson, Ryan
 *         Created 12/25/2015
 */
public abstract class AbstractMarkovChain<T> implements MarkovChain<T>
{
    protected final MarkovGenerator<T> generator;
    protected MarkovState<T> state;

    protected List<TrainingInterceptor<T>> trainingInterceptors;

    private final T endPlaceholder;
    private final T startPlaceholder;


    /**
     * Construct the generator
     * @param generator the MarkovGenerator that is to generate the chains
     * @param state the chain "database"
     * @param startPlaceholder a reserved placeholder for the first item in a chain
     * @param endPlaceholder a reserved placeholder for the last item in a chain.
     */
    protected AbstractMarkovChain(MarkovGenerator<T> generator, MarkovState<T> state, T startPlaceholder, T endPlaceholder)
    {
        this.generator = generator;
        this.state = state;
        this.startPlaceholder = startPlaceholder;
        this.endPlaceholder = endPlaceholder;
        this.trainingInterceptors = new ArrayList<>();
    }

    void setRand(RandomNumberStrategy random)
    {
        this.generator.setRandomNumberStrategy(random);
    }

    @Override
    public void acceptInterceptor(TrainingInterceptor<T> interceptor)
    {
        this.trainingInterceptors.add(interceptor);
    }

    protected void put(T key, T current)
    {
        this.trainingInterceptors.forEach((interceptor -> interceptor.intercept(key, current)));
        this.state.put(key, current);
    }

    /**
     * Private helper method to get the next element in the chain.
     * If the seed cannot be found in the set, null is returned and should (and is) handled elsewhere
     * @param seed the word to seed the next with
     * @return a randomly selected word from the chain or null if the chain is empty
     */
    protected T generate(T seed)
    {
        return this.generator.apply(seed, this.state).orElse(null);
    }


    @Override
    public void addPhrase(List<T> phrase)
    {
        if(phrase == null)
        {
            return;
        }

        for(int i = 0; i < phrase.size(); i++)
        {
            if(i == 0)
            {
                T next = i + 1 <  phrase.size() ?  phrase.get(i + 1) : null;
                put(startPlaceholder, phrase.get(i));
                put(phrase.get(i), next);
            }
            else if(i == phrase.size() - 1)
            {
                put(endPlaceholder, phrase.get(i));
            }
            else
            {
                put(phrase.get(i), phrase.get(i + 1));
            }
        }
    }

    @Override
    public Collection<T> generateSentence()
    {
        Collection<T> sentence = new ArrayList<>();
        T next;


        next = generate(startPlaceholder);
        if(next == null)
        {
            return Collections.emptyList();
        }
        sentence.add(next);

        while((next = generate(next)) != null)
        {
            sentence.add(next);
        }
        return sentence;
    }
}
