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
        markovChain.put(CHAIN_START, new ArrayList<>());
        markovChain.put(CHAIN_END, new ArrayList<>());
        rand = new Random();
    }


    @Override
    public void addPhrase(String phrase)
    {
        //check that its not just a new line or carrage return.
        if(MarkovChainUtilities.hasWhitespaceError(phrase))
            return;

        //ensure that the phrase has ending punctuation
        if(!PUNCTUATION.contains(MarkovChainUtilities.endChar(phrase)))
            phrase += DEFAULT_PHRASE_END;


        String []words = phrase.split(WORD_REGEX);
        ChainNode []nodes = new ChainNode[words.length];

        for(int i = 0; i < words.length; i++)
        {
            if(i == 0)
            {
                List<ChainNode> start = markovChain.get(CHAIN_START);
                ChainNode node = new ChainNode(words[i]);
                nodes[i] = node;
                start.add(node);
                List<ChainNode> suffix = markovChain.get(words[i]);
                if(suffix == null)
                {
                    suffix = new ArrayList<>();
                    if(i + 1 < words.length)
                    {
                        ChainNode n = new ChainNode(words[i + 1]);
                        nodes[i + 1] = n;
                        n.previous = node;
                        node.next = n;
                        suffix.add(n);
                    }
                    markovChain.put(words[i], suffix);
                }
            }
            else if(i == words.length - 1)
            {
                List<ChainNode> end = markovChain.get(CHAIN_END);
                ChainNode node = new ChainNode(words[i]);
                nodes[i] = node;
                node.previous = nodes[i - 1];
                end.add(node);
            }
            else
            {
                List<ChainNode> suffix = markovChain.get(words[i]);
                if(suffix == null)
                {
                    suffix = new ArrayList<>();
                    ChainNode node = new ChainNode(words[i + 1]);
                    node.previous = nodes[i - 1];
                    nodes[i] = node;
                    node.next = nodes[i + 1];
                    suffix.add(node);
                }
                else
                {
                    ChainNode node = new ChainNode(words[i + 1]);
                    node.previous = nodes[i - 1];
                    node.next = nodes[i + 1];
                    nodes[i] = node;
                    suffix.add(node);
                }
            }
        }
    }

    private ChainNode generate(String seed)
    {
        List<ChainNode> nodes = markovChain.get(seed);
        return nodes != null && nodes.size() > 0 ? nodes.get(rand.nextInt(nodes.size())) : null;
    }

    @Override
    public String generateSentence()
    {
        StringBuilder sentence = new StringBuilder();
        ChainNode next = generate(CHAIN_START);
        if(next == null)
            return NO_CHAIN;
        String word = next.toString();
        sentence.append(word).append(" ");


        if(word.length() - 1 > 0)
        {
            while(!PUNCTUATION.contains(word.charAt(word.length() - 1)))
            {
                next = generate(word);
                word = next.toString();
                sentence.append(word).append(" ");
            }
        }

        return sentence.toString();
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

        public String toString()
        {
            return word;
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

    public static void main(String []args)
    {
        MarkovChain markovChain = new ChainedMarkovChain();
        markovChain.addPhrase("She sells seashells by the seashore");
        markovChain.addPhrase("To be or not to be, that is the question");
        markovChain.addPhrase("Hello, I am Ryan");
        for(int i = 0; i < 10; i++)
        {
            System.out.println(markovChain.generateSentence());
        }
    }
}
