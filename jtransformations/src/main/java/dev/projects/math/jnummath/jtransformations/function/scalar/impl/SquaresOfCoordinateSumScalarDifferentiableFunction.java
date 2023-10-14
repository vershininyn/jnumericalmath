package dev.projects.math.jnummath.jtransformations.function.scalar.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.AbstractScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class SquaresOfCoordinateSumScalarDifferentiableFunction
                extends AbstractScalarDifferentiableFunction
                implements IScalarDifferentiableFunction {
    public SquaresOfCoordinateSumScalarDifferentiableFunction(int variablesCount)   {
        super(variablesCount);
    }

    public SquaresOfCoordinateSumScalarDifferentiableFunction(DenseVector variables)   {
        this(variables.getSize());

        setVariables(variables);
    }

    @Override
    protected Double actualComputeOutput()   {
        DenseVector vars = getVariables();
        double out= 0.0;

        for(int index = 0; index < getInputDimensionSize(); index++){
            out += Math.pow(vars.getValue(index), 2.0)*Math.pow(index + 1, 6.0);
        }

        return out;
    }

    @Override
    protected DenseVector actualComputeGradient()   {
        DenseVector vars = getVariables(), gradient = DenseVector.getInstance(getInputDimensionSize(), 0.0);

        for(int index = 0; index < getInputDimensionSize(); index++) {
            gradient.setValue(index, 2.0*vars.getValue(index)*Math.pow(index+1, 6.0));
        }

        return gradient;
    }

    @Override
    public String getName() {
        return "sum{x[k]^(2.0)*k^(6.0),k=1,...,N}";
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
        return new SquaresOfCoordinateSumScalarDifferentiableFunction(getVariables().deepCopy());
    }
}
