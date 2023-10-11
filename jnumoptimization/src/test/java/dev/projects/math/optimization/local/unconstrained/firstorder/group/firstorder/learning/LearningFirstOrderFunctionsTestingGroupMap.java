package dev.projects.math.optimization.local.unconstrained.firstorder.group.firstorder.Learning;

import dev.projects.math.optimization.local.unconstrained.firstorder.group.firstorder.FirstOrderFunctionsTestingGroupMap;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.learning.FirstOrderLearningCriticalOptimizationAttributes;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.firstorder.impl.learning.IFirstOrderLearningLinearSearchAlgorithm;

public class LearningFirstOrderFunctionsTestingGroupMap
                extends FirstOrderFunctionsTestingGroupMap<IFirstOrderLearningLinearSearchAlgorithm,
        IFirstOrderLearningDirectionSearchAlgorithm,
                        FirstOrderLearningCriticalOptimizationAttributes> {
}
