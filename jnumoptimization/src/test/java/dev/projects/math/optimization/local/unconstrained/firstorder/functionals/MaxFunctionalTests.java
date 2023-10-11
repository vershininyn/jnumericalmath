package dev.projects.math.optimization.local.unconstrained.firstorder.functionals;

import dev.projects.math.datasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.optimization.local.unconstrained.firstorder.AbstractFunctionalTests;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.math.transformations.model.IScalarDifferentiableModel;
import dev.projects.math.transformations.model.functionals.impl.MaxFunctional;
import dev.projects.utils.exception.LoggableException;

public class MaxFunctionalTests extends AbstractFunctionalTests {
    @Override
    protected IScalarDifferentiableFunction getFunction(OneDimensionalSupervisedDataSet dataSet,
                                                        IScalarDifferentiableModel model) throws LoggableException {
        return new MaxFunctional(dataSet, model);
    }
}
