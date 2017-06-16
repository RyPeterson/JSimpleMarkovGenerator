package com.peterson.markovchain;

import com.peterson.markovchain.generation.MarkovGenerator;
import com.peterson.markovchain.random.BasicRandomNumberStrategy;
import com.peterson.markovchain.state.BasicMarkovState;

/**
 * Markov Chain Creation Class.
 * These are simple <a href="http://en.wikipedia.org/wiki/Markov_chain">Markov Chains</a> which are
 * formed via parsing strings as input and picking random phrases based on a start word.
 * This Markov generator is simple; it only parses strings based on spaces and ends all phrases with a period; but
 * fun to use, especially when fed a lot of input material.
 * Note: due to the nature of text and parsing, an occasional exception may be thrown.
 * @author Peterson, Ryan
 *         Created: 3/14/2015
 */
public class BasicMarkovChain<T> extends AbstractMarkovChain<T>
{
    /**
     * Construct an empty chain.
     * This just initializes an empty chain in order to add things to it.
     */
    public BasicMarkovChain(T startPlaceholder, T endPlaceholder)
    {
        super(new MarkovGenerator<>(new BasicRandomNumberStrategy()), new BasicMarkovState<>(), startPlaceholder, endPlaceholder);
    }
}
