package com.peterson.markovchain;

import java.util.List;
import java.util.Map;

/**
 * Created by Ryan Peterson on 4/17/2016.
 */
public class MarkovChainSerializationHelper
{
    public static Class<? extends Map> getBasicMarkovChainStorageClass(BasicMarkovChain chain)
    {
        return chain.markovChain.getClass();
    }

    public static Class<? extends Map> getSeedableMarkovChainStorageClass(SeedableMarkovChain chain)
    {
        return chain.markovChain.getClass();
    }

    public static void setMap(BasicMarkovChain chain, Map<String, List<String>> map)
    {
        chain.markovChain = map;
    }

    public static void setSeedableMap(SeedableMarkovChain chain, Map<String, List<SeedableMarkovChain.Link>> map)
    {
        chain.markovChain = map;
    }

    public static Map<String, List<String>> getStorage(BasicMarkovChain chain)
    {
        return chain.markovChain;
    }

    public static Map<String, List<SeedableMarkovChain.Link>> getSeededStorage(SeedableMarkovChain instance)
    {
        return instance.markovChain;
    }
}
