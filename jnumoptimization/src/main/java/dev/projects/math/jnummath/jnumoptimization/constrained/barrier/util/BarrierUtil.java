package dev.projects.math.jnummath.jnumoptimization.constrained.barrier.util;

import dev.projects.utils.exception.LoggableException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BarrierUtil {

    public static List<Double> getParametersIndexedList(int size, int index, double value)  {
        if ((index < 0) || (index >= size)) throw new LoggableException("Unacceptable index");

        ArrayList<Double> list= new ArrayList<>(Arrays.asList(new Double[size]));
        Collections.fill(list, 0.0);

        list.set(index, value);

        return list;
    }

    public static List<Integer> getValuesIndexedList(int size)   {
        return IntStream.range(0, size).boxed().collect(Collectors.toList());
    }
}
