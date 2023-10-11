package dev.projects.math.transformations.function.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.differentiale.AbstractScalarDifferentiableFunction;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class GrivenkaScalarDifferentiableFunction
                extends AbstractScalarDifferentiableFunction
                implements IScalarDifferentiableFunction {
    public GrivenkaScalarDifferentiableFunction(int variablesCount) throws LoggableException {
        super(variablesCount);
    }

    public GrivenkaScalarDifferentiableFunction(DenseVector variables) throws LoggableException {
        super(variables);
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
        DenseVector vars = getVariables();
        double squareOut = 0.0, cosOut = 0.0;

        for(int index= 0; index < getVariablesCount(); index++){
            squareOut += Math.pow(vars.getValue(index),2.0);
        }

        for(int index= 0; index < getVariablesCount(); index++) {
            cosOut += Math.cos((1.0/Math.sqrt(index+1))*vars.getValue(index));
        }

        return 1.0 + (1.0/4000.0)*squareOut - cosOut;
    }

    @Override
    protected DenseVector actualComputeGradient() throws LoggableException {
        DenseVector vars = getVariables(), gradient = DenseVector.getInstance(getVariablesCount(), 0.0);

        for(int index= 0; index < getVariablesCount(); index++){
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
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        return new GrivenkaScalarDifferentiableFunction(getVariables().deepCopy());
    }
}
