package dev.projects.math.datasources;

import dev.projects.math.datasources.supervised.MultidimensionalSupervisedDataSet;
import dev.projects.utils.exception.LoggableException;
import dev.projects.math.datasources.supervised.OneDimensionalSupervisedDataSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.stream.Collectors;

public class MultidimensionalSupervisedDataSetTests {

    @Test
    public void multidimensionalSupervisedDataSetTest() throws IOException, LoggableException {
        MultidimensionalSupervisedDataSet dataSet = new MultidimensionalSupervisedDataSet();

        try (InputStream dataStream = getClass().getClassLoader().getResourceAsStream("supervised_data.csv")) {
            dataSet.read(dataStream, 2);
        }

        Double[] key = dataSet.keySet().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .toArray(new Double[0]),

                values = dataSet.values().stream()
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())
                        .toArray(new Double[0]);

        Assertions.assertArrayEquals(key, new Double[]{1.0, 2.0, 3.0, 4.0});
        Assertions.assertArrayEquals(values, new Double[]{5.0, 6.0});
    }

    @Test
    public void onedimensionalSupervisedDataSetTest() throws LoggableException, IOException {
        MultidimensionalSupervisedDataSet dataSet = new MultidimensionalSupervisedDataSet();

        try (InputStream dataStream = getClass().getClassLoader().getResourceAsStream("supervised_data.csv")) {
            dataSet.read(dataStream, 1);
        }

        OneDimensionalSupervisedDataSet oneDataSet = dataSet.sliceToOneDimension(0);

        Double[] key = oneDataSet.keySet().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .toArray(new Double[0]),

                values = oneDataSet.values().stream()
                        .collect(Collectors.toList())
                        .toArray(new Double[0]);

        Assertions.assertArrayEquals(key, new Double[]{1.0, 2.0, 3.0, 4.0, 5.0});
        Assertions.assertArrayEquals(values, new Double[]{6.0});
    }
}
