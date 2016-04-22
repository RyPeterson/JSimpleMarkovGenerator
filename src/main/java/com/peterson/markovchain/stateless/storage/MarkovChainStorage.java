package com.peterson.markovchain.stateless.storage;

import com.peterson.markovchain.stateless.internal.Chain;

import java.util.List;

/**
 * Interface to some object that can store the linked list that
 * represents a new chain.
 * Created by Ryan Peterson on 4/21/2016.
 */
public interface MarkovChainStorage<T>
{
    void storeChain(Chain<T> chain);

    /**
     * Given the current value, get what follows.
     * @param current
     * @return
     */
    List<T> getNext(T current);

    /**
     * Get a list of possible starting points of the chain.
     * @return
     */
    List<T> getHeads();
}
