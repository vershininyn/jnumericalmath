package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.conjugate;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.transformations.ICloneable;
import dev.projects.utils.exception.LoggableException;

public interface IGradientConjugateStepStrategy extends ICloneable<IGradientConjugateStepStrategy> {
    double computeStep(DenseVector currentGradient, DenseVector lastGradient)  ;
}
