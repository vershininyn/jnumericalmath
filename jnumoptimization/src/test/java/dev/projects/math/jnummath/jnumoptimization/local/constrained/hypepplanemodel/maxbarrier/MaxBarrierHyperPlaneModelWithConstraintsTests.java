package dev.projects.math.jnummath.jnumoptimization.local.constrained.hypepplanemodel.maxbarrier;

import dev.projects.math.jnummath.jdatasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.jnummath.jtransformations.model.IScalarDifferentiableModel;
import dev.projects.utils.exception.LoggableException;

public class MaxBarrierHyperPlaneModelWithConstraintsTests
            extends MaxBarrierModelWithConstraintsTests{
    @Override
    protected IScalarDifferentiableModel getNestedFunction(OneDimensionalSupervisedDataSet dataSet,
                                                           IScalarDifferentiableModel model)   {
        return model;
    }
}
