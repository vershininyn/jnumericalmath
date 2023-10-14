package dev.projects.math.jnummath.jnumoptimization.local.constrained.functionals;

import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.impl.MaxBarrierFunction;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.impl.inequalities.IndexedScalarDifferentiableFunction;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.impl.inequalities.LessThenOrEqualsZeroConstrainedScalarDifferentiableFunction;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.impl.inequalities.MoreThenOrEqualsZeroConstrainedScalarDifferentiableFunction;
import dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.AbstractFunctionalTests;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.model.IScalarDifferentiableModel;
import dev.projects.math.jnummath.jtransformations.model.functionals.IFunctional;
import dev.projects.math.jnummath.jdatasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.utils.exception.LoggableException;

import java.util.Arrays;

public abstract class AbstractFunctionalWithConstraintsTests extends AbstractFunctionalTests {
    @Override
    public IScalarDifferentiableFunction getFunctional(OneDimensionalSupervisedDataSet dataSet, IScalarDifferentiableModel model)   {
        MaxBarrierFunction.MaxBarrierFunctionBuilder builder = new MaxBarrierFunction.MaxBarrierFunctionBuilder(10.0,
                1.0005,
                getNestedFunctional(dataSet, model));

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

    protected abstract IFunctional getNestedFunctional(OneDimensionalSupervisedDataSet dataSet, IScalarDifferentiableModel model)  ;
}
