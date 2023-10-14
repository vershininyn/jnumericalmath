package dev.projects.math.jnummath.jdatasources;

import dev.projects.math.jnummath.jdatasources.unsupervised.MultidimensionalUnsupervisedDataSet;
import dev.projects.math.jnummath.jdatasources.unsupervised.OneDimensionalUnsupervisedDataSet;
import dev.projects.utils.exception.LoggableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.stream.Collectors;

public class MultidimensionalUnsupervisedDataSetTests {

    @Test
    public void multidimensionalUnsupervisedDataSetTest()  , IOException {
        MultidimensionalUnsupervisedDataSet dataSet = new MultidimensionalUnsupervisedDataSet();

        try (InputStream dataStream = getClass().getClassLoader().getResourceAsStream("unsupervised_data.csv")) {
            dataSet.read(dataStream);
        }

        Double[] key = dataSet.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .toArray(new Double[0]);

        Assertions.assertArrayEquals(key, new Double[]{1.0, 2.0, 3.0, 4.0, 5.0,
                6.0, 7.0, 8.0, 9.0, 10.0,
                6.0, 7.0, 8.0, 9.0, 10.0,
                6.0, 7.0, 8.0, 9.0, 10.0,
                6.0, 7.0, 8.0, 9.0, 10.0,
                6.0, 7.0, 8.0, 9.0, 10.0,
                6.0, 7.0, 8.0, 9.0, 10.0,
                6.0, 7.0, 8.0, 9.0, 10.0,
                6.0, 7.0, 8.0, 9.0, 10.0,
                6.0, 7.0, 8.0, 9.0, 10.0});
    }

    @Test
    public void onedimensionalSupervisedDataSetTest()  , IOException {
        MultidimensionalUnsupervisedDataSet dataSet = new MultidimensionalUnsupervisedDataSet();

        try (InputStream dataStream = getClass().getClassLoader().getResourceAsStream("unsupervised_data.csv")) {
            dataSet.read(dataStream);
        }

        OneDimensionalUnsupervisedDataSet oneDataSet = dataSet.sliceToOneDimension(0);

        Double[] key = oneDataSet.stream()
                .collect(Collectors.toList())
                .toArray(new Double[0]);

        Assertions.assertArrayEquals(key, new Double[]{1.0, 6.0, 6.0, 6.0, 6.0, 6.0, 6.0, 6.0, 6.0, 6.0});
    }

    @Test
    public void splitByRowsTest()  , IOException {
        MultidimensionalUnsupervisedDataSet dataSet = new MultidimensionalUnsupervisedDataSet();

        try (InputStream dataStream = getClass().getClassLoader().getResourceAsStream("unsupervised_data.csv")) {
            dataSet.read(dataStream);
        }

        MultidimensionalUnsupervisedDataSet[] splitedArray = dataSet.splitByRows(0.3F);

        Assertions.assertEquals(splitedArray[0].size(), 3);
        Assertions.assertEquals(splitedArray[1].size(), 7);
    }
}
