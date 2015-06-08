package com.peterson.markovchain;

import java.util.*;

/**
 * @author Peterson, Ryan
 *         Created: 6/8/15
 */
public class TraversableMarkovChain implements MarkovChain
{
    //database for the chain
    private Map<String, List<Link>> markovChain;

    private Random rand;

    public TraversableMarkovChain()
    {
        markovChain = new HashMap<>();
        markovChain.put(CHAIN_START, newList());
        markovChain.put(CHAIN_END, newList());
        rand = new Random();
    }

    public TraversableMarkovChain(String ...phrases)
    {
        this();
        for(String s : phrases)
            addPhrase(s);
    }

    protected TraversableMarkovChain(boolean concurrent)
    {
        this();
        markovChain = Collections.synchronizedMap(markovChain);
    }


    @Override
    public void addPhrase(String phrase)
    {

    }

    @Override
    public String generateSentence()
    {
        return null;
    }

    @Override
    public String generateSentence(String seed)
    {
        return null;
    }

    protected List<Link> newList()
    {
        return new ArrayList<>();
    }

    /**
     * Class the holds the state of the word.
     * This allows the traversal back one word.
     */
    private class Link
    {
        //link == null means its the head
        Link previous;
        String word;

        public Link()
        {
            previous = null;
            word = null;
        }

        public Link(String theWord)
        {
            word = theWord;
        }

        public Link(String theWord, Link previousWord)
        {
            this(theWord);
            previous = previousWord;
        }

        public String toString()
        {
            return word;
        }
    }
}
