package com.peterson.markovchain.generation;

import com.peterson.markovchain.random.RandomNumberStrategy;
import com.peterson.markovchain.state.MarkovState;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * Implementation of a generator that is created as a BiFunction that takes in the current value
 * and the MarkovState storage and returns an Optional of the next value of the chain.
 * Created by Ryan on 2/19/2017.
 */
public class MarkovGenerator<T> implements BiFunction<T, MarkovState<T>, Optional<T>>
{
    private RandomNumberStrategy randomNumberStrategy;

    public MarkovGenerator(RandomNumberStrategy randomNumberStrategy)
    {
        this.randomNumberStrategy = randomNumberStrategy;
    }

    public void setRandomNumberStrategy(RandomNumberStrategy randomNumberStrategy)
    {
        this.randomNumberStrategy = randomNumberStrategy;
    }

    @Override
    public Optional<T> apply(T current, MarkovState<T> state)
    {
        if(state != null)
        {
            List<T> currentChain = state.getChain(current);
            if (currentChain != null && !currentChain.isEmpty())
            {
                return Optional.ofNullable(currentChain.get(randomNumberStrategy.nextInt(currentChain.size())));
            }
        }
        return Optional.empty();
    }
}
