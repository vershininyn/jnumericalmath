package dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base;

import dev.projects.math.optimization.unconstrained.local.algorithms.IUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.IFirstOrderDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.firstorder.IFirstOrderLinearSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;

public interface IFirstOrderUnconstrainedOptimizationAlgorithm<TLinearSearchAlgorithm extends IFirstOrderLinearSearchAlgorithm,
                                                                TDirectionSearchAlgortihm extends IFirstOrderDirectionSearchAlgorithm,
                                                                TCriticalOptimizationAttributes extends FirstOrderCriticalOptimizationAttributes>
        extends IUnconstrainedOptimizationAlgorithm<IScalarDifferentiableFunction,
                                                            TLinearSearchAlgorithm ,
                                                            TDirectionSearchAlgortihm,
                                                            TCriticalOptimizationAttributes,
                                                            FirstOrderOptimizationResults> {
}
