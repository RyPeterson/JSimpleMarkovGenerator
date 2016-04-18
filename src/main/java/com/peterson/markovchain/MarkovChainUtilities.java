package com.peterson.markovchain;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * Utility/Factory methods for Markov Chains.
 * @author Peterson, Ryan
 *         Created: 3/14/2015
 */
public class MarkovChainUtilities
{
    public static Set<Character> PUNCTUATION_SET = ImmutableSet.of('.', '?', '!', ';');

    public static boolean hasWhitespaceError(String phrase)
    {
        return phrase.length() <= 1;
    }

    public static char endChar(String phrase)
    {
        return phrase.charAt(phrase.length() - 1);
    }
}
