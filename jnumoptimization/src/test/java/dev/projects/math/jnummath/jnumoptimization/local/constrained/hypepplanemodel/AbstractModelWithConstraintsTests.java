package dev.projects.math.jnummath.jnumoptimization.local.constrained.hypepplanemodel;

import dev.projects.math.jnummath.jdatasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.base.AbstractBarrierBuilder;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.impl.inequalities.IndexedScalarDifferentiableFunction;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.impl.inequalities.LessThenOrEqualsZeroConstrainedScalarDifferentiableFunction;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.impl.inequalities.MoreThenOrEqualsZeroConstrainedScalarDifferentiableFunction;
import dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.AbstractFunctionalTests;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.model.IScalarDifferentiableModel;
import dev.projects.utils.exception.LoggableException;

import java.util.Arrays;

public abstract class AbstractModelWithConstraintsTests extends AbstractFunctionalTests {
    @Override
    public IScalarDifferentiableFunction getFunctional(OneDimensionalSupervisedDataSet dataSet, IScalarDifferentiableModel model)   {
        AbstractBarrierBuilder builder = getBarrierFunction(10.0, 1.0005, dataSet, model);

        int varsCount = model.getParametersCount();

        builder.addInequalityConstraint(
                new LessThenOrEqualsZeroConstrainedScalarDifferentiableFunction(new IndexedScalarDifferentiableFunction(varsCount, Arrays.asList(1.0, 1.0, 1.0), Arrays.asList(0, 1, 2)), 1.0));

        builder.addInequalityConstraint(
                new LessThenOrEqualsZeroConstrainedScalarDifferentiableFunction(new IndexedScalarDifferentiableFunction(varsCount, Arrays.asList(2.0, 2.0, 2.0), Arrays.asList(0, 1, 2)), 2.0));

        builder.addInequalityConstraint(
                new LessThenOrEqualsZeroConstrainedScalarDifferentiableFunction(new IndexedScalarDifferentiableFunction(varsCount, Arrays.asList(3.0, 3.0, 3.0), Arrays.asList(0, 1, 2)), 3.0));

        builder.addInequalityConstraint(
                new MoreThenOrEqualsZeroConstrainedScalarDifferentiableFunction(new IndexedScalarDifferentiableFunction(varsCount, Arrays.asList(1.0, 0.0, 0.0), Arrays.asList(0, 1, 2)), 0.0));

        builder.addInequalityConstraint(
                new MoreThenOrEqualsZeroConstrainedScalarDifferentiableFunction(new IndexedScalarDifferentiableFunction(varsCount, Arrays.asList(0.0, 1.0, 0.0), Arrays.asList(0, 1, 2)), 0.0));

        builder.addInequalityConstraint(
                new MoreThenOrEqualsZeroConstrainedScalarDifferentiableFunction(new IndexedScalarDifferentiableFunction(varsCount, Arrays.asList(0.0, 0.0, 1.0), Arrays.asList(0, 1, 2)), 0.0));

        //builder.addEqualityConstraint(new SumEqualityConstraintScalarDifferentiableFunction(varsCount, 19.0));

        return builder.build();
    }

    protected abstract AbstractBarrierBuilder getBarrierFunction(double kInitialCoefficient,
                                                                 double kIncreaseStepCoefficient,
                                                                 OneDimensionalSupervisedDataSet dataSet,
                                                                 IScalarDifferentiableModel model)  ;
}
