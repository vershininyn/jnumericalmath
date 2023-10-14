package dev.projects.math.jnummath.jnumoptimization.constrained.barrier.base;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.BridgeScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public abstract class AbstractConstrainedScalarDifferentiableFunction
                    extends BridgeScalarDifferentiableFunction {

    @Getter
    @Setter
    private double coefficient;

    public AbstractConstrainedScalarDifferentiableFunction(@NonNull IScalarDifferentiableFunction nestedFunction, double coefficient)   {
        super(nestedFunction);

        setNestedFunction(nestedFunction);
        setCoefficient(coefficient);
    }

    @Override
    protected Double actualComputeOutput()   {
        return getWeigthOfNestedFunction()*getNestedFunction().computeOutput() - getWeigthOfCoefficient()*getCoefficient();
    }

    @Override
    protected DenseVector actualComputeGradient()   {
        DenseVector gradient = getNestedFunction().computeGradient();

        return gradient.multiplyByCoefficient(getWeigthOfNestedFunction());
    }

    protected abstract double getWeigthOfNestedFunction();

    protected abstract double getWeigthOfCoefficient();
}
