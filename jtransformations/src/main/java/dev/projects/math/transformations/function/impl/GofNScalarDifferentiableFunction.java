package dev.projects.math.transformations.function.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.AbstractMaxScalarDifferentiableFunction;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class GofNScalarDifferentiableFunction
        extends AbstractMaxScalarDifferentiableFunction
        implements IScalarDifferentiableFunction {
    public GofNScalarDifferentiableFunction(int variablesCount) throws LoggableException {
        super(variablesCount);
    }

    public GofNScalarDifferentiableFunction(DenseVector variables) throws LoggableException {
        super(variables);
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
        double[] maxEntry = computeMaxEntry((index,value) -> value);
        double sum = 0.0;
        DenseVector vars = getVariables();

        for(int index= 0; index < getVariablesCount(); index++){
            sum += vars.getValue(index);
        }

        return maxEntry[1]*getVariablesCount() - sum;
    }

    @Override
    protected DenseVector actualComputeGradient() throws LoggableException {
        double[] maxEntry = computeMaxEntry((index,value) -> value);

        int varsCount = getVariablesCount(),
                index = (int)maxEntry[0];

        return DenseVector.getInstance(varsCount, 0.0)
                .setValue(index, varsCount)
                .substract(DenseVector.getInstance(varsCount, 1.0));
    }

    @Override
    public String getName() {
        return "max{x[k],k=1,...,N}*N - sum{x[k],k=1,...,N}";
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        return new GofNScalarDifferentiableFunction(getVariables().deepCopy());
    }
}
