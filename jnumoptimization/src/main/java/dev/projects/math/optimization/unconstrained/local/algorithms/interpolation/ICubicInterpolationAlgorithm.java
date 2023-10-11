package dev.projects.math.optimization.unconstrained.local.algorithms.interpolation;

import dev.projects.math.linalgebra.ICloneable;

public interface ICubicInterpolationAlgorithm extends ICloneable<ICubicInterpolationAlgorithm> {
    double computeInterpolation(double lastStep,
                                double currentStep,
                                double lastScalar,
                                double currentScalar,
                                double lastFunctionOutput,
                                double currentFunctionOutput);
}
