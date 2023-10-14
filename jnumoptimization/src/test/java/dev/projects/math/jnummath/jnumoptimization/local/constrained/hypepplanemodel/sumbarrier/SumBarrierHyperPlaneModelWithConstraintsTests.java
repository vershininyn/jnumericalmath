package dev.projects.math.jnummath.jnumoptimization.local.constrained.hypepplanemodel.sumbarrier;

import dev.projects.math.jnummath.jdatasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.jnummath.jtransformations.model.IScalarDifferentiableModel;
import dev.projects.utils.exception.LoggableException;

public class SumBarrierHyperPlaneModelWithConstraintsTests
            extends SumBarrierModelWithContraintsTests {
    @Override
    protected IScalarDifferentiableModel getNestedFunction(OneDimensionalSupervisedDataSet dataSet,
                                                           IScalarDifferentiableModel model)   {
        return model;
    }
}
