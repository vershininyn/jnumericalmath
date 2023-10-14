package dev.projects.math.jnummath.jtransformations.function.scalar.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.AbstractScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class GrivenkaScalarDifferentiableFunction
                extends AbstractScalarDifferentiableFunction
                implements IScalarDifferentiableFunction {
    public GrivenkaScalarDifferentiableFunction(int variablesCount)   {
        super(variablesCount);
    }

    public GrivenkaScalarDifferentiableFunction(DenseVector variables)   {
        super(variables);
    }

    @Override
    protected Double actualComputeOutput()   {
        DenseVector vars = getVariables();
        double squareOut = 0.0, cosOut = 0.0;

        for(int index = 0; index < getInputDimensionSize(); index++){
            squareOut += Math.pow(vars.getValue(index),2.0);
        }

        for(int index = 0; index < getInputDimensionSize(); index++) {
            cosOut += Math.cos((1.0/Math.sqrt(index+1))*vars.getValue(index));
        }

        return 1.0 + (1.0/4000.0)*squareOut - cosOut;
    }

    @Override
    protected DenseVector actualComputeGradient()   {
        DenseVector vars = getVariables(), gradient = DenseVector.getInstance(getInputDimensionSize(), 0.0);

        for(int index = 0; index < getInputDimensionSize(); index++){
            double value = vars.getValue(index),
                    sqrtValue = 1.0/Math.sqrt(index+1),
                    derivate = (value/2000.0) + (sqrtValue)*Math.sin(sqrtValue*value);

            gradient.setValue(index, derivate);
        }

        return gradient;
    }

    @Override
    public String getName() {
        return "1.0 + (1.0/4000.0)*sum{x[k]^2,k=1,...,N} - sum{cos(x[k]/sqrt(k)),k=1,...,N}";
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
        return new GrivenkaScalarDifferentiableFunction(getVariables().deepCopy());
    }
}
