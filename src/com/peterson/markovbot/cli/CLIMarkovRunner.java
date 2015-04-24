package com.peterson.markovbot.cli;

import com.peterson.markovbot.MarkovBot;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A Command-Line Interface to a MarkovBot.
 * This is a main class that runs a MarkovBot on the command line,
 * receiving input from a user.
 * @author Peterson, Ryan
 *         Created 3/21/2015
 */
public class CLIMarkovRunner
{

    public static final String OUTPUT_SIGNAL = "talk";
    public static final String END_PROGRAM = "end";
    public static final String POTENTIAL_SPELLING_DERP = "tlak";

    public static void main(String []args)
    {
        File inFile = parseFile(args);
        boolean createNew = parseCreateNew(args);
        MarkovBot bot = null;
        try
        {
            bot = new MarkovBot(inFile, createNew);
        }
        catch (IOException e)
        {
            System.out.println("Error encountered when reading file: " + args[0]);
            e.printStackTrace();
            System.exit(2);
        }
        Scanner scan = new Scanner(System.in);

        runMarkovBot(bot, scan);
        try
        {
            bot.saveNewPhrases();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        scan.close();

    }

    /*
    Parse args[0] to get the input file
     */
    private static File parseFile(String []args)
    {
        if(args.length == 0)
        {
            System.out.println("Please pass a file name as a parameter. Program will now end");
            System.exit(1);
        }
        return new File(args[0]);
    }

    /*
    parse args[1] to determine if they want a new file to be created
     */
    private static boolean parseCreateNew(String []args)
    {
        //fall-through logic; assume that they dont want to create a new one, check the length of the args,
        //and then parse for the real truth, and assume parse errors are because the user is dumb and screwed up
        boolean createNew = false;
        if(args.length == 2)
        {
            try
            {
                createNew = Boolean.parseBoolean(args[1]);
            }
            catch (NumberFormatException e)
            {
                createNew = false;
            }
        }

        return createNew;
    }

    /*
        Run the core of the program in this method
     */
    private static void runMarkovBot(MarkovBot bot, Scanner scan)
    {
        int count = 0;
        final int COUNT_PER_PRINT = 5;
        System.out.println("Hello, my name is MarkovBot. Please talk to me, and I'll make funny sentences.");
        String inputLine = null;
        do
        {
            printMenu();
            try
            {
                inputLine = scan.nextLine();
            }
            catch (NoSuchElementException err)
            {
                //99% of the time, this exception will be thrown if the user quits the
                //program; just signal that the program will end so things are saved proper
                inputLine = END_PROGRAM;
            }
            parseInput(inputLine, bot);
            count++;
            if (count >= COUNT_PER_PRINT)
            {
                System.out.println(bot.generateSentence());
                count = 0;
            }

        }
        while (inputLine != null && !inputLine.equalsIgnoreCase(END_PROGRAM));
    }

    /*
    Print out the choices to the user
     */
    private static void printMenu()
    {
        System.out.println("\n\nEnter a phrase to teach the bot or \n'talk' to hear what it has to say, \nor enter 'end' to quit");
        System.out.print(">");
    }

    /*
    parse what the user entered
     */
    private static void parseInput(String inputLine, MarkovBot bot)
    {
        if(inputLine.equalsIgnoreCase(OUTPUT_SIGNAL))
            System.out.println(bot.generateSentence());
        else if(!inputLine.equalsIgnoreCase(END_PROGRAM) && !inputLine.equalsIgnoreCase(POTENTIAL_SPELLING_DERP))
            bot.addPhrase(inputLine);
    }
}
