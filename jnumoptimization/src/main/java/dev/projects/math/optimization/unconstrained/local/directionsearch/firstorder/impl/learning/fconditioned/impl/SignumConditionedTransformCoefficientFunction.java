package dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.fconditioned.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.fconditioned.FirstOrderFunctionallyConditionedMultistepDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.fconditioned.IFunctionallyConditionedTransformCoefficientFunction;
import dev.projects.utils.exception.LoggableException;

import java.util.Objects;

public class SignumConditionedTransformCoefficientFunction
        implements IFunctionallyConditionedTransformCoefficientFunction {
    private int primeHash = 151;

    @Override
    public double computeTransformCoefficient(DenseVector correctionGradient, DenseVector pVector) throws LoggableException {
        double coeff = correctionGradient.getInnerProduct(pVector);

        if (coeff == 0.0) {
            return 0.0;
        }

        return 0.5*(Math.signum(-1.0*coeff) + 1.0);
    }

    @Override
    public String getName() {
        return "Signum";
    }

    @Override
    public IFunctionallyConditionedTransformCoefficientFunction deepCopy() throws LoggableException {
        return new SignumConditionedTransformCoefficientFunction();
    }

    @Override
    public int hashCode() {
        return Objects.hash(primeHash);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        SignumConditionedTransformCoefficientFunction
                fcTransformFunction = (SignumConditionedTransformCoefficientFunction) (obj);

        return hashCode() == fcTransformFunction.hashCode();
    }
}
