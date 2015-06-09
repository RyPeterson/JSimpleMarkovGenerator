package tests;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

/**
 * @author Peterson, Ryan
 *         Created: 6/9/15
 */
public class TestUtil
{
    public static String []loadFile() throws IOException
    {
        URL res = TestUtil.class.getResource("testinput.txt");
        File inFile = new File(res.toString());
        List<String> lines = Files.readAllLines(inFile.toPath());
        return lines.toArray(new String[lines.size()]);
    }
}
