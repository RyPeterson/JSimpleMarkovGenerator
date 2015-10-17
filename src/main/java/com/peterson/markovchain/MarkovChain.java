package com.peterson.markovchain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Peterson, Ryan
 *         Created: 5/30/15
 */
public interface MarkovChain
{
    String NO_CHAIN = "-null";

    /* Signal the start of the chain */
    String CHAIN_START = "$";

    /* Signal the end of the chain */
    String CHAIN_END = "@";

    /* Regex to separate words by */
    String WORD_REGEX = " ";

    /* Punctuation to end the phrase on */
    String DEFAULT_PHRASE_END = ".";

    /* a reference to a set that contains punctuation symbols to allow more than '.' to be used to end a phrase*/
    Set<Character> PUNCTUATION = MarkovChainUtilities.generatePunctuationSet();

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