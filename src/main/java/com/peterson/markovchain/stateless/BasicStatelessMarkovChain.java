package com.peterson.markovchain.stateless;

import com.peterson.markovchain.stateless.functions.StateTransitionFunction;

/**
 * Created by Ryan Peterson on 4/20/2016.
 */
class BasicStatelessMarkovChain<T> implements StatelessMarkovChain<T>
{

    private T currentState;
    private boolean isEnded;

    BasicStatelessMarkovChain()
    {
        currentState = null;
    }

    @Override
    public T generate(StateTransitionFunction<T, T> transitionFunction)
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
