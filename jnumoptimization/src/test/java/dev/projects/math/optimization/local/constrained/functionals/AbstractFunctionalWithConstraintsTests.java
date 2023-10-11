package dev.projects.math.optimization.local.constrained.functionals;

import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.math.transformations.model.IScalarDifferentiableModel;
import dev.projects.math.transformations.model.functionals.IFunctional;
import dev.projects.math.datasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.optimization.constrained.barrier.impl.MaxBarrierFunction;
import dev.projects.math.optimization.constrained.barrier.impl.inequalities.IndexedScalarDifferentiableFunction;
import dev.projects.math.optimization.constrained.barrier.impl.inequalities.LessThenOrEqualsZeroConstrainedScalarDifferentiableFunction;
import dev.projects.math.optimization.constrained.barrier.impl.inequalities.MoreThenOrEqualsZeroConstrainedScalarDifferentiableFunction;
import dev.projects.math.optimization.local.unconstrained.firstorder.AbstractFunctionalTests;
import dev.projects.utils.exception.LoggableException;

import java.util.Arrays;

public abstract class AbstractFunctionalWithConstraintsTests extends AbstractFunctionalTests {
    @Override
    public IScalarDifferentiableFunction getFunction(OneDimensionalSupervisedDataSet dataSet, IScalarDifferentiableModel model) throws LoggableException {
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

    protected abstract IFunctional getNestedFunctional(OneDimensionalSupervisedDataSet dataSet, IScalarDifferentiableModel model) throws LoggableException;
}
