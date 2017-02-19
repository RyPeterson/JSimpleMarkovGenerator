package com.peterson.markovchain.state;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by Ryan on 2/19/2017.
 */
public interface NewMapSupplier<T> extends Supplier<Map<T, List<T>>>
{
}
