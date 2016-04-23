package com.peterson.markovchain;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Peterson, Ryan
 *         Created: 6/8/15
 */
public class SeedableMarkovChain extends AbstractSeedableMarkovChain
{
    //database for the chain
    protected Map<String, List<Link>> markovChain;

    public SeedableMarkovChain()
    {
        this(Pattern.compile(WORD_REGEX));
    }

    public SeedableMarkovChain(Pattern regexPattern)
    {
        markovChain = newSeedableMap();
        super.setSplitPattern(regexPattern);
    }


    @Override
    public void addPhrase(String phrase)
    {
        if(phrase == null)
            return;
        if(MarkovChainUtilities.hasWhitespaceError(phrase))
            return;

        if(!MarkovChainUtilities.PUNCTUATION_SET.contains(MarkovChainUtilities.endChar(phrase)))
            phrase += DEFAULT_PHRASE_END;

        String []words = super.splitPattern.split(phrase);

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
        Link wordLink = new Link(word);
        List<Link> start = markovChain.get(CHAIN_START);
        if(start == null)
        {
            start = newLinkList();
            markovChain.put(CHAIN_START, start);
        }
        start.add(wordLink);
        put(word, next, prev);
    }

    protected void putEnd(String word, Link prev)
    {
        Link end = new Link(word);
        end.previous = prev;
        List<Link> list = markovChain.get(CHAIN_END);
        if(list == null)
        {
            list = newLinkList();
            markovChain.put(CHAIN_END, list);
        }
        list.add(end);
    }

    protected void put(String word, String next, Link prev)
    {
        Link wordLink = new Link(next);
        wordLink.previous = prev;
        prev = wordLink;
        List<Link> list = markovChain.get(word);
        if(list == null)
        {
            list = newLinkList();
            markovChain.put(word, list);
        }
        list.add(wordLink);
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
            while(!MarkovChainUtilities.PUNCTUATION_SET.contains(MarkovChainUtilities.endChar(next.word)))
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
        List<Link> initialList = seed == null ? null :  markovChain.get(seed);
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

    @Override
    public MarkovChain copy()
    {
        final Pattern pcopy = Pattern.compile(super.splitPattern.pattern());
        SeedableMarkovChain copy = new SeedableMarkovChain(pcopy);
        Map<String, List<Link>> map = newSeedableMap();
        map.putAll(this.markovChain);
        copy.markovChain = map;
        copy.transformer = this.transformer;
        return copy;
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
        return word != null && word.size() != 0 ? word.get(randInt(word.size())) : null;
    }

    private Link generate(Link seed)
    {
        return generate(seed.word);
    }

    /**
     * Class the holds the state of the word.
     * This allows the traversal back one word.
     */
    public class Link
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
