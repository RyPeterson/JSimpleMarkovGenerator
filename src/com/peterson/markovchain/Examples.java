package com.peterson.markovchain;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Peterson, Ryan
 *         Created: 3/14/2015
 */
public class Examples
{
    public static void main(String []args) throws IOException
    {
        MarkovChain mc = new MarkovChain();
        URL l = MarkovChain.class.getResource("hamlet.txt");
        File f = new File(l.getPath());
        File f2 = new File(MarkovChain.class.getResource("assemblytutorial.txt").getPath());

        //I know its just chaining together the same things, but I want a nice pool of randomness
        for(int i = 0; i < 10; i++)
        {
            MarkovChainUtilities.extendChainWithFile(mc, f);
            MarkovChainUtilities.extendChainWithFile(mc, f2);
        }
        for(int i = 0; i < 1000; i++)
            System.out.println(mc.generateSentence());
    }
}
