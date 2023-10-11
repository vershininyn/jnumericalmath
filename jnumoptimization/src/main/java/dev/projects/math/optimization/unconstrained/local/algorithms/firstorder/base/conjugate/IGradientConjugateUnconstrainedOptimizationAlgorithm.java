package dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.conjugate;

import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.FirstOrderCriticalOptimizationAttributes;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.IFirstOrderUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.IFirstOrderDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.firstorder.IFirstOrderLinearSearchAlgorithm;
import dev.projects.utils.exception.LoggableException;

public interface IGradientConjugateUnconstrainedOptimizationAlgorithm
    extends IFirstOrderUnconstrainedOptimizationAlgorithm<IFirstOrderLinearSearchAlgorithm,
                                                            IFirstOrderDirectionSearchAlgorithm,
        FirstOrderCriticalOptimizationAttributes> {
    void setStepStrategy(IGradientConjugateStepStrategy stepStrategy) throws LoggableException;
    IGradientConjugateStepStrategy getStrategy();
}
