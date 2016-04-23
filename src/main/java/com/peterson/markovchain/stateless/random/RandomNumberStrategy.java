package com.peterson.markovchain.stateless.random;

/**
 * A strategy to create a random number.
 * This will give flexibilty to create a random number in a normal, thread safe, and test manner
 * Created by Ryan Peterson on 4/22/2016.
 */
@FunctionalInterface
public interface RandomNumberStrategy
{
    int nextInt(int upperBound);
}
