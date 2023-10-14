package dev.projects.math.jnummath.jdatasources.unsupervised.generators;

import dev.projects.math.jnummath.jdatasources.unsupervised.MultidimensionalUnsupervisedDataSet;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class UnsupervisedDataSetGeneratorByDistribution {

    public static MultidimensionalUnsupervisedDataSet generate(int totalCount,
                                                               int variablesCount,
                                                               double alpha,
                                                               double beta,
                                                               IDistributionOperator operator)   {
        if (totalCount <= 0) throw new LoggableException("Unacceptable totalCount");
        if (alpha >= beta) throw new LoggableException("Unacceptable alpha and (or) beta");
        if (variablesCount <= 0) throw new LoggableException("Unacceptable variables count");
        if (operator == null) throw new LoggableException("Unacceptable operator");

        MultidimensionalUnsupervisedDataSet dataSet = new MultidimensionalUnsupervisedDataSet();

        for (int index = 0; index < totalCount; index++) {
            List<Double> uniformVector = getUniformVector(variablesCount, alpha, beta),
                    dataRow = operator.generate(uniformVector);

            dataSet.add(dataRow);
        }

        return dataSet;
    }

    private static List<Double> getUniformVector(int count, double alpha, double beta) {
        return (new Random(System.currentTimeMillis()))
                .doubles(count, alpha, beta)
                .boxed()
                .collect(Collectors.toList());
    }
}
