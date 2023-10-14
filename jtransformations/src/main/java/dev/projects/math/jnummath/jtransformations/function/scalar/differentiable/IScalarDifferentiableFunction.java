package dev.projects.math.jnummath.jtransformations.function.scalar.differentiable;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;
import dev.projects.math.jnummath.jtransformations.function.IDifferentiableFunction;

public interface IScalarDifferentiableFunction
        extends IScalarFunction,
        IDifferentiableFunction<DenseVector,Double> {

}
