package dev.projects.math.optimization.unconstrained.local.algorithms.interpolation.impl;

import dev.projects.math.optimization.unconstrained.local.algorithms.interpolation.ICubicInterpolationAlgorithm;
import dev.projects.utils.exception.LoggableException;

public class LearningCubicStepInterpolationAlgorithm implements ICubicInterpolationAlgorithm {

    private final double epsilon = 1.0E-4;

    @Override
    public double computeInterpolation(double lastStep,
                                       double currentStep,
                                       double lastScalar,
                                       double currentScalar,
                                       double lastFunctionOutput,
                                       double currentFunctionOutput) {
        double l1 = currentStep - lastStep,
                b = lastScalar,
                z = 3.0*((lastFunctionOutput - currentFunctionOutput)/l1) + lastScalar + currentScalar,
                c = (-1.0)*((lastScalar + z)/l1),
                d = (lastScalar+currentScalar+2.0*z)/3.0/l1/l1,
                lm = 0.0;

        double g = 3.0*d*b;

        if (Math.abs(g) < (c*c*epsilon)) {
            lm = -b/(2.0*c);
        } else {
            double coeff = (g >= c*c) ? g - c*c : 1.0E-50;

            lm = (-c + Math.sqrt(coeff))/(3.0*d + 0.0000001);
        }

        return lm + lastStep;
    }

    @Override
    public ICubicInterpolationAlgorithm deepCopy() throws LoggableException {
        return new LearningCubicStepInterpolationAlgorithm();
    }
}
