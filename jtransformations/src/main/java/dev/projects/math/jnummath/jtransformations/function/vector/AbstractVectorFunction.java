package dev.projects.math.jnummath.jtransformations.function.vector;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.AbstractFunction;
import dev.projects.utils.exception.LoggableException;

public class AbstractVectorFunction extends AbstractFunction<DenseVector,DenseVector> {
    public AbstractVectorFunction(int variableCount)   {
        super(variableCount);
    }

    @Override
    protected DenseVector actualComputeOutput()   {
        return null;
    }
}
