package com.peterson.markovchain.stateless;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Ryan Peterson on 4/20/2016.
 */
public class Chain<T>
{
    private Collection<Link<T>> chain;

    public Chain()
    {
        this(new ArrayList<>());
    }

    public Chain(Collection<Link<T>> chain)
    {
        this.chain = chain;
    }

    public Collection<Link<T>> getChain()
    {
        return chain;
    }

    public void setChain(Collection<Link<T>> chain)
    {
        this.chain = chain;
    }
}
