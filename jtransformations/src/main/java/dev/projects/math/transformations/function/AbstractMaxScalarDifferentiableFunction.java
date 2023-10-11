package dev.projects.math.transformations.function;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.differentiale.AbstractScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public abstract class AbstractMaxScalarDifferentiableFunction
        extends AbstractScalarDifferentiableFunction {

    public AbstractMaxScalarDifferentiableFunction(int variablesCount) throws LoggableException {
        super(variablesCount);
    }

    public AbstractMaxScalarDifferentiableFunction(DenseVector variables) throws LoggableException {
        super(variables);
    }

    @FunctionalInterface
    protected interface UnaryOutputOperator {
        double computeOutput(int index, double value);
    }

    protected double[] computeMaxEntry(UnaryOutputOperator operator) throws LoggableException {
        DenseVector vars = getVariables();

        int maxIndex = 0;
        double maxValue = Double.NEGATIVE_INFINITY;

        for(int index=0; index < getVariablesCount(); index++) {
            double value = operator.computeOutput(index+1, vars.getValue(index));

            if (value > maxValue) {
                maxValue = value;
                maxIndex = index;
            }
        }

        return new double[]{maxIndex, maxValue};
    }

}
