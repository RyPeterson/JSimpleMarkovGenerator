package com.peterson.markovchain.stateless;

import java.util.function.Function;

/**
 * Created by Ryan Peterson on 4/20/2016.
 */
public class BasicStatelessMarkovChain<T> implements StatelessMarkovChain<T>
{

    private T currentState;
    private boolean isEnded;

    public BasicStatelessMarkovChain()
    {
        currentState = null;
    }

    @Override
    public T generate(Function<T, T> transitionFunction)
    {
        currentState = transitionFunction.apply(currentState);
        isEnded = currentState == null;
        return currentState;
    }

    @Override
    public boolean endOFChain()
    {
        return isEnded;
    }
}
