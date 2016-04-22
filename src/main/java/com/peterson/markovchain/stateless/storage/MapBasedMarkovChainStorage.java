package com.peterson.markovchain.stateless.storage;

import com.peterson.markovchain.stateless.Chain;
import com.peterson.markovchain.stateless.Link;

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
        storage = new HashMap<>();
        heads = new ArrayList<>();
    }

    protected Map<T, T> newMap()
    {
        return new HashMap<>();
    }

    private List<Link<T>> newList()
    {
        return newList();
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
            List<Link<T>> list = storage.get(value);
            if(list == null)
            {
                list = new ArrayList<>();
                storage.put(value, list);
            }
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
