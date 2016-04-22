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

    /**
     * Given a supplier, create a new chain.
     * When the supplier is done giving this generator values,
     * it must return null.
     * @param knowledgeSupplier
     */
    void learn(Supplier<T> knowledgeSupplier);
}
