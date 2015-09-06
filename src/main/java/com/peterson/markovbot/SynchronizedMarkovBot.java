package com.peterson.markovbot;

import java.io.File;
import java.io.IOException;

/**
 * Extension of the base MarkovBot to expose functionality of concurrency constructs.
 * This class wraps the critical sections present in MarkovBot with concurrent counterparts;
 * internally, a synchronized List (part of this class) and synchronized Map (part of the generator).
 * @author Peterson, Ryan
 *         Created: 6/4/15
 */
public class SynchronizedMarkovBot extends MarkovBot
{
    /**
     * Creates a SynchronizedMarkovBot.
     * Uses thread-safe constructs.
     */
    public SynchronizedMarkovBot()
    {
        super(true);
    }

    /**
     * Creates a SynchronizedMarkovBot.
     * Uses thread-safe constructs and is initialized with a seed file.
     * @param inFile the input file to use to seed the chain
     * @throws IOException if an read error occurs
     */
    public SynchronizedMarkovBot(File inFile) throws IOException
    {
        super(true, inFile);
    }

    /**
     * Creates a SynchronizedMarkovBot.
     * Uses thread-safe constructs, initializes itself with a seed file, and will
     * create a new file to save its state in.
     * @param inFile the input file
     * @param createNew true to create a new file to save the content in
     * @throws IOException if a read/write error occurs.
     */
    public SynchronizedMarkovBot(File inFile, boolean createNew) throws IOException
    {
        super(true, inFile, createNew);
    }
}
