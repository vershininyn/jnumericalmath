package dev.projects.math.optimization.local.constrained.hypepplanemodel.maxbarrier;

import dev.projects.math.datasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.optimization.local.constrained.hypepplanemodel.AbstractModelWithConstraintsTests;
import dev.projects.math.optimization.constrained.barrier.base.AbstractBarrierBuilder;
import dev.projects.math.optimization.constrained.barrier.impl.MaxBarrierFunction;
import dev.projects.math.transformations.model.IScalarDifferentiableModel;
import dev.projects.utils.exception.LoggableException;

public abstract class MaxBarrierModelWithConstraintsTests
            extends AbstractModelWithConstraintsTests {
    @Override
    protected AbstractBarrierBuilder getBarrierFunction(double kIntialCoefficient,
                                                        double kIncreaseStepCoefficient,
                                                        OneDimensionalSupervisedDataSet dataSet,
                                                        IScalarDifferentiableModel model) throws LoggableException {
        return new MaxBarrierFunction.MaxBarrierFunctionBuilder(kIntialCoefficient,
                kIncreaseStepCoefficient, getNestedFunction(dataSet, model));
    }

    protected abstract IScalarDifferentiableModel getNestedFunction(OneDimensionalSupervisedDataSet dataSet,
                                                                    IScalarDifferentiableModel model) throws LoggableException;
}
