package com.peterson.markovchain;

/**
 * @author Peterson, Ryan
 *         Created: 5/30/15
 */
public interface MarkovChain
{
    void addPhrase(String phrase);

    String generateSentence();

    String generatreSentence(String seed);

}
