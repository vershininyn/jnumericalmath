package dev.projects.math.optimization.local.constrained.functionals;

import dev.projects.math.datasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.transformations.model.IScalarDifferentiableModel;
import dev.projects.math.transformations.model.functionals.IFunctional;
import dev.projects.math.transformations.model.functionals.impl.MaxFunctional;
import dev.projects.utils.exception.LoggableException;

public class MaxFunctionalWithConstraintsTests
        extends AbstractFunctionalWithConstraintsTests{
    @Override
    protected IFunctional getNestedFunctional(OneDimensionalSupervisedDataSet dataSet, IScalarDifferentiableModel model) throws LoggableException {
        return new MaxFunctional(dataSet, model);
    }

    @Override
    protected double getTaskQMaxStep() {
        return 1.5;
    }
}
