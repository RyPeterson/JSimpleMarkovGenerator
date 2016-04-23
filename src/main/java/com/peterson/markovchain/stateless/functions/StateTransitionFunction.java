package com.peterson.markovchain.stateless.functions;

import java.util.function.Function;

/**
 * Created by Ryan Peterson on 4/22/2016.
 */
public interface StateTransitionFunction<L, R> extends Function<L, R>
{
}
