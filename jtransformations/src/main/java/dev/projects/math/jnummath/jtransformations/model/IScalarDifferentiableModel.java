package dev.projects.math.jnummath.jtransformations.model;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;

/**
 * @author vyn
 */
public interface IScalarDifferentiableModel
        extends IScalarModel,
        IDifferentiableModel<DenseVector, Double, DenseVector, DenseVector>,
        IScalarDifferentiableFunction {

}
