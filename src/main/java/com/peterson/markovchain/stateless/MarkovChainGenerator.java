package com.peterson.markovchain.stateless;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Ryan Peterson on 4/20/2016.
 */
public interface MarkovChainGenerator<T>
{
    /**
     * Create a single chain.
     * @return a constructed chain.
     */
    Collection<T> generateChain(Function<T, T> transitionFunction);

    void learn(Supplier<T> knowledgeSupplier);
}
