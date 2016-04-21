package com.peterson.markovchain.stateless;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Ryan Peterson on 4/20/2016.
 */
public class StatelessMarkovChainGenerator<T> implements MarkovChainGenerator<T>
{
    @Override
    public Collection<T> generateChain(Function<T, T> transitionFunction)
    {
        StatelessMarkovChain<T> chain = new BasicStatelessMarkovChain<>();

        Collection<T> phrase = new ArrayList<>();

        while(!chain.endOFChain())
        {
            phrase.add(chain.generate(transitionFunction));
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
                chain.getChain().add(new Link<>(current));
            }
        }
        while(current != null);

    }
}
