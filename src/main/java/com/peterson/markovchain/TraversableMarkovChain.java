package com.peterson.markovchain;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Peterson, Ryan
 *         Created: 6/8/15
 */
public class TraversableMarkovChain implements MarkovChain
{
    //database for the chain
    protected Map<String, List<Link>> markovChain;

    private transient Random rand;

    private Pattern regexPattern;

    public TraversableMarkovChain()
    {
        this(Pattern.compile(WORD_REGEX));
    }

    public TraversableMarkovChain(Pattern regexPattern)
    {
        markovChain = new HashMap<>();
        markovChain.put(CHAIN_START, newList());
        markovChain.put(CHAIN_END, newList());
        rand = new Random();
        setRegexPattern(regexPattern);
    }

    public void setRegexPattern(Pattern pattern)
    {
        this.regexPattern = pattern;
    }



    @Override
    public void addPhrase(String phrase)
    {
        if(phrase == null)
            return;
        if(MarkovChainUtilities.hasWhitespaceError(phrase))
            return;

        if(!PUNCTUATION.contains(MarkovChainUtilities.endChar(phrase)))
            phrase += DEFAULT_PHRASE_END;

        String []words = regexPattern.split(phrase);

        Link nMinus1 = null;

        for(int i = 0; i < words.length; i++)
        {
            if(i == 0)
            {
                putHead(words[i], i + 1 < words.length ? words[i + 1] : null, nMinus1);
            }
            else if(i == words.length - 1)
            {
                putEnd(words[i], nMinus1);
            }
            else
            {
                put(words[i], words[i + 1], nMinus1);
            }
        }
    }

    protected void putHead(String word, String next, Link prev)
    {
        List<Link> starting = markovChain.get(CHAIN_START);
        Link wordLink = new Link(word);
        starting.add(wordLink);

        List<Link> suffix = markovChain.get(word);
        if(suffix == null)
        {
            suffix = newList();
            if(next != null)
            {
                Link nextLink = new Link(next);
                nextLink.previous = wordLink;
                suffix.add(nextLink);
                prev = nextLink;
            }
            markovChain.put(word, suffix);
        }
    }

    protected void putEnd(String word, Link prev)
    {
        Link end = new Link(word);
        end.previous = prev;
        markovChain.get(CHAIN_END).add(end);
    }

    protected void put(String word, String next, Link prev)
    {
        List<Link> suffix = markovChain.get(word);
        if(suffix == null)
        {
            suffix = newList();
            Link wordLink = new Link(next);
            wordLink.previous = prev;
            prev = wordLink;
            suffix.add(wordLink);
            markovChain.put(word, suffix);
        }
        else
        {
            Link wordLink = new Link(next);
            wordLink.previous = prev;
            prev = wordLink;
            suffix.add(wordLink);
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
        if(rand == null)
        {
            rand = new Random();
        }
        List<Link> word = markovChain.get(seed);
        return word != null && word.size() != 0 ? word.get(rand.nextInt(word.size())) : null;
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
    protected class Link
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
            return this.previous != null && this.previous.word.equalsIgnoreCase(other.word);
        }
    }
}
