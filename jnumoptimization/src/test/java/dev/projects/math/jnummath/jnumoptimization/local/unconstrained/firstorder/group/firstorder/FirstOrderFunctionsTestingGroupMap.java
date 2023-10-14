package dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.group.firstorder;

import dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.group.base.FunctionsTestingGroupMap;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.FirstOrderCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.FirstOrderOptimizationResults;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.IFirstOrderDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.IFirstOrderLinearSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;

public class FirstOrderFunctionsTestingGroupMap<TLinearSearchAlgorithm extends IFirstOrderLinearSearchAlgorithm,
                                                TDirectionSearchAlgorithm extends IFirstOrderDirectionSearchAlgorithm,
                                                TCriticalOptimizationAttributes extends FirstOrderCriticalOptimizationAttributes>
        extends FunctionsTestingGroupMap<IScalarDifferentiableFunction,
                                                                    TLinearSearchAlgorithm,
                                                                    TDirectionSearchAlgorithm,
                                                                    TCriticalOptimizationAttributes,
                                FirstOrderOptimizationResults> {
}
