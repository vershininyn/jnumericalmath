package dev.projects.math.optimization.local.constrained.hypepplanemodel.maxbarrier;

import dev.projects.math.datasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.transformations.model.IScalarDifferentiableModel;
import dev.projects.utils.exception.LoggableException;

public class MaxBarrierHyperPlaneModelWithConstraintsTests
            extends MaxBarrierModelWithConstraintsTests{
    @Override
    protected IScalarDifferentiableModel getNestedFunction(OneDimensionalSupervisedDataSet dataSet,
                                                           IScalarDifferentiableModel model) throws LoggableException {
        return model;
    }
}
