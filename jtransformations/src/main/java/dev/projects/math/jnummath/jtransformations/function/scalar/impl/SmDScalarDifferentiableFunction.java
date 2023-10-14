package dev.projects.math.jnummath.jtransformations.function.scalar.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.AbstractScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class SmDScalarDifferentiableFunction
                extends AbstractScalarDifferentiableFunction
                implements IScalarDifferentiableFunction {
    public SmDScalarDifferentiableFunction(int variablesCount)   {
        super(variablesCount);
    }

    public SmDScalarDifferentiableFunction(DenseVector variables)   {
        super(variables);
    }

    @Override
    protected Double actualComputeOutput()   {
        DenseVector vars = getVariables();
        double out = 0.0;

        for(int index = 0; index < getInputDimensionSize(); index++){
            out += Math.pow(Math.abs(vars.getValue(index)), index+1)*Math.pow(index+1,3.0);
        }

        return out;
    }

    @Override
    protected DenseVector actualComputeGradient()   {
        DenseVector vars = getVariables(), gradient = DenseVector.getInstance(getInputDimensionSize(), 0.0);

        for(int index = 0; index < getInputDimensionSize(); index++){
            double value = vars.getValue(index),
                    derivate = Math.pow(Math.abs(value),index)*Math.signum(value)*Math.pow(index+1,4.0);

            gradient.setValue(index, derivate);
        }

        return gradient;
    }

    @Override
    public String getName() {
        return "sum{k^3*abs(x[k])^i},k=1,...,N";
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
        return new SmDScalarDifferentiableFunction(getVariables().deepCopy());
    }
}
