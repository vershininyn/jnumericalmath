package dev.projects.math.jnummath.jtransformations.function.scalar.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.AbstractMaxScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class MxCScalarDifferentiableFunction
        extends AbstractMaxScalarDifferentiableFunction
        implements IScalarDifferentiableFunction {
    public MxCScalarDifferentiableFunction(int variablesCount)   {
        super(variablesCount);
    }

    public MxCScalarDifferentiableFunction(DenseVector variables)   {
        super(variables);
    }

    @Override
    protected Double actualComputeOutput()   {
        return computeMaxEntry((index,value) -> Math.pow(index,3.0)*Math.abs(value))[1];
    }

    @Override
    protected DenseVector actualComputeGradient()   {
        double[] maxEntry = computeMaxEntry((index,value) -> Math.pow(index,3.0)*Math.abs(value));
        int index = (int)maxEntry[0];
        DenseVector vars = getVariables();

        return DenseVector.getInstance(getInputDimensionSize(), 0.0)
                .setValue(index, Math.signum(vars.getValue(index))*Math.pow(index+1,3.0));
    }

    @Override
    public String getName() {
        return "max{abs(x[k]*k^3),k=1,...,N}";
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
        return new MxCScalarDifferentiableFunction(getVariables().deepCopy());
    }
}
