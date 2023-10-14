package dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.group.firstorder;

import dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.group.base.ConcurrentFunctionsGroupTestingSlave;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.IUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.FirstOrderCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.FirstOrderOptimizationResults;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.IFirstOrderDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.IFirstOrderLinearSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

import java.util.List;

public abstract class FirstOrderConcurrentFunctionsGroupTestingSlave<TLinearSearchAlgorithm extends IFirstOrderLinearSearchAlgorithm,
                                                            TDirectionSearchAlgortihm extends IFirstOrderDirectionSearchAlgorithm,
                                                            TCriticalOptimizationAttributes extends FirstOrderCriticalOptimizationAttributes,
                                                            TOptimizationAlgorithm extends IUnconstrainedOptimizationAlgorithm<IScalarDifferentiableFunction,
                                                                                                                                TLinearSearchAlgorithm,
                                                                                                                                TDirectionSearchAlgortihm,
                                                                                                                                TCriticalOptimizationAttributes,
                                                                                                                                FirstOrderOptimizationResults>,
                                                            TFirstOrderFunctionsTestingGroupMap extends FirstOrderFunctionsTestingGroupMap<TLinearSearchAlgorithm,
                                                                                                        TDirectionSearchAlgortihm,
                                                                                                        TCriticalOptimizationAttributes>>
                extends ConcurrentFunctionsGroupTestingSlave<IScalarDifferentiableFunction,
                                                                                                                TLinearSearchAlgorithm,
                                                                                                                TDirectionSearchAlgortihm,
                                                                                                                TCriticalOptimizationAttributes,
                                                        FirstOrderOptimizationResults,
                                                                                                                TFirstOrderFunctionsTestingGroupMap,
                                                                                                                TOptimizationAlgorithm> {

    public FirstOrderConcurrentFunctionsGroupTestingSlave(List<TOptimizationAlgorithm>  optAlgorithm, IScalarDifferentiableFunction function)   {
        super(optAlgorithm, function);
    }
}
