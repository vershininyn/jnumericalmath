package dev.projects.math.transformations.function.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.AbstractMaxScalarDifferentiableFunction;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class MxCScalarDifferentiableFunction
        extends AbstractMaxScalarDifferentiableFunction
        implements IScalarDifferentiableFunction {
    public MxCScalarDifferentiableFunction(int variablesCount) throws LoggableException {
        super(variablesCount);
    }

    public MxCScalarDifferentiableFunction(DenseVector variables) throws LoggableException {
        super(variables);
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
        return computeMaxEntry((index,value) -> Math.pow(index,3.0)*Math.abs(value))[1];
    }

    @Override
    protected DenseVector actualComputeGradient() throws LoggableException {
        double[] maxEntry = computeMaxEntry((index,value) -> Math.pow(index,3.0)*Math.abs(value));
        int index = (int)maxEntry[0];
        DenseVector vars = getVariables();

        return DenseVector.getInstance(getVariablesCount(), 0.0)
                .setValue(index, Math.signum(vars.getValue(index))*Math.pow(index+1,3.0));
    }

    @Override
    public String getName() {
        return "max{abs(x[k]*k^3),k=1,...,N}";
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        return new MxCScalarDifferentiableFunction(getVariables().deepCopy());
    }
}
