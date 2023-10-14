package dev.projects.math.jnummath.jtransformations.function;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.AbstractScalarDifferentiableFunction;

public abstract class AbstractMaxScalarDifferentiableFunction
        extends AbstractScalarDifferentiableFunction {

    public AbstractMaxScalarDifferentiableFunction(int variablesCount)   {
        super(variablesCount);
    }

    public AbstractMaxScalarDifferentiableFunction(DenseVector variables)   {
        super(variables);
    }

    @FunctionalInterface
    protected interface UnaryOutputOperator {
        double computeOutput(int index, double value);
    }

    protected double[] computeMaxEntry(UnaryOutputOperator operator)   {
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
