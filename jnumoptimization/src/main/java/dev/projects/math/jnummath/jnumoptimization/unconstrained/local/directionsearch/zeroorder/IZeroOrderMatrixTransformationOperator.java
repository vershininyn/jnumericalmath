package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.zeroorder;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseMatrix;
import dev.projects.utils.exception.LoggableException;

@FunctionalInterface
public interface IZeroOrderMatrixTransformationOperator {
    Object[] transformMatrix(DenseMatrix metricMatrix)  ;
}
