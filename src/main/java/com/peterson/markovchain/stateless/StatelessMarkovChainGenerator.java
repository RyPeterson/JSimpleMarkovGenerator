package com.peterson.markovchain.stateless;

import com.peterson.markovchain.stateless.functions.StateTransitionFunction;
import com.peterson.markovchain.stateless.internal.Chain;
import com.peterson.markovchain.stateless.storage.MarkovChainStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by Ryan Peterson on 4/20/2016.
 */
public class StatelessMarkovChainGenerator<T> implements MarkovChainGenerator<T>
{
    private final MarkovChainStorage<T> storage;

    public StatelessMarkovChainGenerator(MarkovChainStorage<T> dataStore)
    {
        this.storage = dataStore;
    }

    @Override
    public Collection<T> generateChain(StateTransitionFunction<T, T> transitionFunction)
    {
        StatelessMarkovChain<T> chain = new BasicStatelessMarkovChain<>();

        Collection<T> phrase = new ArrayList<>();

        while(!chain.endOFChain())
        {
            Optional.ofNullable(chain.generate(transitionFunction)).ifPresent(phrase::add);
        }

        return phrase;
    }

    @Override
    public void learn(Supplier<T> knowledgeSupplier)
    {
        T current;
        Chain<T> chain = new Chain<>();
        do
        {
            current = knowledgeSupplier.get();
            if(current != null)
            {
                chain.addLink(current);
            }
        }
        while(current != null);

        storage.storeChain(chain);
    }
}
