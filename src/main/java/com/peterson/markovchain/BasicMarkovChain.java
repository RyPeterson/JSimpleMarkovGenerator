package com.peterson.markovchain;

import java.util.*;

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
public class BasicMarkovChain implements MarkovChain
{

    //database for the chain
    protected Map<String, List<String>> markovChain;

    //set to store the beginning of a sentence so that a particular start word can be used
    private Set<String> suffixSet;

    //rng for forming new phrases
    private Random rand;

    /**
     * Construct an empty chain.
     * This just initializes an empty chain in order to add things to it.
     */
    public BasicMarkovChain()
    {
        markovChain = new HashMap<>();
        markovChain.put(CHAIN_START, newList());
        markovChain.put(CHAIN_END, newList());
        rand = new Random();
        suffixSet = new HashSet<>();
    }

    /**
     * Construct a chain with a starting phrase.
     * @param phrase the initial phrase to start with
     */
    public BasicMarkovChain(String phrase)
    {
        this();
        addPhrase(phrase);
    }

    /**
     * Generate a chain using a set of initial phrases.
     * @param phrases a list of phrases to initialize the chain with.
     */
    public BasicMarkovChain(String... phrases)
    {
        this();
        for(String s : phrases)
            addPhrase(s);
    }

    public void addPhrase(String phrase)
    {
        if(phrase == null)
            return;

        //check that its not just a new line or carrage return.
        if(MarkovChainUtilities.hasWhitespaceError(phrase))
            return;

        //ensure that the phrase has ending punctuation
        if(!PUNCTUATION.contains(MarkovChainUtilities.endChar(phrase)))
            phrase += DEFAULT_PHRASE_END;


        String []words = phrase.split(WORD_REGEX);

        for(int i = 0; i < words.length; i++)
        {
            if(i == 0)
            {
                List<String> starting = markovChain.get(CHAIN_START);
                starting.add(words[i]);
                suffixSet.add(words[i]);
                List<String> suffix = markovChain.get(words[i]);
                if(suffix == null)
                {
                    suffix = newList();
                    if(i + 1 < words.length)
                        suffix.add(words[i + 1]);
                    markovChain.put(words[i], suffix);
                }
            }
            else if(i == words.length - 1)
            {
                markovChain.get(CHAIN_END).add(words[i]);
            }
            else
            {
                List<String> suffix = markovChain.get(words[i]);
                if(suffix == null)
                {
                    suffix = newList();
                    suffix.add(words[i + 1]);
                    markovChain.put(words[i], suffix);
                }
                else
                {
                    suffix.add(words[i + 1]);
                }
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
            //throws an occasional StringIndexOutOfBounds exception
            try
            {
                while(!PUNCTUATION.contains(next.charAt(next.length() - 1)))
                {
                    next = generate(next);
                    sentence.append(next).append(" ");
                }
            }
            catch(StringIndexOutOfBoundsException sioobe)
            {
                //do nothing
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

        while(!PUNCTUATION.contains(next.charAt(next.length() - 1)))
        {
            next = generate(next);
            sentence.append(next).append(" ");
        }
        return sentence.toString();
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
            return word.get(rand.nextInt(word.size()));
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

    /**
     * Returns a more "loggable" view of the state.
     * @return a formatted string to represent the state.
     */
    public String toLoggableString()
    {
        StringBuilder b = new StringBuilder();
        for(String key : markovChain.keySet())
        {
            b.append(key).append("|");
            for(String s : markovChain.get(key))
                b.append(s).append(" ");
            b.append("\n");
        }
        return b.toString();
    }
    
    protected List<String> newList()
    {
        return new ArrayList<>();
    }
}
