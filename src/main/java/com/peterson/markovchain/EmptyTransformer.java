package com.peterson.markovchain;

/**
 * An essentially empty transformer.
 * Returns exactly what is given to it, and is to be used
 * when a more custom transformation is not wanted.
 * @author Peterson, Ryan
 *         Created 12/25/2015
 */
public class EmptyTransformer implements WordTransformer
{
    @Override
    public String transform(String word)
    {
        return word;
    }
}
