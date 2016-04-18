package com.peterson.markovchain.io;

import com.peterson.markovchain.BasicMarkovChain;
import com.peterson.markovchain.MarkovChain;
import com.peterson.markovchain.MarkovChainSerializationHelper;
import com.peterson.markovchain.SeedableMarkovChain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

/**
 * Created by Ryan Peterson on 4/17/2016.
 */
public class MarkovChainSerializer
{
    public static void serialize(Path path, MarkovChain instance) throws IOException
    {
        String json = toString(instance);
        Files.write(path, json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

    }

    public static void serialzeMap(Path path, MarkovChain instance) throws IOException
    {
        String json;
        if(instance instanceof SeedableMarkovChain)
        {
            json = toStringSeeded(MarkovChainSerializationHelper.getSeededStorage((SeedableMarkovChain) instance));
        }
        else
        {
            json = toString(MarkovChainSerializationHelper.getStorage((BasicMarkovChain) instance));
        }

        Files.write(path, json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    static String toString(Map<String, List<String>> map)
    {
        return SerializationHelper.getInstance().toJson(map);
    }

    static String toStringSeeded(Map<String, List<SeedableMarkovChain.Link>> map)
    {
        return SerializationHelper.getInstance().toJson(map);
    }

    static String toString(MarkovChain instance)
    {
        return SerializationHelper.getInstance().toJson(instance);
    }
}
