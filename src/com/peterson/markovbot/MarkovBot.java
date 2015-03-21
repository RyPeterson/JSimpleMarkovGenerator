package com.peterson.markovbot;

import com.peterson.markovchain.MarkovChain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * An example "bot" that assists in generating and maintaining Markov Chains.
 * This bot allows the input of all sentences the generator uses so that it can be saved to a file
 * for later use.
 * This is a nice, interactive example that can be used to demonstrate Markov Chains
 * @author Peterson, Ryan
 *         Created 3/21/2015
 */
public class MarkovBot
{
    private MarkovChain markovChain;
    private StringBuilder newData;
    private File brainFile;

    /**
     * Save-stateless constructor.
     * This creates a MarkovBot that cannot save the data it encounters, and therefore it cannot be saved
     * to a file. This also generates an initial state MarkovChain, ie. one that is empty.
     */
    public MarkovBot()
    {
        markovChain = new MarkovChain();
        newData = new StringBuilder();
        brainFile = null;
    }

    /**
     * Save-state constructor.
     * This creates a MarkovBot that will both read in the entire file to generate a MarkovChain as well as write new data to
     * a file if the saveChains() method is called.
     * <br/>
     * The precondition contract is such:
     * <li>-The file exists; if not, see MarkovBot(File inFile, boolean createNew)</li>
     * <li>-The file to be read in has all phrases separated by a newline, as described by <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#readAllLines-java.nio.file.Path-">Files.readAllLines()</a></li>
     * <br/>
     * The runtime condition is such:
     * <li>-All new phrases are to be saved with a new line separating them</li>
     * <li>-Calls to saveChains() write to the exact file passed in, appending the new data to it and clearing current save state data</li>
     * @param inFile the file to load as the bot's brain
     * @throws IOException if an IOException occurs with the file input
     */
    public MarkovBot(File inFile) throws IOException
    {
        this();
        brainFile = inFile;
        load();
    }

    /**
     * Save-state constructor with optional file creation.
     * This creates a MarkovBot that will save new data to the passed file, but differs from MarkovBot(File inFile)
     * in that, if createNew is true, it will create a new file named as the one passed in during construction.
     * This does so <b>even if the file already exists</b>, effectively overwriting the contents.
     * The general contracts specified in MarkovBot(File inFile) apply, except for the preconditions if createNew is false.
     * @param inFile the file to save phrases to.
     * @param createNew
     * @throws IOException if an IOException occurs with creating a file or loading the file.
     */
    public MarkovBot(File inFile, boolean createNew) throws IOException
    {
        this();
        brainFile = inFile;
        if(!createNew)
            load();
        else
        {
            Files.createFile(inFile.toPath());
        }
    }

    private void load() throws IOException
    {
        for(String line : Files.readAllLines(brainFile.toPath()))
            markovChain.addPhrase(line);
    }


    public void saveChains()
    {

    }
}
