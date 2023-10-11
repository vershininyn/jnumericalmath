package dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base;

import dev.projects.math.linalgebra.DenseMatrix;
import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.linalgebra.ICloneable;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public interface ISpaceTensionMatrixCorrectionOperator extends ICloneable<ISpaceTensionMatrixCorrectionOperator> {
    DenseMatrix correctMatrix(DenseVector currentPoint,
                              DenseVector correctionGradient,
                              DenseVector internalDirection,
                              DenseMatrix matrix,
                              IScalarDifferentiableFunction function) throws LoggableException;

    String getAlgorithmName();
}
