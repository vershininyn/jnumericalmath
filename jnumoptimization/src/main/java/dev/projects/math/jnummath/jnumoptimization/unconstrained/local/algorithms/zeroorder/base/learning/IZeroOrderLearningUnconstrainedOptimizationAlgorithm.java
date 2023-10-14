package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.learning;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.IZeroOrderUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderLocalCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.zeroorder.impl.learning.base.IZeroOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.zeroorder.impl.learning.IZeroOrderLearningLinearSearchAlgorithm;

public interface IZeroOrderLearningUnconstrainedOptimizationAlgorithm<TDirectionSearchAlgorithm extends IZeroOrderLearningDirectionSearchAlgorithm,
                                                                      TCriticalOptimizationAttributes extends ZeroOrderLocalCriticalOptimizationAttributes>
                    extends IZeroOrderUnconstrainedOptimizationAlgorithm<IZeroOrderLearningLinearSearchAlgorithm,
                                                                            TDirectionSearchAlgorithm,
                                                                            TCriticalOptimizationAttributes>
{

}
