package dev.projects.math.jnummath.jnumoptimization.constrained.barrier.impl.equalities;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.BridgeScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class AbsBridgeScalarDifferentiableNestedFunction
            extends BridgeScalarDifferentiableFunction
            implements IScalarDifferentiableFunction {

    public AbsBridgeScalarDifferentiableNestedFunction(IScalarDifferentiableFunction nestedFunction)   {
        super(nestedFunction);
    }

    @Override
    protected Double actualComputeOutput()   {
        return Math.abs(getNestedFunction().computeOutput());
    }

    @Override
    protected DenseVector actualComputeGradient()   {
        return getNestedFunction().computeGradient().deepCopy().multiplyByCoefficient(Math.signum(getNestedFunction().computeOutput()));
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
        return new AbsBridgeScalarDifferentiableNestedFunction((IScalarDifferentiableFunction) getNestedFunction().deepCopy());
    }
}
