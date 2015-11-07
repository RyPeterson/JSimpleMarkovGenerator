package com.peterson.markovchain.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.peterson.markovchain.MarkovChain;

import java.io.IOException;

/**
 * A GSON implementation of MarkovLoader and MarkovSaver.
 * This converts the existing markov chain to JSON and back.
 * This also is abstract to allow for flexible loading schemes (local file, JSON response, etc.)
 * Created by Ryan Peterson on 11/07/2015
 */
public abstract class GSONMarkovIO implements MarkovLoader, MarkovSaver
{
    protected final Gson gson;
    protected final Class<? extends MarkovChain> instanceTyping;

    public GSONMarkovIO(Class<? extends MarkovChain> instanceType)
    {
        gson = new GsonBuilder().generateNonExecutableJson().setPrettyPrinting().create();
        this.instanceTyping = instanceType;
    }

    @Override
    public MarkovChain load() throws IOException
    {
        String jsonString = new String(getChainBytes());

        return gson.fromJson(jsonString, instanceTyping);
    }

    @Override
    public void save(MarkovChain chain) throws IOException
    {
        if(!chain.getClass().equals(instanceTyping))
        {
            throw new IOException(String.format("Expected chain of %s but got chain of type %s", instanceTyping, chain.getClass()));
        }
    }

    /**
     * Get the bytes that represent the JSON for the chain.
     * @return the array of bytes that represent the JSON string
     * @throws IOException allow propagation of IOException
     */
    public abstract byte[] getChainBytes() throws IOException;

    /**
     * Convert the MarkovChain to the appropriate JSON
     * @param chain the chain to convert
     * @return the string that represents the JSON
     */
    public String getJSON(MarkovChain chain)
    {
        return gson.toJson(chain, instanceTyping);
    }
}
