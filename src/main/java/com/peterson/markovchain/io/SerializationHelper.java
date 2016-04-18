package com.peterson.markovchain.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.peterson.markovchain.EmptyTransformer;
import com.peterson.markovchain.WordTransformer;

/**
 * Created by Ryan Peterson on 4/17/2016.
 */
public class SerializationHelper
{
    private static final Gson SERIALIZER = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(WordTransformer.class, (InstanceCreator<WordTransformer>) type -> new EmptyTransformer())
            .create();

    public static Gson getInstance()
    {
        return SERIALIZER;
    }
}
