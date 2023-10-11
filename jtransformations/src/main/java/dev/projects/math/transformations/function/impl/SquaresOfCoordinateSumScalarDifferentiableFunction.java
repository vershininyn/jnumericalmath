package dev.projects.math.transformations.function.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.differentiale.AbstractScalarDifferentiableFunction;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class SquaresOfCoordinateSumScalarDifferentiableFunction
                extends AbstractScalarDifferentiableFunction
                implements IScalarDifferentiableFunction {
    public SquaresOfCoordinateSumScalarDifferentiableFunction(int variablesCount) throws LoggableException {
        super(variablesCount);
    }

    public SquaresOfCoordinateSumScalarDifferentiableFunction(DenseVector variables) throws LoggableException {
        this(variables.getSize());

        setVariables(variables);
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
        DenseVector vars = getVariables();
        double out= 0.0;

        for(int index= 0; index < getVariablesCount(); index++){
            out += Math.pow(vars.getValue(index), 2.0)*Math.pow(index + 1, 6.0);
        }

        return out;
    }

    @Override
    protected DenseVector actualComputeGradient() throws LoggableException {
        DenseVector vars = getVariables(), gradient = DenseVector.getInstance(getVariablesCount(), 0.0);

        for(int index= 0; index < getVariablesCount(); index++) {
            gradient.setValue(index, 2.0*vars.getValue(index)*Math.pow(index+1, 6.0));
        }

        return gradient;
    }

    @Override
    public String getName() {
        return "sum{x[k]^(2.0)*k^(6.0),k=1,...,N}";
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        return new SquaresOfCoordinateSumScalarDifferentiableFunction(getVariables().deepCopy());
    }
}
