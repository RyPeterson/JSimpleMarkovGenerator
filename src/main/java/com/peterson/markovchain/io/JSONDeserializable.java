package com.peterson.markovchain.io;

/**
 * Interface to impalement to convert a JSON string
 * to a Markov Chain container
 * Created by Ryan Peterson on 01/10/2016
 */
public interface JSONDeserializable
{
    /**
     * From a String representing a markov chain, turn it into a Markov Chain.
     * Void is meant to allow either a MarkovChain class to instantiate itself, or an external
     * class to create a Markov Chain
     */
     void fromJSON(String jsonString);
}
