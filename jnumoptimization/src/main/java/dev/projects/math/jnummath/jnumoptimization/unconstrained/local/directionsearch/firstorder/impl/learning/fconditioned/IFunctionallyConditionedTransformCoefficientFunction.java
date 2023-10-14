package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.fconditioned;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.transformations.ICloneable;
import dev.projects.utils.exception.LoggableException;

public interface IFunctionallyConditionedTransformCoefficientFunction extends ICloneable<IFunctionallyConditionedTransformCoefficientFunction> {
    double computeTransformCoefficient(DenseVector correctionGradient, DenseVector pVector)  ;

    String getName();
}
