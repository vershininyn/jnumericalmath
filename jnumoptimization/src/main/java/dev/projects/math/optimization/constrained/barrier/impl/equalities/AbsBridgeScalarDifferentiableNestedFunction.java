package dev.projects.math.optimization.constrained.barrier.impl.equalities;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.BridgeScalarDifferentiableFunction;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class AbsBridgeScalarDifferentiableNestedFunction
            extends BridgeScalarDifferentiableFunction
            implements IScalarDifferentiableFunction {

    public AbsBridgeScalarDifferentiableNestedFunction(IScalarDifferentiableFunction nestedFunction) throws LoggableException {
        super(nestedFunction);
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
        return Math.abs(getNestedFunction().computeOutput());
    }

    @Override
    protected DenseVector actualComputeGradient() throws LoggableException {
        return getNestedFunction().computeGradient().deepCopy().multiplyByCoefficient(Math.signum(getNestedFunction().computeOutput()));
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        return new AbsBridgeScalarDifferentiableNestedFunction((IScalarDifferentiableFunction) getNestedFunction().deepCopy());
    }
}
