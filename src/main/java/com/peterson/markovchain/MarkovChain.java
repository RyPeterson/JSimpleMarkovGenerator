package com.peterson.markovchain;

import com.peterson.markovchain.io.TrainingInterceptor;

/**
 * @author Peterson, Ryan
 *         Created: 5/30/15
 */
public interface MarkovChain
{
    /**
     * Add a single phrase to the database.
     * This will generate with previous chains with the chain resulting from the passed phrase.
     * The words of the phrase generate with the assumption that each word is separated by a space, and if the sentence
     * does not contain a period at the end, it will be appended with one.
     * @param phrase the sentence to add to the chain.
     */
    void addPhrase(String phrase);

    /**
     * Generate a random sentence using the chains in the database.
     * This is the default behaviour of a markov chain; it starts off with the
     * first element in the chain and forms based off of that.
     * Calls to this method remove no data from the chain database.
     * @return a single generated sentence.
     */
    String generateSentence();


    void acceptInterceptor(TrainingInterceptor interceptor);
}
