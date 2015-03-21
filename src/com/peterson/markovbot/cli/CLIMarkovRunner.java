package com.peterson.markovbot.cli;

import com.peterson.markovbot.MarkovBot;

import java.io.File;
import java.io.IOException;
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
        scan.close();

    }

    private static File parseFile(String []args)
    {
        if(args.length == 0)
        {
            System.out.println("Please pass a file name as a parameter. Program will now end");
            System.exit(1);
        }
        return new File(args[0]);
    }

    private static boolean parseCreateNew(String []args)
    {
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

    private static void runMarkovBot(MarkovBot bot, Scanner scan)
    {
        System.out.println("Hello, my name is MarkovBot. Please talk to me, and I'll make funny sentences.");
        String inputLine = null;
        do
        {
            printMenu();
            inputLine = scan.nextLine();
            parseInput(inputLine, bot);

        }
        while (inputLine != null && !inputLine.equalsIgnoreCase(END_PROGRAM));
    }

    private static void printMenu()
    {
        System.out.println("\n\nEnter a phrase to teach the bot or 'talk' to hear what it has to say, or enter 'end' to quit");
        System.out.print(">");
    }

    private static void parseInput(String inputLine, MarkovBot bot)
    {
        if(inputLine.equalsIgnoreCase(OUTPUT_SIGNAL))
            System.out.println(bot.generateSentence());
        else if(!inputLine.equalsIgnoreCase(END_PROGRAM))
            bot.addPhrase(inputLine);
    }
}
