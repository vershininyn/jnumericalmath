package dev.projects.math.jnummath.jtransformations.function.scalar.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.AbstractMaxScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class MaxQScalarDifferentiableFunction
                extends AbstractMaxScalarDifferentiableFunction
                implements IScalarDifferentiableFunction {

    public MaxQScalarDifferentiableFunction(int variablesCount)   {
        super(variablesCount);
    }

    public MaxQScalarDifferentiableFunction(DenseVector variables)   {
        super(variables);
    }

    @Override
    protected Double actualComputeOutput()   {
        return computeMaxEntry((index,value) -> Math.pow(value, 2.0))[1];
    }

    @Override
    protected DenseVector actualComputeGradient()   {
        double[] maxEntry = computeMaxEntry((index,value) -> Math.pow(value, 2.0));
        int index = (int)maxEntry[0];

        return DenseVector.getInstance(getInputDimensionSize(), 0.0).setValue(index, 2.0*getVariables().getValue(index));
    }

    @Override
    public String getName() {
        return "max{x[k]^2, k=1,...,N}";
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
        return new MaxQScalarDifferentiableFunction(getVariables().deepCopy());
    }
}
