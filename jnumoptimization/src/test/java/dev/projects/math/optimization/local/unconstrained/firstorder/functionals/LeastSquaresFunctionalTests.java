package dev.projects.math.optimization.local.unconstrained.firstorder.functionals;

import dev.projects.math.datasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.optimization.local.unconstrained.firstorder.AbstractFunctionalTests;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.math.transformations.model.IScalarDifferentiableModel;
import dev.projects.math.transformations.model.functionals.impl.LeastSquaresFunctional;
import dev.projects.utils.exception.LoggableException;

public class LeastSquaresFunctionalTests extends AbstractFunctionalTests {
    @Override
    protected IScalarDifferentiableFunction getFunction(OneDimensionalSupervisedDataSet dataSet,
                                                        IScalarDifferentiableModel model) throws LoggableException {
        return new LeastSquaresFunctional(dataSet, model);
    }
}
