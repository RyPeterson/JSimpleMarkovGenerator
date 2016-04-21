package com.peterson.markovchain.stateless;

/**
 * Created by Ryan Peterson on 4/20/2016.
 */
public class Link<T>
{
    private T value;

    private Link<T> next;

    public Link(T value, Link<T> next)
    {
        this.value = value;
        this.next = next;
    }

    public Link(T value)
    {
        this(value, null);
    }

    public T getValue()
    {
        return value;
    }

    public void setValue(T value)
    {
        this.value = value;
    }

    public Link<T> getNext()
    {
        return next;
    }

    public void setNext(Link<T> next)
    {
        this.next = next;
    }
}
