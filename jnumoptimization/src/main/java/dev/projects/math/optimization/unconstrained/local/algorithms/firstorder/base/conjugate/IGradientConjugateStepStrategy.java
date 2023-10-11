package dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.conjugate;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.linalgebra.ICloneable;
import dev.projects.utils.exception.LoggableException;

public interface IGradientConjugateStepStrategy extends ICloneable<IGradientConjugateStepStrategy> {
    double computeStep(DenseVector currentGradient, DenseVector lastGradient) throws LoggableException;
}
