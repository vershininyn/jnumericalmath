package dev.projects.math.jnummath.jtransformations.function.scalar.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.AbstractMaxScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class MaxLScalarDifferentiableFunction
        extends AbstractMaxScalarDifferentiableFunction
        implements IScalarDifferentiableFunction {
    public MaxLScalarDifferentiableFunction(int variablesCount)   {
        super(variablesCount);
    }

    public MaxLScalarDifferentiableFunction(DenseVector variables)   {
        super(variables);
    }

    @Override
    protected Double actualComputeOutput()   {
        return computeMaxEntry((index,value) -> Math.abs(value))[1];
    }

    @Override
    protected DenseVector actualComputeGradient()   {
        double[] maxEntry = computeMaxEntry((index,value) -> Math.abs(value));
        int index = (int)maxEntry[0];

        return DenseVector.getInstance(getInputDimensionSize(), 0.0).setValue(index, Math.signum(getVariables().getValue(index)));
    }

    @Override
    public String getName() {
        return "max{abs(x[k]), k=1,...,N}";
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
        return new MaxLScalarDifferentiableFunction(getVariables().deepCopy());
    }
}
