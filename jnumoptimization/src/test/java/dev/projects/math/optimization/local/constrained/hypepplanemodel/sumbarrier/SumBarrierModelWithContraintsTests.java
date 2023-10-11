package dev.projects.math.optimization.local.constrained.hypepplanemodel.sumbarrier;

import dev.projects.math.datasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.optimization.local.constrained.hypepplanemodel.AbstractModelWithConstraintsTests;
import dev.projects.math.optimization.constrained.barrier.base.AbstractBarrierBuilder;
import dev.projects.math.optimization.constrained.barrier.impl.SumBarrierFunction;
import dev.projects.math.transformations.model.IScalarDifferentiableModel;
import dev.projects.utils.exception.LoggableException;

public abstract class SumBarrierModelWithContraintsTests
            extends AbstractModelWithConstraintsTests {
    @Override
    protected AbstractBarrierBuilder getBarrierFunction(double kIntialCoefficient,
                                                        double kIncreaseStepCoefficient,
                                                        OneDimensionalSupervisedDataSet dataSet,
                                                        IScalarDifferentiableModel model) throws LoggableException {
        return new SumBarrierFunction.SumBarrierFunctionBuilder(10.0,
                1.0005,
                getNestedFunction(dataSet, model));
    }

    protected abstract IScalarDifferentiableModel getNestedFunction(OneDimensionalSupervisedDataSet dataSet,
                                                                    IScalarDifferentiableModel model) throws LoggableException;
}
