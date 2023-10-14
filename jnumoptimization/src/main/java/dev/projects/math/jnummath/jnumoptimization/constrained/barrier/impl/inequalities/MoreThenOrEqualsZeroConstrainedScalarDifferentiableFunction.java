package dev.projects.math.jnummath.jnumoptimization.constrained.barrier.impl.inequalities;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.base.AbstractConstrainedScalarDifferentiableFunction;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.base.IConstrainedDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.NonNull;

public class MoreThenOrEqualsZeroConstrainedScalarDifferentiableFunction
        extends AbstractConstrainedScalarDifferentiableFunction
        implements IConstrainedDifferentiableFunction {

    public MoreThenOrEqualsZeroConstrainedScalarDifferentiableFunction(@NonNull IScalarDifferentiableFunction nestedFunction,
                                                                       double coefficient)   {
        super(nestedFunction, coefficient);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    protected double getWeigthOfNestedFunction() {
        return -1.0;
    }

    @Override
    protected double getWeigthOfCoefficient() {
        return -1.0;
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
        return new MoreThenOrEqualsZeroConstrainedScalarDifferentiableFunction((IScalarDifferentiableFunction) getNestedFunction().deepCopy(), getCoefficient());
    }
}
