package com.peterson.markovchain.io;

/**
 * Interface describing an object that can intercept a phrase a MarkovChain
 * is learning.
 * This should not modify the chain!
 * Created by Ryan on 2/3/2017.
 */
public interface TrainingInterceptor<T>
{
    void intercept(T key, T current);
}
