package com.peterson.markovchain;

/**
 * @author Peterson, Ryan
 *         Created: 6/8/15
 */
public class TraversableMarkovChain implements MarkovChain
{
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
