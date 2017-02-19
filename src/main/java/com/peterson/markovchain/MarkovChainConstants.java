package com.peterson.markovchain;

import com.google.common.collect.ImmutableSet;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by Ryan on 2/19/2017.
 */
public class MarkovChainConstants
{
    public enum ChainDefaultValues
    {
        /**
         * Value that indicates there are no chains
         */
        NO_CHAIN("_null_"),

        /**
         * Value to indicate the start of the chain
         */
        CHAIN_START("$_MARKOV_CHAIN_START"),

        /**
         * Value to indicate the end of the chain
         */
        CHAIN_END("@_MARKOV_CHAIN_END");

        private final String value;

        ChainDefaultValues(String value)
        {
            this.value = value;
        }

        public String toString()
        {
            return this.value;
        }
    }

    /**
     * The default regex that splits a sentence on the whitespace characters
     */
    public static final Pattern DEFAULT_WORD_REGEX = Pattern.compile("\\s+");

    /**
     * The default punctuation that chains will end on unless other punctuation is used
     */
    public static String DEFAULT_PHRASE_END = ".";

    /**
     * The values that indicate the end of a sentence. If one of these isn't present, then
     * DEFAULT_PHRASE_END will be appended to the end of the sentence.
     */
    public static Set<Character> PUNCTUATION_SET = ImmutableSet.of('.', '?', '!', ';');
}
