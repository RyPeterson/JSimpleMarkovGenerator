package com.peterson.markovchain.io;

import com.peterson.markovchain.MarkovChain;

import java.io.IOException;

/**
 * Interface to describe how to load a Markov Chain into memory
 * from an outside source.
 * Created by Ryan Peterson on 11/07/2015
 */
public interface MarkovLoader
{
    /**
     * Load the MarkovChain into memory.
     * @return the loaded MarkovChain, with the necessary amount of state.
     * @throws IOException allowance for an IOException to propagate
     */
    MarkovChain load() throws IOException;
}
