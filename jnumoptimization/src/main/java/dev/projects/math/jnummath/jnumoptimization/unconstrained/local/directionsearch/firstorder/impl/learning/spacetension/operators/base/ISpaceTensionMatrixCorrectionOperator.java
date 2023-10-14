package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseMatrix;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.transformations.ICloneable;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public interface ISpaceTensionMatrixCorrectionOperator extends ICloneable<ISpaceTensionMatrixCorrectionOperator> {
    DenseMatrix correctMatrix(DenseVector currentPoint,
                              DenseVector correctionGradient,
                              DenseVector internalDirection,
                              DenseMatrix matrix,
                              IScalarDifferentiableFunction function)  ;

    String getAlgorithmName();
}
