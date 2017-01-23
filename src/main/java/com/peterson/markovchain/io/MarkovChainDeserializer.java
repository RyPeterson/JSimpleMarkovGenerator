package com.peterson.markovchain.io;

import com.peterson.markovchain.BasicMarkovChain;
import com.peterson.markovchain.ConcurrentBasicMarkovChain;
import com.peterson.markovchain.MarkovChain;
import com.peterson.markovchain.MarkovChainSerializationHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Created by Ryan Peterson on 4/17/2016.
 */
public class MarkovChainDeserializer
{
    public static MarkovChain deserialzeJSON(Path path, Class<? extends MarkovChain> classType) throws IOException
    {
        String json = new String(Files.readAllBytes(path));
        return SerializationHelper.getInstance().fromJson(json, classType);
    }

    public static MarkovChain deserializeFromMap(Path path, boolean concurrent, boolean seedable) throws IOException
    {
        if(seedable)
        {
            throw new UnsupportedOperationException("Sadly, GSON is stupid and doesn't like the Link class");
        }
        else
        {
            return deserialzeChain(path, concurrent);
        }
    }

    static MarkovChain deserialzeChain(String json, boolean concurrent)
    {
        BasicMarkovChain chain = concurrent ? new ConcurrentBasicMarkovChain() : new BasicMarkovChain();
        Map<String, List<String>> map = SerializationHelper.getInstance()
                .fromJson(json, MarkovChainSerializationHelper.getBasicMarkovChainStorageClass(chain));
        MarkovChainSerializationHelper.setMap(chain, map);
        return chain;
    }

    private static MarkovChain deserialzeChain(Path path, boolean concurrent) throws IOException
    {
        return deserialzeChain(new String(Files.readAllBytes(path)), concurrent);
    }

}
