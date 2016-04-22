package com.peterson.markovchain.stateless.internal;


import java.util.Optional;

/**
 * Essentially a doubly linked list node.
 * Just go with it.
 * Created by Ryan Peterson on 4/20/2016.
 */
public class Link<T>
{
    private T value;

    private Link<T> next;

    private Link<T> previous;

    public Link(T value)
    {
        this.value = value;
    }

    public T getValue()
    {
        return value;
    }

    public Link<T> getNext()
    {
        return next;
    }

    //Package protected for a reason...
    void setNext(Link<T> next)
    {
        this.next = next;
    }

    public Link<T> getPrevious()
    {
        return this.previous;
    }

    public static <T> Link<T> assemble(Link<T> previous, T value, Link<T> next)
    {
        Link<T> val;
        val = new Link<>(Optional.ofNullable(value).<RuntimeException>orElseThrow(() -> new UnsupportedOperationException("Cannot use a null value")));
        val.previous = previous;
        val.next = next;
        return val;
    }
}
