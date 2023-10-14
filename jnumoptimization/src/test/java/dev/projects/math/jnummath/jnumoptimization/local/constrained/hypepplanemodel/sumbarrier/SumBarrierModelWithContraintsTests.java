package dev.projects.math.jnummath.jnumoptimization.local.constrained.hypepplanemodel.sumbarrier;

import dev.projects.math.jnummath.jdatasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.base.AbstractBarrierBuilder;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.impl.SumBarrierFunction;
import dev.projects.math.jnummath.jnumoptimization.local.constrained.hypepplanemodel.AbstractModelWithConstraintsTests;
import dev.projects.math.jnummath.jtransformations.model.IScalarDifferentiableModel;
import dev.projects.utils.exception.LoggableException;

public abstract class SumBarrierModelWithContraintsTests
            extends AbstractModelWithConstraintsTests {
    @Override
    protected AbstractBarrierBuilder getBarrierFunction(double kIntialCoefficient,
                                                        double kIncreaseStepCoefficient,
                                                        OneDimensionalSupervisedDataSet dataSet,
                                                        IScalarDifferentiableModel model)   {
        return new SumBarrierFunction.SumBarrierFunctionBuilder(10.0,
                1.0005,
                getNestedFunction(dataSet, model));
    }

    protected abstract IScalarDifferentiableModel getNestedFunction(OneDimensionalSupervisedDataSet dataSet,
                                                                    IScalarDifferentiableModel model)  ;
}
