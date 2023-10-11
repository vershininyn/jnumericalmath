package dev.projects.math.transformations.model;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;

/**
 * @author vyn
 */
public interface IScalarDifferentiableModel
        extends IScalarModel,
        IDifferentiableModel<DenseVector, Double, DenseVector, DenseVector>,
        IScalarDifferentiableFunction {

}
