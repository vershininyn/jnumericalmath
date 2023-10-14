package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.IUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.IFirstOrderDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.IFirstOrderLinearSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;

public interface IFirstOrderUnconstrainedOptimizationAlgorithm<TLinearSearchAlgorithm extends IFirstOrderLinearSearchAlgorithm,
                                                                TDirectionSearchAlgortihm extends IFirstOrderDirectionSearchAlgorithm,
                                                                TCriticalOptimizationAttributes extends FirstOrderCriticalOptimizationAttributes>
        extends IUnconstrainedOptimizationAlgorithm<IScalarDifferentiableFunction,
                                                                    TLinearSearchAlgorithm ,
                                                                    TDirectionSearchAlgortihm,
                                                                    TCriticalOptimizationAttributes,
                                                                    FirstOrderOptimizationResults> {
}
