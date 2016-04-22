package com.peterson.markovchain.stateless.storage;

import com.peterson.markovchain.stateless.internal.Chain;
import com.peterson.markovchain.stateless.internal.Link;

import java.util.*;

/**
 * Created by Ryan Peterson on 4/21/2016.
 */
public class MapBasedMarkovChainStorage<T> implements MarkovChainStorage<T>
{
    private final Map<T, List<Link<T>>> storage;
    private final List<T> heads;

    public MapBasedMarkovChainStorage()
    {
        storage = newMap();
        heads = new ArrayList<>();
    }

    protected Map<T, List<Link<T>>> newMap()
    {
        return new HashMap<>();
    }

    private List<Link<T>> newList()
    {
        return new ArrayList<>();
    }

    @Override
    public void storeChain(Chain<T> chain)
    {
        if(!chain.getChain().isEmpty())
        {
            heads.add(Optional.ofNullable(chain.getChain().get(0)).orElse(new Link<>(null)).getValue());
        }

        chain.getChain().forEach((link) -> {
            T value = link.getValue();
            //Essentially a get, but if not present, add a new list
            List<Link<T>> list = Optional.ofNullable(storage.get(value)).orElseGet(() ->
            {
                List<Link<T>> newList = newList();
                storage.put(value, newList);
                return newList;
            });
            list.add(link);
        });
    }

    @Override
    public List<T> getNext(T current)
    {
        Link<T> link = next(storage.get(current), current);
        final List<T> list = new ArrayList<>();
        //TODO reconsider api...
        Optional.ofNullable(link).ifPresent((l) -> Optional.ofNullable(l.getNext()).ifPresent((next) -> {
            List<Link<T>> res = storage.get(next.getValue());
            Optional.ofNullable(res).ifPresent((result) -> result.forEach((i) -> list.add(i.getValue())));
        }));

        return list;
    }

    @Override
    public List<T> getHeads()
    {
        return Collections.unmodifiableList(heads);
    }

    private static <T> Link<T> next(List<Link<T>> links, T value)
    {
        for(Link<T> l : links)
        {
            if(l.getValue().equals(value))
            {
                return l;
            }
        }
        return null;
    }
}
