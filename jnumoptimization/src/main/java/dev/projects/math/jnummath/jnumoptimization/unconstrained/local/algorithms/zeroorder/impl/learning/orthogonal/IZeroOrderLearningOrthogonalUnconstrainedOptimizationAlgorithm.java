package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.impl.learning.orthogonal;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.learning.IZeroOrderLearningUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.learning.ZeroOrderLocalLearningCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.zeroorder.impl.learning.orthogonal.IZeroOrderOrthogonalDirectionSearchAlgorithm;

public interface IZeroOrderLearningOrthogonalUnconstrainedOptimizationAlgorithm
                    extends IZeroOrderLearningUnconstrainedOptimizationAlgorithm<IZeroOrderOrthogonalDirectionSearchAlgorithm,
        ZeroOrderLocalLearningCriticalOptimizationAttributes> {
}
