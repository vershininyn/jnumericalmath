package dev.projects.math.optimization.constrained.barrier.base;

import dev.projects.math.optimization.constrained.barrier.impl.equalities.AbsBridgeScalarDifferentiableNestedFunction;
import dev.projects.math.optimization.constrained.barrier.impl.equalities.MaxBridgeScalarDifferentiableFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
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
                                  IScalarDifferentiableFunction nestedFunction) throws LoggableException {

        if (nestedFunction == null) throw new LoggableException("Unacceptable nested function");

        if (kIncreaseStepCoefficient <= 1.0) throw new LoggableException("Unacceptable a increase step coefficient");

        this.nestedFunction = nestedFunction;
        this.kCoefficient = kInitialCoefficient;
        this.kIncreaseStepCoefficient = kIncreaseStepCoefficient;
    }

    public AbstractBarrierBuilder addEqualityConstraint(IConstrainedDifferentiableFunction function) throws LoggableException {
        equalityFunctionsSet.add(new AbsBridgeScalarDifferentiableNestedFunction(function));

        return this;
    }

    public AbstractBarrierBuilder addInequalityConstraint(IConstrainedDifferentiableFunction function) throws LoggableException {
        inequalityFunctionsSet.add(new MaxBridgeScalarDifferentiableFunction(function));

        return this;
    }

    public IBarrierFunction build() throws LoggableException {
        if (getEqualityFunctionsSet().isEmpty() && getInequalityFunctionsSet().isEmpty())
            throw new LoggableException("A equality and inequality functions set\'s can\'t be empty at the same time");

        return actualBuild();
    }

    protected abstract IBarrierFunction actualBuild() throws LoggableException;
}
