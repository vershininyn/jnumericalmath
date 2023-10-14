package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseMatrix;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.transformations.ICloneable;
import dev.projects.utils.exception.LoggableException;

public interface ISpaceTensionInternalDirectionCorrectionOperator
        extends ICloneable<ISpaceTensionInternalDirectionCorrectionOperator> {
    DenseVector correctInternalDirection(DenseVector currentInternalDirection,
                                         DenseVector correctionGradient,
                                         DenseMatrix currentMatrix)  ;

    String getAlgorithmName();
}
