package com.peterson.markovchain.io;

import com.peterson.markovchain.MarkovChain;

import java.io.IOException;

/**
 * Interface to describe saving an existing MarkovChain to external source
 * Created by Ryan Peterson on 11/07/2015
 */
public interface MarkovSaver
{
    /**
     * Save the chain to external source.
     * @param chain the chain instance to save
     * @throws IOException allow propagation of an IOException
     */
    void save(MarkovChain chain) throws IOException;
}
