package dev.projects.math.optimization.constrained.barrier.base;

import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public interface IConstrainedDifferentiableFunction extends IScalarDifferentiableFunction {
    double getCoefficient();
    void setCoefficient(double coefficient);

    IScalarDifferentiableFunction getNestedFunction() throws LoggableException;
    void setNestedFunction(IScalarDifferentiableFunction nestedFunction) throws LoggableException;
}
