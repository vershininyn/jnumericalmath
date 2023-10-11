package dev.projects.math.transformations.function.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.AbstractMaxScalarDifferentiableFunction;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class MaxQScalarDifferentiableFunction
                extends AbstractMaxScalarDifferentiableFunction
                implements IScalarDifferentiableFunction {

    public MaxQScalarDifferentiableFunction(int variablesCount) throws LoggableException {
        super(variablesCount);
    }

    public MaxQScalarDifferentiableFunction(DenseVector variables) throws LoggableException {
        super(variables);
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
        return computeMaxEntry((index,value) -> Math.pow(value, 2.0))[1];
    }

    @Override
    protected DenseVector actualComputeGradient() throws LoggableException {
        double[] maxEntry = computeMaxEntry((index,value) -> Math.pow(value, 2.0));
        int index = (int)maxEntry[0];

        return DenseVector.getInstance(getVariablesCount(), 0.0).setValue(index, 2.0*getVariables().getValue(index));
    }

    @Override
    public String getName() {
        return "max{x[k]^2, k=1,...,N}";
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        return new MaxQScalarDifferentiableFunction(getVariables().deepCopy());
    }
}
