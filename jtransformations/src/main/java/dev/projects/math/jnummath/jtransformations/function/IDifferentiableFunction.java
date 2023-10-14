package dev.projects.math.jnummath.jtransformations.function;

import dev.projects.math.jnummath.jkernel.ICountable;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;

/**
 * @author vyn
 */
public interface IDifferentiableFunction<TInputData extends ICountable, TOutputResult>
        extends IFunction<TInputData, TOutputResult> {
    long getGradientComputeCount();

    DenseVector getCachedGradient();

    DenseVector computeGradient();

    DenseVector computeGradient(TInputData pVariables);
}
