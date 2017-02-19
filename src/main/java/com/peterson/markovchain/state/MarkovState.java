package com.peterson.markovchain.state;

import java.util.List;
import java.util.Map;

/**
 * Some syntactic sugar around the Map that stores the state for the generator.
 * Created by Ryan on 2/19/2017.
 */
public abstract class MarkovState<T>
{
    private final Map<T, List<T>> state;
    private final NewListSupplier<T> listSupplier;
    private final NewMapSupplier<T> mapSupplier;

    public MarkovState(NewListSupplier<T> listSupplier, NewMapSupplier<T> mapSupplier)
    {
        this.listSupplier = listSupplier;
        this.mapSupplier = mapSupplier;
        this.state = mapSupplier.get();
    }

    public void put(T current, T next)
    {
        state.computeIfAbsent(current, (n) -> this.listSupplier.get()).add(next);
    }

    public List<T> getChain(T current)
    {
        return state.get(current);
    }
}
