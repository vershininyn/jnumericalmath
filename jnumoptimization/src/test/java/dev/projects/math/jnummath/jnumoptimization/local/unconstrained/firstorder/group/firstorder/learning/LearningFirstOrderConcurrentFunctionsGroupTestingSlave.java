package dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.group.firstorder.learning;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.learning.FirstOrderLearningCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.learning.IFirstOrderLearningUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.group.firstorder.FirstOrderConcurrentFunctionsGroupTestingSlave;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.impl.learning.IFirstOrderLearningLinearSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

import java.util.List;
import java.util.concurrent.Callable;

public class LearningFirstOrderConcurrentFunctionsGroupTestingSlave
                extends FirstOrderConcurrentFunctionsGroupTestingSlave<IFirstOrderLearningLinearSearchAlgorithm,
                        IFirstOrderLearningDirectionSearchAlgorithm,
        FirstOrderLearningCriticalOptimizationAttributes,
        IFirstOrderLearningUnconstrainedOptimizationAlgorithm,
        LearningFirstOrderFunctionsTestingGroupMap>
                implements Callable<LearningFirstOrderFunctionsTestingGroupMap> {

    public LearningFirstOrderConcurrentFunctionsGroupTestingSlave(List<IFirstOrderLearningUnconstrainedOptimizationAlgorithm> optAlgorithmsGroup, IScalarDifferentiableFunction function)   {
        super(optAlgorithmsGroup, function);
    }

    @Override
    protected LearningFirstOrderFunctionsTestingGroupMap getFunctionsTestingGroupMapInstance()   {
        return new LearningFirstOrderFunctionsTestingGroupMap();
    }
}
