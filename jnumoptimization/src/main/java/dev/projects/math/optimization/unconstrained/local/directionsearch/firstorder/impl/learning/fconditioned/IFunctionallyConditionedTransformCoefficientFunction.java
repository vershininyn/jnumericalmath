package dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.fconditioned;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.linalgebra.ICloneable;
import dev.projects.utils.exception.LoggableException;

public interface IFunctionallyConditionedTransformCoefficientFunction extends ICloneable<IFunctionallyConditionedTransformCoefficientFunction> {
    double computeTransformCoefficient(DenseVector correctionGradient, DenseVector pVector) throws LoggableException;

    String getName();
}
