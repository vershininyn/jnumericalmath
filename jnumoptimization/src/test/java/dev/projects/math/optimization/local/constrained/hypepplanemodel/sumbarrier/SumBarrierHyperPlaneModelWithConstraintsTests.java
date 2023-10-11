package dev.projects.math.optimization.local.constrained.hypepplanemodel.sumbarrier;

import dev.projects.math.datasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.transformations.model.IScalarDifferentiableModel;
import dev.projects.utils.exception.LoggableException;

public class SumBarrierHyperPlaneModelWithConstraintsTests
            extends SumBarrierModelWithContraintsTests {
    @Override
    protected IScalarDifferentiableModel getNestedFunction(OneDimensionalSupervisedDataSet dataSet,
                                                           IScalarDifferentiableModel model) throws LoggableException {
        return model;
    }
}
