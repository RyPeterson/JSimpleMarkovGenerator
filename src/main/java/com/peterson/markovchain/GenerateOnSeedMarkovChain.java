package com.peterson.markovchain;

/**
 * Created by Ryan on 2/10/2017.
 */
public interface GenerateOnSeedMarkovChain<T> extends MarkovChain<T>
{
    /**
     * Generates a Markov phrase based off a seed word.
     * Similar to generateSentence(), this will generate a phrase using the supplied seed word as a
     * start state. If, for some reason, the word is not in the database, this will return NO_CHAIN, signaling that
     * a chain could not be formed
     * @param seed the seed word to start the chain with
     * @return a generated phrase using the seed word or NO_CHAIN if a chain could not be started
     */
    String generateSentence(String seed);
}
