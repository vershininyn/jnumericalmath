package dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.group.firstorder.learning;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.learning.FirstOrderLearningCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.learning.IFirstOrderLearningUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.group.firstorder.FirstOrderConcurrentFunctionsGroupTestingMaster;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.impl.learning.IFirstOrderLearningLinearSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

import java.util.List;

public class LearningFirstOrderConcurrentFunctionsGroupTestingMaster
                extends FirstOrderConcurrentFunctionsGroupTestingMaster<IFirstOrderLearningLinearSearchAlgorithm,
                        IFirstOrderLearningDirectionSearchAlgorithm,
        FirstOrderLearningCriticalOptimizationAttributes,
        IFirstOrderLearningUnconstrainedOptimizationAlgorithm,
        LearningFirstOrderFunctionsTestingGroupMap,
                        LearningFirstOrderConcurrentFunctionsGroupTestingSlave> {
    public LearningFirstOrderConcurrentFunctionsGroupTestingMaster(List<IScalarDifferentiableFunction> funGroup, List<IFirstOrderLearningUnconstrainedOptimizationAlgorithm> algGroup)   {
        super(funGroup, algGroup);
    }

    @Override
    protected LearningFirstOrderConcurrentFunctionsGroupTestingSlave getInstanceOfSlave(List<IFirstOrderLearningUnconstrainedOptimizationAlgorithm> optAlgorithmsGroup,
                                                                                        IScalarDifferentiableFunction function)   {
        return new LearningFirstOrderConcurrentFunctionsGroupTestingSlave(optAlgorithmsGroup, function);
    }
}
