package com.peterson.markovchain;

import com.peterson.markovchain.generation.MarkovGenerator;
import com.peterson.markovchain.io.TrainingInterceptor;
import com.peterson.markovchain.random.RandomNumberStrategy;
import com.peterson.markovchain.state.MarkovState;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Peterson, Ryan
 *         Created 12/25/2015
 */
public abstract class AbstractMarkovChain implements MarkovChain
{
    protected final MarkovGenerator<String> generator;
    protected final MarkovState<String> state;

    protected Pattern splitPattern;

    protected List<TrainingInterceptor> trainingInterceptors;

    protected AbstractMarkovChain(MarkovGenerator<String> generator, MarkovState<String> state)
    {
        this.generator = generator;
        this.state = state;
        this.trainingInterceptors = new ArrayList<>();
        setSplitPattern(Pattern.compile(WORD_REGEX));
    }

    protected void setSplitPattern(Pattern pattern)
    {
        this.splitPattern = pattern;
    }

    void setRand(RandomNumberStrategy random)
    {
        this.generator.setRandomNumberStrategy(random);
    }

    @Override
    public void acceptInterceptor(TrainingInterceptor interceptor)
    {
        this.trainingInterceptors.add(interceptor);
    }

    protected void put(String key, String current)
    {
        this.trainingInterceptors.forEach((interceptor -> interceptor.intercept(key, current)));
        this.state.put(key, current);
    }

    /**
     * Private helper method to get the next element in the chain.
     * If the seed cannot be found in the set, null is returned and should (and is) handled elsewhere
     * @param seed the word to seed the next with
     * @return a randomly selected word from the chain or null if the chain is empty
     */
    protected String generate(String seed)
    {
        return this.generator.apply(seed, this.state).orElse(null);
    }


    public void addPhrase(String phrase)
    {
        if(phrase == null)
        {
            return;
        }

        //check that its not just a new line or carrage return.
        if(MarkovChainUtilities.hasWhitespaceError(phrase))
        {
            return;
        }

        //ensure that the phrase has ending punctuation
        if(!MarkovChainUtilities.PUNCTUATION_SET.contains(MarkovChainUtilities.endChar(phrase)))
        {
            phrase += DEFAULT_PHRASE_END;
        }


        String[] words = this.splitPattern.split(phrase);

        for(int i = 0; i < words.length; i++)
        {
            if(i == 0)
            {
                String next = i + 1 < words.length ? words[i + 1] : null;
                put(CHAIN_START, words[i]);
                put(words[i], next);
            }
            else if(i == words.length - 1)
            {
                put(CHAIN_END, words[i]);
            }
            else
            {
                put(words[i], words[i + 1]);
            }
        }
    }

    @Override
    public String generateSentence()
    {
        StringBuilder sentence = new StringBuilder();
        String next;


        next = generate(CHAIN_START);
        if(next == null)
        {
            return NO_CHAIN;
        }
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
}
