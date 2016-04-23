package com.peterson.markovchain.stateless.functions;

import com.peterson.markovchain.stateless.random.RandomNumberStrategy;
import com.peterson.markovchain.stateless.storage.MarkovChainStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * A function that transitions the markov chain to its next state
 * based on the probability from its current state to get there.
 * Created by Ryan Peterson on 4/22/2016.
 */
public class MarkovStateTransitionFunction<T> implements StateTransitionFunction<T, T>
{
    private final RandomNumberStrategy random;

    //TODO: wtf is this state doing here
    private final MarkovChainStorage<T> storage;

    private final Function<T, T> headFunction;
    private final Function<T, T> currentFunction;

    public MarkovStateTransitionFunction(RandomNumberStrategy randomNumberStrategy, MarkovChainStorage<T> storage)
    {
        this.random = randomNumberStrategy;
        this.storage = storage;

        headFunction = new HeadFunction();
        currentFunction = new CurrentFunction();
    }

    @Override
    public T apply(T current)
    {
        return current == null ? headFunction.apply(current) : currentFunction.apply(current);
    }

    private class HeadFunction implements Function<T, T>
    {

        @Override
        public T apply(T t)
        {
            final List<T> heads = storage.getHeads();
            return heads.get(random.nextInt(heads.size()));
        }
    }

    private class CurrentFunction implements Function<T, T>
    {

        @Override
        public T apply(T current)
        {
            List<T> list = storage.getNext(current);

            final List<T> result = new ArrayList<>();
            Optional.ofNullable(current).ifPresent((c) ->
            {
                List<T> res = storage.getNext(c);
                if(res != null && !res.isEmpty())
                {
                    result.add(res.get(random.nextInt(res.size())));
                }
            });
            return result.isEmpty() ? null : result.get(0);
        }
    }
}
