package dev.projects.math.datasources.supervised;

import dev.projects.math.datasources.unsupervised.MultidimensionalUnsupervisedDataSet;
import dev.projects.utils.exception.LoggableException;

import java.util.*;

public class OneDimensionalSupervisedDataSet
        extends LinkedHashMap<List<Double>, Double>
        implements Map<List<Double>, Double> {

    /**
     * Divide a sample by two subsamples
     * @param sampleCoeff
     * @return 0 - sampleCoeff*size of a sample is belong to first and 1 to second
     * @throws LoggableException
     */
    public OneDimensionalSupervisedDataSet[] splitByRows(double sampleCoeff) throws LoggableException {
        if ((sampleCoeff <= 0.0) || (sampleCoeff >= 1.0)) throw new LoggableException("Unacceptable coeff");

        //TODO: недостаточно данных
        int divideIndex = (int) Math.round(Math.ceil(sampleCoeff * size()));

        List<Double>[] keys = keySet().toArray(new ArrayList[]{new ArrayList<Double>(0)});

        OneDimensionalSupervisedDataSet first = new OneDimensionalSupervisedDataSet();

        for(int index = 0; index < divideIndex; index++){
            List<Double> key = keys[index];

            first.put(key, get(key));
        }

        OneDimensionalSupervisedDataSet second = new OneDimensionalSupervisedDataSet();

        for(int index = divideIndex; index < size(); index++){
            List<Double> key = keys[index];

            second.put(key, get(key));
        }

        return new OneDimensionalSupervisedDataSet[]{first, second};
    }

    public MultidimensionalUnsupervisedDataSet convertToUnsupervisedDataSet() throws LoggableException {
        MultidimensionalUnsupervisedDataSet dataSet = new MultidimensionalUnsupervisedDataSet();

        for(List<Double> key: keySet()) {
            dataSet.add(key);
        }

        return dataSet;
    }
}
