package com.peterson.markovchain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * @author Peterson, Ryan
 *         Created: 6/9/15
 */
public class TestUtil
{
    //public static final String PATH_TO_FILE = "/home/ryan/Desktop/testinput.txt";
    public static final String PATH_TO_FILE = "C:/users/rpete_000/Desktop/testinput.txt";

    public static String []loadFile() throws IOException
    {
        File inFile = new File(PATH_TO_FILE);
        List<String> lines = Files.readAllLines(inFile.toPath());
        return lines.toArray(new String[lines.size()]);
    }
}
