package com.peterson.markovchain;

import com.peterson.markovchain.io.TrainingInterceptor;

import java.util.Collection;
import java.util.List;

/**
 * @author Peterson, Ryan
 *         Created: 5/30/15
 */
public interface MarkovChain<T>
{
    /**
     * Add a single phrase to the database.
     * This will generate with previous chains with the chain resulting from the passed phrase.
     * @param phrase the sentence to add to the chain.
     */
    void addPhrase(List<T> phrase);

    /**
     * Generate a random sentence using the chains in the database.
     * This is the default behaviour of a markov chain; it starts off with the
     * first element in the chain and forms based off of that.
     * Calls to this method remove no data from the chain database.
     * @return a single generated sentence.
     */
    Collection<T> generateSentence();


    void acceptInterceptor(TrainingInterceptor<T> interceptor);
}
