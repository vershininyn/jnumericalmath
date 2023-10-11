package dev.projects.math.transformations.function.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.AbstractMaxScalarDifferentiableFunction;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class MaxLScalarDifferentiableFunction
        extends AbstractMaxScalarDifferentiableFunction
        implements IScalarDifferentiableFunction {
    public MaxLScalarDifferentiableFunction(int variablesCount) throws LoggableException {
        super(variablesCount);
    }

    public MaxLScalarDifferentiableFunction(DenseVector variables) throws LoggableException {
        super(variables);
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
        return computeMaxEntry((index,value) -> Math.abs(value))[1];
    }

    @Override
    protected DenseVector actualComputeGradient() throws LoggableException {
        double[] maxEntry = computeMaxEntry((index,value) -> Math.abs(value));
        int index = (int)maxEntry[0];

        return DenseVector.getInstance(getVariablesCount(), 0.0).setValue(index, Math.signum(getVariables().getValue(index)));
    }

    @Override
    public String getName() {
        return "max{abs(x[k]), k=1,...,N}";
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        return new MaxLScalarDifferentiableFunction(getVariables().deepCopy());
    }
}
