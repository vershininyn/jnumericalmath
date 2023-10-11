package dev.projects.math.optimization.local.unconstrained.firstorder.group.firstorder;

import dev.projects.math.optimization.local.unconstrained.firstorder.group.base.ConcurrentFunctionsGroupTestingMaster;
import dev.projects.math.optimization.unconstrained.local.algorithms.IUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.FirstOrderCriticalOptimizationAttributes;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.FirstOrderOptimizationResults;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.IFirstOrderDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.firstorder.IFirstOrderLinearSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

import java.util.List;

public abstract class FirstOrderConcurrentFunctionsGroupTestingMaster<TLinearSearchAlgorithm extends IFirstOrderLinearSearchAlgorithm,
                                                             TDirectionSearchAlgortihm extends IFirstOrderDirectionSearchAlgorithm,
                                                             TCriticalOptimizationAttributes extends FirstOrderCriticalOptimizationAttributes,
                                                             TOptimizationAlgorithm extends IUnconstrainedOptimizationAlgorithm<IScalarDifferentiableFunction,
                                                                                                                                  TLinearSearchAlgorithm,
                                                                                                                                  TDirectionSearchAlgortihm,
                                                                                                                                  TCriticalOptimizationAttributes,
                                                                                                                                  FirstOrderOptimizationResults>,
                                                             TFirstOrderFunctionsTestingGroupMap extends FirstOrderFunctionsTestingGroupMap<TLinearSearchAlgorithm,
                                                                                                                                            TDirectionSearchAlgortihm,
                                                                                                                                            TCriticalOptimizationAttributes>,
                                                             TFirstOrderConcurrentFunctionsGroupTestingSlave extends FirstOrderConcurrentFunctionsGroupTestingSlave<TLinearSearchAlgorithm,
                                                                                                                                                                    TDirectionSearchAlgortihm,
                                                                                                                                                                    TCriticalOptimizationAttributes,
                                                                                                                                                                    TOptimizationAlgorithm,
                                                                                                                                                                    TFirstOrderFunctionsTestingGroupMap>>
                    extends ConcurrentFunctionsGroupTestingMaster<IScalarDifferentiableFunction,
                                                                                                            TLinearSearchAlgorithm,
                                                                                                            TDirectionSearchAlgortihm,
                                                                                                            TCriticalOptimizationAttributes,
                                                FirstOrderOptimizationResults,
                                                                                                            TOptimizationAlgorithm,
                                                                                                            TFirstOrderFunctionsTestingGroupMap,
                                                                                                            TFirstOrderConcurrentFunctionsGroupTestingSlave> {

    public FirstOrderConcurrentFunctionsGroupTestingMaster(List<IScalarDifferentiableFunction> funGroup, List<TOptimizationAlgorithm> algGroup) throws LoggableException {
        super(funGroup, algGroup);
    }
}
