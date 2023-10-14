package dev.projects.math.jnummath.jtransformations.function.vector.differentiable;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.IDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.vector.IVectorFunction;

public interface IVectorDifferentiableFunction extends IVectorFunction,
        IDifferentiableFunction<DenseVector, DenseVector> {

}
