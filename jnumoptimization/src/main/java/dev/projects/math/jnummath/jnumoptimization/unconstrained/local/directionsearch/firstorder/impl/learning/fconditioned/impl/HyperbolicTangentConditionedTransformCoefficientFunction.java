package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.fconditioned.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.fconditioned.IFunctionallyConditionedTransformCoefficientFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;

import java.util.Objects;

public class HyperbolicTangentConditionedTransformCoefficientFunction
        implements IFunctionallyConditionedTransformCoefficientFunction {
    @Getter
    private double tanhCoeff = 0.0;

    private int primeHash = 157;

    public HyperbolicTangentConditionedTransformCoefficientFunction(double tanhCoefficient)   {
        if (tanhCoefficient < 0.0) throw new LoggableException("Unacceptable coefficient");

        this.tanhCoeff = tanhCoefficient;
    }

    @Override
    public double computeTransformCoefficient(DenseVector correctionGradient, DenseVector pVector)   {
        double coeff = correctionGradient.getInnerProduct(pVector);

        if (coeff == 0.0) {
            return 0.0;
        }

        return 0.5*(Math.tanh(-1.0*tanhCoeff*coeff) + 1.0);
    }

    @Override
    public String getName() {
        return "0.5*(1.0 + tanh(-1.0*"+tanhCoeff+"*(g1,p)))";
    }

    @Override
    public IFunctionallyConditionedTransformCoefficientFunction deepCopy()   {
        return new HyperbolicTangentConditionedTransformCoefficientFunction(getTanhCoeff());
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

        HyperbolicTangentConditionedTransformCoefficientFunction
                fcTransformFunction = (HyperbolicTangentConditionedTransformCoefficientFunction) (obj);

        return hashCode() == fcTransformFunction.hashCode();
    }
}
