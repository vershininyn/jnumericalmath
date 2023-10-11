package dev.projects.math.optimization.constrained.barrier.impl.equalities;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.BridgeScalarDifferentiableFunction;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class MaxBridgeScalarDifferentiableFunction
            extends BridgeScalarDifferentiableFunction
            implements IScalarDifferentiableFunction {

    public MaxBridgeScalarDifferentiableFunction(IScalarDifferentiableFunction nestedFunction) throws LoggableException {
        super(nestedFunction);
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
        return Math.max(0.0, getNestedFunction().computeOutput());
    }

    @Override
    protected DenseVector actualComputeGradient() throws LoggableException {
        double out = getNestedFunction().computeOutput();

        if (out > 0.0) {
            return getNestedFunction().computeGradient();
        } else {
            return DenseVector.getInstance(getNestedFunction().getVariablesCount(), 0.0);
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        return new MaxBridgeScalarDifferentiableFunction((IScalarDifferentiableFunction)getNestedFunction().deepCopy());
    }
}
