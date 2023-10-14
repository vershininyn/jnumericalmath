package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.zeroorder.impl.learning.orthogonal;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.zeroorder.impl.learning.base.IZeroOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;

public interface IZeroOrderOrthogonalDirectionSearchAlgorithm
                extends IZeroOrderLearningDirectionSearchAlgorithm {

    int getDirectionRow();
    void setDirectionRow(int directionRow)  ;

    DenseVector computeDirection(IScalarFunction function,
                                 DenseVector currentPoint,
                                 int directionRow)  ;
}
