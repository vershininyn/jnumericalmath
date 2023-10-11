package dev.projects.math.optimization.local.unconstrained.firstorder.group.firstorder.learning;

import dev.projects.math.optimization.local.unconstrained.firstorder.group.firstorder.FirstOrderConcurrentFunctionsGroupTestingMaster;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.learning.FirstOrderLearningCriticalOptimizationAttributes;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.learning.IFirstOrderLearningUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.firstorder.impl.learning.IFirstOrderLearningLinearSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

import java.util.List;

public class LearningFirstOrderConcurrentFunctionsGroupTestingMaster
                extends FirstOrderConcurrentFunctionsGroupTestingMaster<IFirstOrderLearningLinearSearchAlgorithm,
                        IFirstOrderLearningDirectionSearchAlgorithm,
                        FirstOrderLearningCriticalOptimizationAttributes,
                        IFirstOrderLearningUnconstrainedOptimizationAlgorithm,
                        dev.projects.math.optimization.local.unconstrained.firstorder.group.firstorder.Learning.LearningFirstOrderFunctionsTestingGroupMap,
                        LearningFirstOrderConcurrentFunctionsGroupTestingSlave> {
    public LearningFirstOrderConcurrentFunctionsGroupTestingMaster(List<IScalarDifferentiableFunction> funGroup, List<IFirstOrderLearningUnconstrainedOptimizationAlgorithm> algGroup) throws LoggableException {
        super(funGroup, algGroup);
    }

    @Override
    protected LearningFirstOrderConcurrentFunctionsGroupTestingSlave getInstanceOfSlave(List<IFirstOrderLearningUnconstrainedOptimizationAlgorithm> optAlgorithmsGroup,
                                                                                        IScalarDifferentiableFunction function) throws LoggableException {
        return new LearningFirstOrderConcurrentFunctionsGroupTestingSlave(optAlgorithmsGroup, function);
    }
}
