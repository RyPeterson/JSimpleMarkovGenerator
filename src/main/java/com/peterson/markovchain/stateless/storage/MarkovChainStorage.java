package com.peterson.markovchain.stateless.storage;

import com.peterson.markovchain.stateless.Chain;

import java.util.List;

/**
 * Interface to some object that can store the linked list that
 * represents a new chain.
 * Created by Ryan Peterson on 4/21/2016.
 */
public interface MarkovChainStorage<T>
{
    void storeChain(Chain<T> chain);

    List<T> getNext(T current);
}
