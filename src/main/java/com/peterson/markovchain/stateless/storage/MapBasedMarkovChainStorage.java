package com.peterson.markovchain.stateless.storage;

import com.peterson.markovchain.stateless.internal.Chain;
import com.peterson.markovchain.stateless.internal.Link;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
        List<Link<T>> links = chain.getChain();
        if(!links.isEmpty())
        {
            heads.add(Optional.ofNullable(links.get(0)).orElse(new Link<>(null)).getValue());

           links.forEach((link) ->
           {
                T value = link.getValue();
                //Essentially a get, but if not present, add a new list
                List<Link<T>> list = Optional.ofNullable(storage.get(value)).orElseGet(new ListProducer(value));
                list.add(link);
            });
        }
    }

    @Override
    public List<T> getNext(T current)
    {
        Link<T> link = getLink(storage.get(current), current);
        LinkConsumer consumer = new LinkConsumer();
        Optional.ofNullable(link).ifPresent(consumer);
        return consumer.getResult();
    }

    @Override
    public List<T> getHeads()
    {
        return Collections.unmodifiableList(heads);
    }

    private static <T> Link<T> getLink(List<Link<T>> links, T value)
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

    private class ListProducer implements Supplier<List<Link<T>>>
    {
        private final T key;

        ListProducer(T key)
        {
            this.key = key;

        }

        @Override
        public List<Link<T>> get()
        {
            List<Link<T>> newList = newList();
            storage.put(key, newList);
            return newList;
        }
    }

    private class LinkConsumer implements Consumer<Link<T>>
    {
        private final List<T> list;
        private final Consumer<List<Link<T>>> consumer;

        LinkConsumer()
        {
            list = new ArrayList<>();
            consumer = new ListLinkConsumer(list);
        }

        @Override
        public void accept(Link<T> link)
        {
            Optional.ofNullable(link.getNext()).ifPresent((val) -> Optional.ofNullable(val.getValue()).ifPresent((next)
                    -> Optional.ofNullable(storage.get(next)).ifPresent(consumer)));
        }

        List<T> getResult()
        {
            return list;
        }
    }

    private class ListLinkConsumer implements Consumer<List<Link<T>>>
    {
        private final List<T> list;

        ListLinkConsumer(List<T> list)
        {
            this.list = list;
        }

        @Override
        public void accept(List<Link<T>> ts)
        {
            Optional.ofNullable(ts).ifPresent((links) -> links.forEach((i) -> list.add(i.getValue())));

        }
    }
}
