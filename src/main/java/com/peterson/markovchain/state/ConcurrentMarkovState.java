package com.peterson.markovchain.state;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Concurrent Markov State that stores the chains in a ConcurrentHashMap and maps to CopyOnWriteArray lists.
 * Created by Ryan on 2/19/2017.
 */
public class ConcurrentMarkovState<T> extends MarkovState<T>
{
    public ConcurrentMarkovState()
    {
        super(new ConcurrentNewListSupplier<>(), new ConcurrentNewMapSupplier<>());
    }

    protected static class ConcurrentNewListSupplier<T> implements NewListSupplier<T>
    {
        @Override
        public List<T> get()
        {
            return new CopyOnWriteArrayList<>();
        }
    }

    protected static class ConcurrentNewMapSupplier<T> implements NewMapSupplier<T>
    {
        @Override
        public Map<T, List<T>> get()
        {
            return new ConcurrentHashMap<>();
        }
    }
}
