package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.conjugate.stepStrategy;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.conjugate.IGradientConjugateStepStrategy;
import dev.projects.utils.exception.LoggableException;

public class FletcherReevesStepStrategy implements IGradientConjugateStepStrategy {
    @Override
    public double computeStep(DenseVector currentGradient, DenseVector lastGradient)   {
        return (currentGradient.getInnerProduct(currentGradient))/(lastGradient.getInnerProduct(lastGradient));
    }

    @Override
    public IGradientConjugateStepStrategy deepCopy()   {
        return new FletcherReevesStepStrategy();
    }
}
