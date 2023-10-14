package dev.projects.math.jnummath.jnumoptimization.constrained.barrier.base;

import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public interface IConstrainedDifferentiableFunction extends IScalarDifferentiableFunction {
    double getCoefficient();
    void setCoefficient(double coefficient);

    IScalarDifferentiableFunction getNestedFunction()  ;
    void setNestedFunction(IScalarDifferentiableFunction nestedFunction)  ;
}
