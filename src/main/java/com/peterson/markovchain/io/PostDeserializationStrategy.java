package com.peterson.markovchain.io;

import com.peterson.markovchain.MarkovChain;

/**
 * Created by Ryan on 2/3/2017.
 */
public interface PostDeserializationStrategy
{
    /**
     * Perform additional steps after reconstructing the state of the MarkovChain.
     * @param markovChain the post-serialized MarkovChain
     */
    void postDeserializationInitialization(MarkovChain markovChain);
}
