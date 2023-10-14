package dev.projects.math.jnummath.jnumoptimization.local.constrained.functionals;

import dev.projects.math.jnummath.jdatasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.jnummath.jtransformations.model.IScalarDifferentiableModel;
import dev.projects.math.jnummath.jtransformations.model.functionals.IFunctional;
import dev.projects.math.jnummath.jtransformations.model.functionals.impl.LeastSquaresFunctional;
import dev.projects.utils.exception.LoggableException;

public class LeastSquaresModelWithConstraintsTests
            extends AbstractFunctionalWithConstraintsTests {
    @Override
    protected IFunctional getNestedFunctional(OneDimensionalSupervisedDataSet dataSet, IScalarDifferentiableModel model)   {
        return new LeastSquaresFunctional(dataSet, model);
    }

    @Override
    protected double getTaskQMaxStep() {
        return 3.0;
    }
}
