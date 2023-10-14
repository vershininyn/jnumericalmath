package dev.projects.math.jnummath.jnumoptimization.constrained.barrier.impl.equalities;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.BridgeScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class MaxBridgeScalarDifferentiableFunction
            extends BridgeScalarDifferentiableFunction
            implements IScalarDifferentiableFunction {

    public MaxBridgeScalarDifferentiableFunction(IScalarDifferentiableFunction nestedFunction)   {
        super(nestedFunction);
    }

    @Override
    protected Double actualComputeOutput()   {
        return Math.max(0.0, getNestedFunction().computeOutput());
    }

    @Override
    protected DenseVector actualComputeGradient()   {
        double out = getNestedFunction().computeOutput();

        if (out > 0.0) {
            return getNestedFunction().computeGradient();
        } else {
            return DenseVector.getInstance(getNestedFunction().getInputDimensionSize(), 0.0);
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
        return new MaxBridgeScalarDifferentiableFunction((IScalarDifferentiableFunction)getNestedFunction().deepCopy());
    }
}
