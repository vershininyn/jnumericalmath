package dev.projects.math.jnummath.jtransformations.function.vector.differentiable;

import dev.projects.math.jnummath.jtransformations.function.vector.AbstractVectorFunction;
import dev.projects.utils.exception.LoggableException;

public abstract class AbstractVectorDifferentiableFunction extends AbstractVectorFunction {
    public AbstractVectorDifferentiableFunction(int variableCount)   {
        super(variableCount);
    }
}
