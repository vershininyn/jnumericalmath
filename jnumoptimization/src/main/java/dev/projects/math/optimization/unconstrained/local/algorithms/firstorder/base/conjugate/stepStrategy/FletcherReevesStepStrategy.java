package dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.conjugate.stepStrategy;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.conjugate.IGradientConjugateStepStrategy;
import dev.projects.utils.exception.LoggableException;

public class FletcherReevesStepStrategy implements IGradientConjugateStepStrategy {
    @Override
    public double computeStep(DenseVector currentGradient, DenseVector lastGradient) throws LoggableException {
        return (currentGradient.getInnerProduct(currentGradient))/(lastGradient.getInnerProduct(lastGradient));
    }

    @Override
    public IGradientConjugateStepStrategy deepCopy() throws LoggableException {
        return new FletcherReevesStepStrategy();
    }
}
