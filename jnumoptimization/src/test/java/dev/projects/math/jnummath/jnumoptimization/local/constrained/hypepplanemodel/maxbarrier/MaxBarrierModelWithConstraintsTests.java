package dev.projects.math.jnummath.jnumoptimization.local.constrained.hypepplanemodel.maxbarrier;

import dev.projects.math.jnummath.jdatasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.base.AbstractBarrierBuilder;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.impl.MaxBarrierFunction;
import dev.projects.math.jnummath.jnumoptimization.local.constrained.hypepplanemodel.AbstractModelWithConstraintsTests;
import dev.projects.math.jnummath.jtransformations.model.IScalarDifferentiableModel;
import dev.projects.utils.exception.LoggableException;

public abstract class MaxBarrierModelWithConstraintsTests
            extends AbstractModelWithConstraintsTests {
    @Override
    protected AbstractBarrierBuilder getBarrierFunction(double kIntialCoefficient,
                                                        double kIncreaseStepCoefficient,
                                                        OneDimensionalSupervisedDataSet dataSet,
                                                        IScalarDifferentiableModel model)   {
        return new MaxBarrierFunction.MaxBarrierFunctionBuilder(kIntialCoefficient,
                kIncreaseStepCoefficient, getNestedFunction(dataSet, model));
    }

    protected abstract IScalarDifferentiableModel getNestedFunction(OneDimensionalSupervisedDataSet dataSet,
                                                                    IScalarDifferentiableModel model)  ;
}
