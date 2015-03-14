package com.peterson.markovchain;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Utility/Factory methods for Markov Chains.
 * @author Peterson, Ryan
 *         Created: 3/14/2015
 */
public class MarkovChainUtilities
{
    /**
     * Generate a Markov Chain from a seed file.
     * The assumption is that each sentence is separated by a new line. Also note that empty lines are ignored
     * @param seed the file to seed the chain with
     * @return a fully constructed MarkovChain object
     * @throws IOException
     */
    public static MarkovChain generateFromFile(File seed) throws IOException
    {
        MarkovChain markovChain = new MarkovChain();
        // <3
        extendChainWithFile(markovChain, seed);
        return markovChain;
    }

    /**
     * Extend an existing Markov Chain with data from a file.
     * @param markovChain the existing markov chain
     * @param seed the file to add data to the markov chain with.
     * @throws IOException
     */
    public static void extendChainWithFile(MarkovChain markovChain, File seed) throws IOException
    {
        BufferedReader reader = Files.newBufferedReader(seed.toPath());
        String line;
        while((line = reader.readLine()) != null)
        {
            if(line.length() > 0)
                markovChain.addPhrase(line);
        }

        reader.close();
    }

    /**
     * Generate a markov chain from a file using a specific regex to separate sentences by.
     * @param seed the seed file to create the Markov Chain from.
     * @param parseRegex the regular expression to use to parse with the assumption that each sentence is separated by it
     * @return a constructed Markov Chain object
     * @throws IOException
     */
    public static MarkovChain generateFromFile(File seed, String parseRegex) throws IOException
    {
        MarkovChain markovChain = new MarkovChain();
        extendChainWithFile(markovChain, seed, parseRegex);
        return markovChain;
    }

    /**
     * Extend an existing markov chain with a file using the provided regex to separate the sentence with.
     * @param markovChain an existing markov chain to add to
     * @param seed the file to add to the chain
     * @param parseRegex the regex to separate sentences by.
     * @throws IOException
     */
    public static void extendChainWithFile(MarkovChain markovChain, File seed, String parseRegex) throws IOException
    {
        BufferedReader reader = Files.newBufferedReader(seed.toPath());
        String line;
        while((line = reader.readLine()) != null)
        {
            String []parsed = line.split(parseRegex);
            for(String s : parsed)
            {
                if(s.length() > 1)
                    markovChain.addPhrase(s);
            }
        }
    }
}
