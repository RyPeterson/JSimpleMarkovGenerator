package com.peterson.markovbot;

import com.peterson.markovchain.MarkovChain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

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
    private List<String> newData;
    private File brainFile;

    /**
     * Save-stateless constructor.
     * This creates a MarkovBot that cannot save the data it encounters, and therefore it cannot be saved
     * to a file. This also generates an initial state MarkovChain, ie. one that is empty.
     */
    public MarkovBot()
    {
        markovChain = new MarkovChain();
        newData = new ArrayList<>();
        newData.add("\n");
        brainFile = null;
    }

    /**
     * Save-state constructor.
     * This creates a MarkovBot that will both read in the entire file to generate a MarkovChain as well as write new data to
     * a file if the saveNewPhrases() method is called.
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
     * @param createNew true if the file does not exist and needs to be created, false if not
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

    /*
        Helper method to load the file contents into the markov generator
     */
    private void load() throws IOException
    {
        Files.readAllLines(brainFile.toPath()).forEach(markovChain::addPhrase);
    }

    /**
     * Adds a new phrase to the Markov generator.
     * This both adds the phrase to the actual Markov generator as well as
     * prepares that phrase to be saved.
     * @param phrase the phrase to add to the Markov generator and to the file
     */
    public void addPhrase(String phrase)
    {
        markovChain.addPhrase(phrase);
        newData.add(phrase);
    }

    /**
     * Adds new phrases to the Markov generator.
     * This adds all the phrases in the Iterable to the Markov generator and to the running set of data
     * to be saved to the file.
     * @param phrases the phrases to add to the generator.
     */
    public void addPhrases(Iterable<String> phrases)
    {
        for(String s : phrases)
            addPhrase(s);
    }

    /**
     * A varargs version of addPhrases(Iterable<String> phrases) provided as convenience.
     * @param phrases the varargs of phrases to add to the generator and data
     */
    public void addPhrases(String ...phrases)
    {
        for(String s : phrases)
            addPhrase(s);
    }

    /**
     * Generate a random sentence from the generator.
     * @see com.peterson.markovchain.MarkovChain for details.
     * @return a random sentence from the Markov Chain generator
     */
    public String generateSentence()
    {
        return markovChain.generateSentence();
    }

    /**
     * Generates multiple sentences from the generator.
     * This is equivalent to calling generateSentence() more than once.
     * @param numberOfSentences the number of Markov Chain sentences to generate
     * @return an array of generated Markov chains.
     */
    public String[] generateSentences(int numberOfSentences)
    {
        String []arr = new String[numberOfSentences];
        for(int i = 0; i < arr.length; i++)
            arr[i] = markovChain.generateSentence();
        return arr;
    }

    /**
     * Save newly found phrases to the file passed at construction.
     * The contract is that each new phrase is separated by a new line.
     * After saving, the internal data storage of encountered data is cleared and starts off as empty.
     * @throws IOException if an IOException occurs when trying to save the data
     */
    public void saveNewPhrases() throws IOException
    {
        if(brainFile != null)
        {
            //save to file
            Files.write(brainFile.toPath(), newData, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
            //clear new data and start fresh
            newData.clear();
            newData.add("\n");
        }
    }
}
