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
public class MarkovChain
{
    /**
     * Signal that a chain based on an seed could not be generated because its not in the object's database
     */
    public static final String NO_CHAIN = "-null";

    /* Signal the start of the chain */
    private static final String CHAIN_START = "$";

    /* Signal the end of the chain */
    private static final String CHAIN_END = "@";

    /* Regex to separate words by */
    private static final String WORD_REGEX = " ";

    /* Punctuation to end the phrase on */
    private static final String DEFAULT_PHRASE_END = ".";

    /* a reference to a set that contains punctuation symbols to allow more than '.' to be used to end a phrase*/
    private static final Set<Character> PUNCTUATION = generatePunctuationSet();

    //database for the chain
    private Map<String, List<String>> markovChain;

    //rng for forming new phrases
    private Random rand;

    /**
     * Construct an empty chain.
     * This just initializes an empty chain in order to add things to it.
     */
    public MarkovChain()
    {
        markovChain = new HashMap<>();
        markovChain.put(CHAIN_START, new ArrayList<>());
        markovChain.put(CHAIN_END, new ArrayList<>());
        rand = new Random();
    }

    /**
     * Construct a chain with a starting phrase.
     * @param phrase the initial phrase to start with
     */
    public MarkovChain(String phrase)
    {
        this();
        addPhrase(phrase);
    }

    /**
     * Generate a chain using a set of initial phrases.
     * @param phrases a list of phrases to initialize the chain with.
     */
    public MarkovChain(String ...phrases)
    {
        this();
        for(String s : phrases)
            addPhrase(s);
    }

    /**
     * Add a single phrase to the database.
     * This will generate with previous chains with the chain resulting from the passed phrase.
     * The words of the phrase generate with the assumption that each word is separated by a space, and if the sentence
     * does not contain a period at the end, it will be appended with one.
     * @param phrase the sentence to add to the chain.
     */
    public void addPhrase(String phrase)
    {
        //check that its not just a new line or carrage return.
        if(hasWhitespaceError(phrase))
            return;
        //ensure that the phrase has ending punctuation
        if(!phrase.endsWith(DEFAULT_PHRASE_END))
        {
            phrase += DEFAULT_PHRASE_END;
        }

        String []words = phrase.split(WORD_REGEX);

        for(int i = 0; i < words.length; i++)
        {
            if(i == 0)
            {
                List<String> starting = markovChain.get(CHAIN_START);
                starting.add(words[i]);
                List<String> suffix = markovChain.get(words[i]);
                if(suffix == null)
                {
                    suffix = new ArrayList<>();
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
                    suffix = new ArrayList<>();
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

    private boolean hasWhitespaceError(String phrase)
    {
        return phrase.length() <= 1;
    }

    /**
     * Generate a random sentence using the chains in the database.
     * This is the default behaviour of a markov chain; it starts off with the
     * first element in the chain and forms based off of that.
     * Calls to this method remove no data from the chain database.
     * @return a single generated sentence.
     */
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
                while (next.charAt(next.length() - 1) != DEFAULT_PHRASE_END.charAt(0))
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

    /**
     * Generates a Markov phrase based off a seed word.
     * Similar to generateSentence(), this will generate a phrase using the supplied seed word as a
     * start state. If, for some reason, the word is not in the database, this will return NO_CHAIN, signaling that
     * a chain could not be formed
     * @param seed the seed word to start the chain with
     * @return a generated phrase using the seed word or NO_CHAIN if a chain could not be started
     */
    public String generateSentence(String seed)
    {
        StringBuilder sentence = new StringBuilder(seed).append(" ");
        String next;

        next = generate(seed);
        if(next == null)
            return NO_CHAIN;

        sentence.append(next).append(" ");

        while(next.charAt(next.length() - 1) != DEFAULT_PHRASE_END.charAt(0))
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

    private static Set<Character> generatePunctuationSet()
    {
        Set<Character> punctSet = new HashSet<>(4);
        punctSet.add('.');
        punctSet.add('?');
        punctSet.add('!');
        punctSet.add(';');

        return punctSet;
    }
}
