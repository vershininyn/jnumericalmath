package dev.projects.math.jnummath.jnumoptimization.constrained.barrier.impl.equalities;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.base.IConstrainedDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.AbstractScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
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

    public SumEqualityConstraintScalarDifferentiableFunction(int variablesCount, double coefficient)   {
        super(variablesCount);

        setCoefficient(coefficient);
    }

    public SumEqualityConstraintScalarDifferentiableFunction(@NonNull DenseVector variables, double coefficient)   {
        super(variables);

        setCoefficient(coefficient);
    }

    @Override
    protected Double actualComputeOutput()   {
        double out = 0.0;
        DenseVector vars = getVariables();

        for(int index = 0; index < vars.getSize(); index++) {
            out += vars.getValue(index);
        }

        return out - getCoefficient();
    }

    @Override
    protected DenseVector actualComputeGradient()   {
        return DenseVector.getInstance(getInputDimensionSize(), 1.0);
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
    public IFunction<DenseVector, Double> deepCopy()   {
        return new SumEqualityConstraintScalarDifferentiableFunction(getVariables().deepCopy(),getCoefficient());
    }
}
