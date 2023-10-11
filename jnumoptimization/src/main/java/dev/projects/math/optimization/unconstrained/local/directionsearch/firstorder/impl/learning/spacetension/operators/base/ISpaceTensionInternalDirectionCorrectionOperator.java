package dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base;

import dev.projects.math.linalgebra.DenseMatrix;
import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.linalgebra.ICloneable;
import dev.projects.utils.exception.LoggableException;

public interface ISpaceTensionInternalDirectionCorrectionOperator
        extends ICloneable<ISpaceTensionInternalDirectionCorrectionOperator> {
    DenseVector correctInternalDirection(DenseVector currentInternalDirection,
                                         DenseVector correctionGradient,
                                         DenseMatrix currentMatrix) throws LoggableException;

    String getAlgorithmName();
}
