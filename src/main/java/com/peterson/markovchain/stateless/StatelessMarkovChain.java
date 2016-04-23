package com.peterson.markovchain.stateless;

import com.peterson.markovchain.stateless.functions.StateTransitionFunction;

/**
 * Interface to a "stateless: machine.
 * Stateless in the fact that a markov chain machine needs to go based
 * on the current state it is in.
 * Created by Ryan Peterson on 4/20/2016.
 */
public interface StatelessMarkovChain<T>
{
    /**
     * From the current state, generate the next state
     * @param transitionFunction
     */
    T generate(StateTransitionFunction<T, T> transitionFunction);

    boolean endOFChain();
}
