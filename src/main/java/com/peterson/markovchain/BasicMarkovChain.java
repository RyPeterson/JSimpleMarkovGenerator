package com.peterson.markovchain;

import com.peterson.markovchain.stateless.random.BasicRandomNumberStrategy;

import java.util.*;
import java.util.regex.Pattern;

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
public class BasicMarkovChain extends AbstractMarkovChain
{

    //database for the chain
    protected Map<String, List<String>> markovChain;

    //set to store the beginning of a sentence so that a particular start word can be used
    protected Set<String> suffixSet;

    /**
     * Construct an empty chain.
     * This just initializes an empty chain in order to add things to it.
     */
    public BasicMarkovChain()
    {
        this(Pattern.compile(WORD_REGEX));
    }

    public BasicMarkovChain(Pattern regexPattern)
    {
        markovChain = newMap();
        rand = new BasicRandomNumberStrategy();
        suffixSet = new HashSet<>();
        super.setSplitPattern(regexPattern);
    }

    public void addPhrase(String phrase)
    {
        if(phrase == null)
            return;

        //check that its not just a new line or carrage return.
        if(MarkovChainUtilities.hasWhitespaceError(phrase))
            return;

        //ensure that the phrase has ending punctuation
        if(!MarkovChainUtilities.PUNCTUATION_SET.contains(MarkovChainUtilities.endChar(phrase)))
            phrase += DEFAULT_PHRASE_END;


        String []words = super.splitPattern.split(phrase);

        for(int i = 0; i < words.length; i++)
        {
            if(i == 0)
            {
                String next = i + 1 < words.length ? words[i + 1] : null;
                put(CHAIN_START, words[i], markovChain);
                suffixSet.add(words[i]);
                put(words[i], next, markovChain);
            }
            else if(i == words.length - 1)
            {
                put(CHAIN_END, words[i], markovChain);
            }
            else
            {
                put(words[i], words[i + 1], markovChain);
            }
        }
    }




    public String generateSentence()
    {
        StringBuilder sentence = new StringBuilder();
        String next;


        next = generate(CHAIN_START);
        if(next == null)
            return NO_CHAIN;
        sentence.append(next).append(" ");

        if(next.length() - 1 > 0)
        {
            while(!MarkovChainUtilities.PUNCTUATION_SET.contains(next.charAt(next.length() - 1)))
            {
                next = generate(next);
                sentence.append(next).append(" ");
            }
        }
        return sentence.toString();
    }


    public String generateSentence(String seed)
    {
        //if the suffix set does not contain the see, then what is the point of starting there?
        if(!suffixSet.contains(seed))
            return generateSentence();

        StringBuilder sentence = new StringBuilder(seed).append(" ");
        String next;


        next = generate(seed);
        if(next == null)
            return NO_CHAIN;

        sentence.append(next).append(" ");

        while(!MarkovChainUtilities.PUNCTUATION_SET.contains(next.charAt(next.length() - 1)))
        {
            next = generate(next);
            sentence.append(next).append(" ");
        }
        return sentence.toString();
    }

    @Override
    public MarkovChain copy()
    {
        final Pattern pcopy = Pattern.compile(super.splitPattern.pattern());
        BasicMarkovChain copy = new BasicMarkovChain(pcopy);
        copy.suffixSet = new HashSet<>(this.suffixSet);
        Map<String, List<String>> chains = newMap();
        chains.putAll(this.markovChain);
        copy.markovChain = chains;

        return copy;
    }

    /**
     * Private helper method to get the next element in the chain.
     * If the seed cannot be found in the set, null is returned and should (and is) handled elsewhere
     * @param seed the word to seed the next with
     * @return a randomly selected word from the chain or null if the chain is empty
     */
    private String generate(String seed)
    {
        List<String> word = markovChain.get(seed);
        if(word != null && word.size() > 0)
            return word.get(randInt(word.size()));
        else
            return null;
    }

    /**
     * Present a human readable string representing the chains in the database.
     * This is presented as "KEY [key_value]->{value list}
     * Due to the nature of how things are represented, this is essentially a wall of text.
     * @return a string representing the state of the object
     */
    public String toString()
    {
        StringBuilder b = new StringBuilder();
        for(String k : markovChain.keySet())
        {
            b.append("KEY ").append(k).append("-> ");
            for(String s : markovChain.get(k))
                b.append(s).append(" ");
            b.append("\n");
        }

        return b.toString();
    }
}
