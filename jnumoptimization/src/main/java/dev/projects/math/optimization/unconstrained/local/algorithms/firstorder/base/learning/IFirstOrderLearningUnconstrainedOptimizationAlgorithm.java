package dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.learning;

import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.IFirstOrderUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.firstorder.impl.learning.IFirstOrderLearningLinearSearchAlgorithm;

public interface IFirstOrderLearningUnconstrainedOptimizationAlgorithm
        extends IFirstOrderUnconstrainedOptimizationAlgorithm<IFirstOrderLearningLinearSearchAlgorithm,
        IFirstOrderLearningDirectionSearchAlgorithm,
                                                                FirstOrderLearningCriticalOptimizationAttributes> {
}
