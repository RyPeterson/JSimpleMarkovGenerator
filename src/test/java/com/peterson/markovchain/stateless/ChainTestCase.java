package com.peterson.markovchain.stateless;

import com.peterson.markovchain.stateless.internal.Chain;
import com.peterson.markovchain.stateless.internal.Link;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Ryan Peterson on 4/21/2016.
 */
public class ChainTestCase
{
    private Chain<String> chain;

    @Before
    public void setUp() throws Exception
    {
        chain = new Chain<>();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testExternalImmutability()
    {
        chain.getChain().add(new Link<>("Muah-hahaha."));
    }

    @Test
    public void testAddChain()
    {
        Arrays.asList("one", "two", "three").forEach((i) -> chain.addLink(i));

        List<Link<String>> result = chain.getChain();
        Assert.assertNull(result.get(0).getPrevious());
        Assert.assertNotNull(result.get(0).getNext());
        Assert.assertEquals("one", result.get(0).getValue());

        Assert.assertEquals(result.get(0), result.get(1).getPrevious());

        Assert.assertEquals(result.get(1), result.get(2).getPrevious());

        Assert.assertNull(result.get(2).getNext());

        chain.addLink("four");

        Assert.assertNotNull(result.get(2).getNext());

        Assert.assertEquals(result.get(2), result.get(3).getPrevious());
    }

}