package dev.projects.math.optimization.unconstrained.local.directionsearch.zeroorder;

import dev.projects.math.linalgebra.DenseMatrix;
import dev.projects.utils.exception.LoggableException;

@FunctionalInterface
public interface IZeroOrderMatrixTransformationOperator {
    Object[] transformMatrix(DenseMatrix metricMatrix) throws LoggableException;
}
