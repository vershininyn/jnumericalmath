package dev.projects.math.jnummath.jnumoptimization.local.constrained.functionals;

import dev.projects.math.jnummath.jdatasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.jnummath.jtransformations.model.IScalarDifferentiableModel;
import dev.projects.math.jnummath.jtransformations.model.functionals.IFunctional;
import dev.projects.math.jnummath.jtransformations.model.functionals.impl.MaxFunctional;
import dev.projects.utils.exception.LoggableException;

public class MaxFunctionalWithConstraintsTests
        extends AbstractFunctionalWithConstraintsTests{
    @Override
    protected IFunctional getNestedFunctional(OneDimensionalSupervisedDataSet dataSet, IScalarDifferentiableModel model)   {
        return new MaxFunctional(dataSet, model);
    }

    @Override
    protected double getTaskQMaxStep() {
        return 1.5;
    }
}
