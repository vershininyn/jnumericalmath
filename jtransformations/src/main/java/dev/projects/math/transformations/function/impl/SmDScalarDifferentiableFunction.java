package dev.projects.math.transformations.function.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.differentiale.AbstractScalarDifferentiableFunction;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class SmDScalarDifferentiableFunction
                extends AbstractScalarDifferentiableFunction
                implements IScalarDifferentiableFunction {
    public SmDScalarDifferentiableFunction(int variablesCount) throws LoggableException {
        super(variablesCount);
    }

    public SmDScalarDifferentiableFunction(DenseVector variables) throws LoggableException {
        super(variables);
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
        DenseVector vars = getVariables();
        double out = 0.0;

        for(int index= 0; index < getVariablesCount(); index++){
            out += Math.pow(Math.abs(vars.getValue(index)), index+1)*Math.pow(index+1,3.0);
        }

        return out;
    }

    @Override
    protected DenseVector actualComputeGradient() throws LoggableException {
        DenseVector vars = getVariables(), gradient = DenseVector.getInstance(getVariablesCount(), 0.0);

        for(int index= 0; index < getVariablesCount(); index++){
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
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        return new SmDScalarDifferentiableFunction(getVariables().deepCopy());
    }
}
