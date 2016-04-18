package com.peterson.markovchain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ryan Peterson on 4/17/2016.
 */
public abstract class AbstractSeedableMarkovChain extends AbstractMarkovChain
{

    protected Map<String, List<SeedableMarkovChain.Link>> newSeedableMap()
    {
        return new HashMap<>();
    }

    protected List<SeedableMarkovChain.Link> newLinkList()
    {
        return new ArrayList<>();
    }
}
