package dev.projects.math.optimization.constrained.barrier.impl.inequalities;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.constrained.barrier.base.AbstractConstrainedScalarDifferentiableFunction;
import dev.projects.math.optimization.constrained.barrier.base.IConstrainedDifferentiableFunction;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.NonNull;

public class MoreThenOrEqualsZeroConstrainedScalarDifferentiableFunction
        extends AbstractConstrainedScalarDifferentiableFunction
        implements IConstrainedDifferentiableFunction {

    public MoreThenOrEqualsZeroConstrainedScalarDifferentiableFunction(@NonNull IScalarDifferentiableFunction nestedFunction,
                                                                       double coefficient) throws LoggableException {
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
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        return new MoreThenOrEqualsZeroConstrainedScalarDifferentiableFunction((IScalarDifferentiableFunction) getNestedFunction().deepCopy(), getCoefficient());
    }
}
