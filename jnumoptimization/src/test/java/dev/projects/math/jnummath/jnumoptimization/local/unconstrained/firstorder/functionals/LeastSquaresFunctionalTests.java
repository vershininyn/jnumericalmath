package dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.functionals;

import dev.projects.math.jnummath.jdatasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.AbstractFunctionalTests;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.model.IScalarDifferentiableModel;
import dev.projects.math.jnummath.jtransformations.model.functionals.impl.LeastSquaresFunctional;
import dev.projects.utils.exception.LoggableException;

public class LeastSquaresFunctionalTests extends AbstractFunctionalTests {
    @Override
    protected IScalarDifferentiableFunction getFunctional(OneDimensionalSupervisedDataSet dataSet,
                                                          IScalarDifferentiableModel model)   {
        return new LeastSquaresFunctional(dataSet, model);
    }
}