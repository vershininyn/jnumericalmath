package dev.projects.math.transformations.function.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.differentiale.AbstractScalarDifferentiableFunction;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class SchwefelScalarDifferentiableFunction
                extends AbstractScalarDifferentiableFunction
                implements IScalarDifferentiableFunction {

    public SchwefelScalarDifferentiableFunction(int variablesCount) throws LoggableException {
        super(variablesCount);
    }

    public SchwefelScalarDifferentiableFunction(DenseVector variables) throws LoggableException {
        super(variables);
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
        DenseVector vars = getVariables();
        double out = 0.0;

        for(int index= 0; index < getVariablesCount(); index++) {
            double value = vars.getValue(index);
            out += value*Math.sin(Math.pow(value, 2.0));
        }

        return (1.0/getVariablesCount())*out;
    }

    @Override
    protected DenseVector actualComputeGradient() throws LoggableException {
        DenseVector vars = getVariables(),
                gradient = DenseVector.getInstance(getVariablesCount(), 0.0);

        double coeff = 1.0/getVariablesCount();

        for(int index= 0; index < getVariablesCount(); index++){
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
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        return new SchwefelScalarDifferentiableFunction(getVariables().deepCopy());
    }

}
