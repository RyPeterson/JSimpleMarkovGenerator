package com.peterson.markovchain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility/Factory methods for Markov Chains.
 * @author Peterson, Ryan
 *         Created: 3/14/2015
 */
public class MarkovChainUtilities
{
    static Set<Character> generatePunctuationSet()
    {
        Set<Character> punctSet = new HashSet<>(4);
        punctSet.add('.');
        punctSet.add('?');
        punctSet.add('!');
        punctSet.add(';');

        return Collections.unmodifiableSet(punctSet);
    }

    public static boolean hasWhitespaceError(String phrase)
    {
        return phrase.length() <= 1;
    }

    public static char endChar(String phrase)
    {
        return phrase.charAt(phrase.length() - 1);
    }
}
