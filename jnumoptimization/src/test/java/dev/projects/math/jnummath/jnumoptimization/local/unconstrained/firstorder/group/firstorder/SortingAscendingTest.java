package dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.group.firstorder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SortingAscendingTest {
    @Test
    public void sortingAscendingTest() {
        List<Integer> list = IntStream.rangeClosed(-1 ,15).boxed().collect(Collectors.toList());

        Integer minValue = list.stream()
                .min((f0, f1) -> {
                    if (f0 > f1) return 1;
                    if (f0 < f1) return -1;
                    return 0;
                })
                .get();

        Assertions.assertEquals(-1, minValue);
    }
}
