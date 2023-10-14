package dev.projects.math.jnummath.jdatasources;

import dev.projects.math.jnummath.jdatasources.unsupervised.OneDimensionalUnsupervisedDataSet;
import dev.projects.utils.exception.LoggableException;
import dev.projects.math.jnummath.jdatasources.supervised.OneDimensionalSupervisedDataSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OneDimensionalUnsupervisedDataSetTests {

    @Test
    public void oneDimensionalUnsupervisedDataSetWindowTest()   {
        OneDimensionalUnsupervisedDataSet dataSet = new OneDimensionalUnsupervisedDataSet();

        for(double index = -10.0; index < 10.0; index += 1.0) {
            dataSet.add(index);
        }

        OneDimensionalSupervisedDataSet windowDataSet = dataSet.performWindowTransformation(5);

        List<Double>[] keys = windowDataSet.keySet().toArray(new ArrayList[]{new ArrayList<Double>(0)});

        Assertions.assertEquals(Arrays.asList(-10.0,-9.0,-8.0,-7.0,-6.0), keys[0]);
        Assertions.assertEquals(-5.0, windowDataSet.get(keys[0]));

        Assertions.assertEquals(Arrays.asList(-4.0,-3.0,-2.0,-1.0,0.0), keys[1]);
        Assertions.assertEquals(1.0, windowDataSet.get(keys[1]));

        Assertions.assertEquals(Arrays.asList(2.0,3.0,4.0,5.0,6.0), keys[2]);
        Assertions.assertEquals(7.0, windowDataSet.get(keys[2]));

        Assertions.assertEquals(Arrays.asList(8.0,9.0,0.0,0.0,0.0), keys[3]);
        Assertions.assertEquals(0.0, windowDataSet.get(keys[3]));
    }

    @Test
    public void oneDimensionalUnsupervisedDataSetSplitTest()   {
        OneDimensionalUnsupervisedDataSet dataSet = new OneDimensionalUnsupervisedDataSet();

        for(double index = -10.0; index < 10.0; index += 1.0) {
            dataSet.add(index);
        }

        OneDimensionalSupervisedDataSet windowDataSet = dataSet.performWindowTransformation(6);

        OneDimensionalSupervisedDataSet[] splitedDataSet = windowDataSet.splitByRows(0.4);

        Assertions.assertEquals(2, splitedDataSet[0].size());
        Assertions.assertEquals(2, splitedDataSet[1].size());
    }
}
