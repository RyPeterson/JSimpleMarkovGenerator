package com.peterson.markovchain;

/**
 * Utility/Factory methods for Markov Chains.
 * @author Peterson, Ryan
 *         Created: 3/14/2015
 */
public class MarkovChainUtilities
{

    public static boolean hasWhitespaceError(String phrase)
    {
        return phrase.length() <= 1;
    }

    public static char endChar(String phrase)
    {
        return phrase.charAt(phrase.length() - 1);
    }
}
