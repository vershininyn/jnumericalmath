package dev.projects.math.jnummath.jnumoptimization.constrained.barrier.base;

import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.impl.equalities.AbsBridgeScalarDifferentiableNestedFunction;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.impl.equalities.MaxBridgeScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBarrierBuilder {
    @Getter
    private double kCoefficient, kIncreaseStepCoefficient;

    @Getter
    private final IScalarDifferentiableFunction nestedFunction;

    @Getter
    private final List<IScalarDifferentiableFunction> equalityFunctionsSet = new ArrayList<>(),
            inequalityFunctionsSet = new ArrayList<>();

    public AbstractBarrierBuilder(double kInitialCoefficient,
                                  double kIncreaseStepCoefficient,
                                  IScalarDifferentiableFunction nestedFunction)   {

        if (nestedFunction == null) throw new LoggableException("Unacceptable nested function");

        if (kIncreaseStepCoefficient <= 1.0) throw new LoggableException("Unacceptable a increase step coefficient");

        this.nestedFunction = nestedFunction;
        this.kCoefficient = kInitialCoefficient;
        this.kIncreaseStepCoefficient = kIncreaseStepCoefficient;
    }

    public AbstractBarrierBuilder addEqualityConstraint(IConstrainedDifferentiableFunction function)   {
        equalityFunctionsSet.add(new AbsBridgeScalarDifferentiableNestedFunction(function));

        return this;
    }

    public AbstractBarrierBuilder addInequalityConstraint(IConstrainedDifferentiableFunction function)   {
        inequalityFunctionsSet.add(new MaxBridgeScalarDifferentiableFunction(function));

        return this;
    }

    public IBarrierFunction build()   {
        if (getEqualityFunctionsSet().isEmpty() && getInequalityFunctionsSet().isEmpty())
            throw new LoggableException("A equality and inequality functions set\'s can\'t be empty at the same time");

        return actualBuild();
    }

    protected abstract IBarrierFunction actualBuild()  ;
}
