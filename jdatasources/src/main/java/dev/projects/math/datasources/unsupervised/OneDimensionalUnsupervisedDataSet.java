package dev.projects.math.datasources.unsupervised;

import dev.projects.utils.exception.LoggableException;
import dev.projects.math.datasources.supervised.OneDimensionalSupervisedDataSet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OneDimensionalUnsupervisedDataSet
        extends LinkedList<Double>
        implements List<Double> {

    public OneDimensionalSupervisedDataSet performWindowTransformation(int windowSize) throws LoggableException {
        if ((windowSize <= 0) || (windowSize >= size())) throw new LoggableException("Unacceptable window size");

        int offset = (size() % windowSize == 0) ? (0) : (1),
                zeroCount = (int) ((Math.round(Math.ceil(size() / windowSize)) + offset) * (windowSize + 1) - size());

        OneDimensionalUnsupervisedDataSet newDataSet = new OneDimensionalUnsupervisedDataSet();

        newDataSet.addAll(this);

        for (int index = 0; index < zeroCount; index++) {
            newDataSet.add(0.0);
        }

        OneDimensionalSupervisedDataSet result = new OneDimensionalSupervisedDataSet();

        int windowIndex = 0;

        while (windowIndex < (newDataSet.size() - 1)) {
            List<Double> independentVariables = new ArrayList<>(windowSize);

            for (int index = 0; index < windowSize; index++) {
                independentVariables.add(newDataSet.get(windowIndex));

                windowIndex++;
            }

            result.put(independentVariables, newDataSet.get(windowIndex));

            windowIndex++;
        }

        return result;
    }

}
