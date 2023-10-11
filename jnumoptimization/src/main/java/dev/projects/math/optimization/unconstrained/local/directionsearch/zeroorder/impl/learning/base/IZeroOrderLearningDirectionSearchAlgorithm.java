package dev.projects.math.optimization.unconstrained.local.directionsearch.zeroorder.impl.learning.base;

import dev.projects.math.optimization.unconstrained.local.directionsearch.zeroorder.IZeroOrderDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.zeroorder.IZeroOrderMatrixTransformationOperator;
import dev.projects.utils.exception.LoggableException;

public interface IZeroOrderLearningDirectionSearchAlgorithm
                    extends IZeroOrderDirectionSearchAlgorithm {
    Object[] performMetricMatrixTransformation(IZeroOrderMatrixTransformationOperator transformationOperator) throws LoggableException;
}
