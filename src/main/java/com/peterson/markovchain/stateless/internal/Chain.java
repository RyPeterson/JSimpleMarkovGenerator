package com.peterson.markovchain.stateless.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Ryan Peterson on 4/20/2016.
 */
public class Chain<T>
{
    private List<Link<T>> chain;

    public Chain()
    {
        this(new ArrayList<>());
    }

    public Chain(List<Link<T>> chain)
    {
        this.chain = chain;
    }

    /**
     * Return an immutable view of the current chain
     * @return
     */
    public List<Link<T>> getChain()
    {
        //All your list are belong to us
        return Collections.unmodifiableList(chain);
    }

    public void addLink(T value)
    {
        Link<T> previous = get();

        Link<T> newLink = Link.assemble(previous, value, null);

        Optional.ofNullable(previous).ifPresent(tLink -> tLink.setNext(newLink));

        chain.add(newLink);
    }

    private Link<T> get()
    {
        return chain.isEmpty() ? null : chain.get(chain.size() - 1);
    }
}
