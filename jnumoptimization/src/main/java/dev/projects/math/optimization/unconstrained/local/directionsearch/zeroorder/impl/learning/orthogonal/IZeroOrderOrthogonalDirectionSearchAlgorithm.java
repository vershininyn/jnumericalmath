package dev.projects.math.optimization.unconstrained.local.directionsearch.zeroorder.impl.learning.orthogonal;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.directionsearch.zeroorder.impl.learning.base.IZeroOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;

public interface IZeroOrderOrthogonalDirectionSearchAlgorithm
                extends IZeroOrderLearningDirectionSearchAlgorithm {

    int getDirectionRow();
    void setDirectionRow(int directionRow) throws LoggableException;

    DenseVector computeDirection(IScalarFunction function,
                                 DenseVector currentPoint,
                                 int directionRow) throws LoggableException;
}
