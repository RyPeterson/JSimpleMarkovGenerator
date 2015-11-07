package com.peterson.markovchain.io;

import com.peterson.markovchain.MarkovChain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Local File system serializer and deserializer for Markov Chains
 * Created by Ryan Peterson on 11/07/2015
 */
public class LocalFileGSONMarkovIO extends GSONMarkovIO
{
    private final File chainFile;

    /**
     * Construct a Serializer and Deserializer that saves to the local file system.
     * @param instanceType the type of chain serializing.
     * @param markovChainJSON the file to save to, or null if it shouldn't be saved or loaded
     */
    public LocalFileGSONMarkovIO(Class<? extends MarkovChain> instanceType, File markovChainJSON)
    {
        super(instanceType);
        this.chainFile = markovChainJSON;
    }

    @Override
    public byte[] getChainBytes() throws IOException
    {
        if(chainFile != null)
        {
            return Files.readAllBytes(chainFile.toPath());
        }
        return new byte[]{};
    }

    @Override
    public void save(MarkovChain chain) throws IOException
    {
        super.save(chain);
        if(chainFile != null)
        {
            Files.write(chainFile.toPath(), super.getJSON(chain).getBytes());
        }
    }
}
