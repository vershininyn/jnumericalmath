package dev.projects.math.optimization.constrained.barrier.base;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.BridgeScalarDifferentiableFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public abstract class AbstractConstrainedScalarDifferentiableFunction
                    extends BridgeScalarDifferentiableFunction {

    @Getter
    @Setter
    private double coefficient;

    public AbstractConstrainedScalarDifferentiableFunction(@NonNull IScalarDifferentiableFunction nestedFunction, double coefficient) throws LoggableException {
        super(nestedFunction);

        setNestedFunction(nestedFunction);
        setCoefficient(coefficient);
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
        return getWeigthOfNestedFunction()*getNestedFunction().computeOutput() - getWeigthOfCoefficient()*getCoefficient();
    }

    @Override
    protected DenseVector actualComputeGradient() throws LoggableException {
        DenseVector gradient = getNestedFunction().computeGradient();

        return gradient.multiplyByCoefficient(getWeigthOfNestedFunction());
    }

    protected abstract double getWeigthOfNestedFunction();

    protected abstract double getWeigthOfCoefficient();
}
