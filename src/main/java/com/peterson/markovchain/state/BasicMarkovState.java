package com.peterson.markovchain.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MarkovState that stores the chains as a HashMap of T keys to an ArrayList<T> of values.
 * This adds no concurrency to the state and is not thread safe.
 * Created by Ryan on 2/19/2017.
 */
public class BasicMarkovState<T> extends MarkovState<T>
{

    public BasicMarkovState()
    {
        super(new BasicNewListSupplier<>(), new BasicNewMapSupplier<>());
    }

    protected static class BasicNewListSupplier<T> implements NewListSupplier<T>
    {
        @Override
        public List<T> get()
        {
            return new ArrayList<>();
        }
    }

    protected static class BasicNewMapSupplier<T> implements NewMapSupplier<T>
    {

        @Override
        public Map<T, List<T>> get()
        {
            return new HashMap<>();
        }
    }
}
