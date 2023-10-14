package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.interpolation;

import dev.projects.math.transformations.ICloneable;

public interface ICubicInterpolationAlgorithm extends ICloneable<ICubicInterpolationAlgorithm> {
    double computeInterpolation(double lastStep,
                                double currentStep,
                                double lastScalar,
                                double currentScalar,
                                double lastFunctionOutput,
                                double currentFunctionOutput);
}
