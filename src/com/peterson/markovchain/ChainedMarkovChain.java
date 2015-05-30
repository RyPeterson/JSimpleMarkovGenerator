package com.peterson.markovchain;

import com.sun.net.httpserver.Filter;

import java.util.*;

/**
 * @author Peterson, Ryan
 *         Created: 5/30/15
 */
public class ChainedMarkovChain implements MarkovChain
{
    private Map<String, List<ChainNode>> markovChain;

    private Random rand;

    public ChainedMarkovChain()
    {
        markovChain = new HashMap<>();
        rand = new Random();
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

    private class ChainNode
    {
        public String word;
        public ChainNode next;
        public ChainNode previous;

        public ChainNode()
        {
            word = null;
            next = null;
            previous = null;
        }

        public ChainNode(String word)
        {
            this();
            this.word = word;
        }

        public ChainNode(String word, ChainNode nextNode, ChainNode previousNode)
        {
            this(word);
            next = nextNode;
            previous = previousNode;
        }

        @Override
        public int hashCode()
        {
            return word.hashCode();
        }

        @Override
        public boolean equals(Object other)
        {
            return (other instanceof Filter.Chain) && this.word.equals(((ChainNode)other).word);
        }
    }
}
