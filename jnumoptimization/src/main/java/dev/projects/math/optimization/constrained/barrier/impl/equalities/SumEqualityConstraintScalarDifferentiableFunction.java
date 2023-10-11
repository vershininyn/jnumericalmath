package dev.projects.math.optimization.constrained.barrier.impl.equalities;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.constrained.barrier.base.IConstrainedDifferentiableFunction;
import dev.projects.math.transformations.function.scalar.differentiale.AbstractScalarDifferentiableFunction;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class SumEqualityConstraintScalarDifferentiableFunction
        extends AbstractScalarDifferentiableFunction
        implements IConstrainedDifferentiableFunction {

    @Getter
    @Setter
    private double coefficient;

    public SumEqualityConstraintScalarDifferentiableFunction(int variablesCount, double coefficient) throws LoggableException {
        super(variablesCount);

        setCoefficient(coefficient);
    }

    public SumEqualityConstraintScalarDifferentiableFunction(@NonNull DenseVector variables, double coefficient) throws LoggableException {
        super(variables);

        setCoefficient(coefficient);
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
        double out = 0.0;
        DenseVector vars = getVariables();

        for(int index = 0; index < vars.getSize(); index++) {
            out += vars.getValue(index);
        }

        return out - getCoefficient();
    }

    @Override
    protected DenseVector actualComputeGradient() throws LoggableException {
        return DenseVector.getInstance(getVariablesCount(), 1.0);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public IScalarDifferentiableFunction getNestedFunction() {
        return null;
    }

    @Override
    public void setNestedFunction(IScalarDifferentiableFunction nestedFunction) {

    }

    @Override
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        return new SumEqualityConstraintScalarDifferentiableFunction(getVariables().deepCopy(),getCoefficient());
    }
}
