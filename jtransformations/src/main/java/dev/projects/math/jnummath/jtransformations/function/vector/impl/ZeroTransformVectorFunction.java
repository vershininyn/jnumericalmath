package dev.projects.math.jnummath.jtransformations.function.vector.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.vector.AbstractVectorFunction;
import dev.projects.math.jnummath.jtransformations.function.vector.IVectorFunction;
import dev.projects.utils.exception.LoggableException;

public class ZeroTransformVectorFunction
        extends AbstractVectorFunction
        implements IVectorFunction {
    public ZeroTransformVectorFunction(int variableCount)   {
        super(variableCount);
    }

    @Override
    public IFunction<DenseVector, DenseVector> deepCopy()   {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
