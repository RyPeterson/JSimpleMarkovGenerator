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
        if(concurrent)
            markovChain = Collections.synchronizedMap(markovChain);
    }


    @Override
    public void addPhrase(String phrase)
    {
        if(MarkovChainUtilities.hasWhitespaceError(phrase))
            return;

        if(!PUNCTUATION.contains(MarkovChainUtilities.endChar(phrase)))
            phrase += DEFAULT_PHRASE_END;

        String []words = phrase.split(WORD_REGEX);

        Link nMinus1 = null;

        for(int i = 0; i < words.length; i++)
        {
            if(i == 0)
            {
                List<Link> starting = markovChain.get(CHAIN_START);
                Link word = new Link(words[i]);
                starting.add(word);

                List<Link> suffix = markovChain.get(words[i]);
                if(suffix == null)
                {
                    suffix = newList();
                    if(i + 1 < words.length)
                    {
                        Link next = new Link(words[i + 1]);
                        next.previous = word;
                        suffix.add(next);
                        nMinus1 = next;
                    }
                    markovChain.put(words[i], suffix);

                }
            }
            else if(i == words.length - 1)
            {
                Link end = new Link(words[i]);
                if(nMinus1 == null)
                    throw  new NullPointerException("The previous Link was not set");
                end.previous = nMinus1;
                markovChain.get(CHAIN_END).add(end);
            }
            else
            {
                List<Link> suffix = markovChain.get(words[i]);
                if(suffix == null)
                {
                    suffix = newList();
                    Link word = new Link(words[i + 1]);
                    word.previous = nMinus1;
                    nMinus1 = word;
                    suffix.add(word);
                    markovChain.put(words[i], suffix);
                }
                else
                {
                    Link word = new Link(words[i + 1]);
                    word.previous = nMinus1;
                    nMinus1 = word;
                    suffix.add(word);
                }
            }
        }
    }

    @Override
    public String generateSentence()
    {
        Link next = generate(CHAIN_START);
        if(next == null)
            return NO_CHAIN;

        return runGenerator(next);
    }

    private String runGenerator(Link next)
    {
        StringBuilder sentence = new StringBuilder();
        sentence.append(next).append(" ");

        if(next.word.length() - 1 > 0)
        {
            while(!PUNCTUATION.contains(MarkovChainUtilities.endChar(next.word)))
            {
                next = generate(next);
                sentence.append(next).append(" ");
            }
        }

        return sentence.toString();
    }

    @Override
    public String generateSentence(String seed)
    {
        //determine if the seed word is in a chain
        List<Link> initialList = markovChain.get(seed);
        if(initialList == null) //if its not, just generate a chain
            return generateSentence();

        //initialize the Link to search for
        Link toFind = new Link(seed);

        Link starting = findLink(initialList, toFind);

        //if, for some reason something failed, just return a chain
        if(starting == null)
            return generateSentence();

        Link current = starting;
        Link previous = starting.previous;

        //walk backward
        while(current.previous != null)
        {
            current = previous;
            previous = previous.previous;
        }
        //by now, its at the start of a chain using the seed word
        return runGenerator(current);
    }

    private Link findLink(List<Link> linkList, final Link toFind)
    {
        for(Link l : linkList)
        {
            if(l.sameWord(toFind))
                return l;
        }

        return null;
    }

    private Link generate(String seed)
    {
        List<Link> word = markovChain.get(seed);
        return word != null ? word.get(rand.nextInt(word.size())) : null;
    }

    private Link generate(Link seed)
    {
        return generate(seed.word);
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

        public boolean equals(Object other)
        {
            return (other instanceof Link && this.word.equals(((Link) other).word));
        }

        public boolean sameWord(Link other)
        {
            if(this.previous != null)
            {
                return this.previous.word.equalsIgnoreCase(other.word);
            }
            return false;
        }
    }

    public static void main(String []args)
    {
        MarkovChain mark = new TraversableMarkovChain();
        mark.addPhrase("She sells sea shells by the sea shore.");
        mark.addPhrase("To be or not to be, that is the question");
        mark.addPhrase("Hello, my name is Ryan!");
        for(int i = 0; i < 10; i++)
            System.out.println(mark.generateSentence("sells"));
    }
}
