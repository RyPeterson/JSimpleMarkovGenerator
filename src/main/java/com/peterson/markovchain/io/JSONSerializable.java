package com.peterson.markovchain.io;

/**
 * Interface to implement to convert a markov chain
 * container to a JSON string.
 * Created by Ryan Peterson on 01/10/2016
 */
public interface JSONSerializable
{
    /**
     * Convert a Markov Chain object to a JSON string.
     * @return
     */
    String toJSON();
}
