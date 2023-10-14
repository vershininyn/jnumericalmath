package dev.projects.math.jnummath.jtransformations.function.scalar.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.AbstractScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class SchwefelScalarDifferentiableFunction
                extends AbstractScalarDifferentiableFunction
                implements IScalarDifferentiableFunction {

    public SchwefelScalarDifferentiableFunction(int variablesCount)   {
        super(variablesCount);
    }

    public SchwefelScalarDifferentiableFunction(DenseVector variables)   {
        super(variables);
    }

    @Override
    protected Double actualComputeOutput()   {
        DenseVector vars = getVariables();
        double out = 0.0;

        for(int index = 0; index < getInputDimensionSize(); index++) {
            double value = vars.getValue(index);
            out += value*Math.sin(Math.pow(value, 2.0));
        }

        return (1.0/ getInputDimensionSize())*out;
    }

    @Override
    protected DenseVector actualComputeGradient()   {
        DenseVector vars = getVariables(),
                gradient = DenseVector.getInstance(getInputDimensionSize(), 0.0);

        double coeff = 1.0/ getInputDimensionSize();

        for(int index = 0; index < getInputDimensionSize(); index++){
            double varSquare = Math.pow(vars.getValue(index), 2.0),
                    derivate = (coeff)*(Math.sin(varSquare) + 2.0*varSquare*Math.cos(varSquare));

            gradient.setValue(index, derivate);
        }

        return gradient;
    }

    @Override
    public String getName() {
        return "(1.0/N)*sum{x[k]*sin(x[k]^2),k=1,...,N}";
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
        return new SchwefelScalarDifferentiableFunction(getVariables().deepCopy());
    }

}
