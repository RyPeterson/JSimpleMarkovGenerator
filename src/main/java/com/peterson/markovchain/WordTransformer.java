package com.peterson.markovchain;

/**
 * Interface to be used when finer grain parsing is
 * needed when the generator is reading in text, such as
 * removing extra characters or punctuation.
 * @author Peterson, Ryan
 *         Created 12/25/2015
 */
public interface WordTransformer
{
    /**
     * Apply a parse to a single word that is read by the
     * generator, as defined by the split when the Pattern is applied in the generator (essentially a String.split())
     * @param word the word to transform or parse
     * @return the result of the application of the custom transformation.
     */
    String transform(String word);
}
