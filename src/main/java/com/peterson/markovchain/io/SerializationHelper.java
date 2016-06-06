package com.peterson.markovchain.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Ryan Peterson on 4/17/2016.
 */
public class SerializationHelper
{
    private static final Gson SERIALIZER = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(ReadWriteLock.class, (InstanceCreator<ReadWriteLock>) type -> new ReentrantReadWriteLock(true)) //Dont really care about 1:1 serialization of this
            .create();

    public static Gson getInstance()
    {
        return SERIALIZER;
    }
}
