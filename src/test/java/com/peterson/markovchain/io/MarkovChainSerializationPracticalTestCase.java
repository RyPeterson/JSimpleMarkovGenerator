package com.peterson.markovchain.io;

import com.peterson.markovchain.*;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Ryan Peterson on 4/17/2016.
 */
public class MarkovChainSerializationPracticalTestCase
{
    @Test
    public void testSerialization()
    {
        AbstractMarkovChain chain = new BasicMarkovChain();
        chain.addPhrase("She sells seashells by the seashore");
        chain.addPhrase("To be or not to be, that is the question");
        chain.addPhrase("Hello world");

        String json = MarkovChainSerializer.toString(MarkovChainSerializationHelper.getStorage((BasicMarkovChain) chain));
        Assert.assertNotNull(json);

        MarkovChain deserialzed = MarkovChainDeserializer.deserialzeChain(json, false);
        Assert.assertNotNull(deserialzed);
        Assert.assertThat(deserialzed, IsInstanceOf.instanceOf(BasicMarkovChain.class));
        Assert.assertNotNull(deserialzed.generateSentence());

        chain = new ConcurrentBasicMarkovChain();
        chain.addPhrase("She sells seashells by the seashore");
        chain.addPhrase("To be or not to be, that is the question");
        chain.addPhrase("Hello world");

        json = MarkovChainSerializer.toString(MarkovChainSerializationHelper.getStorage((BasicMarkovChain) chain));
        Assert.assertNotNull(json);

        deserialzed = MarkovChainDeserializer.deserialzeChain(json, true);
        Assert.assertThat(deserialzed, IsInstanceOf.instanceOf(ConcurrentBasicMarkovChain.class));
        Assert.assertNotNull(deserialzed.generateSentence());
    }
}
