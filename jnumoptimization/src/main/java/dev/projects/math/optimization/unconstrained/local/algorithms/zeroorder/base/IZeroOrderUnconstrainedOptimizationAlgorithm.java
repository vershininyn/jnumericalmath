package dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base;

import dev.projects.math.optimization.unconstrained.local.algorithms.IUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.zeroorder.IZeroOrderDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.zeroorder.IZeroOrderLinearSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.IScalarFunction;

public interface IZeroOrderUnconstrainedOptimizationAlgorithm<TLinearSearchAlgorithm extends IZeroOrderLinearSearchAlgorithm,
                                                            TDirectionSearchAlgortihm extends IZeroOrderDirectionSearchAlgorithm,
                                                            TCriticalOptimizationAttributes extends ZeroOrderLocalCriticalOptimizationAttributes>
                        extends IUnconstrainedOptimizationAlgorithm<IScalarFunction,
                                                                                            TLinearSearchAlgorithm,
                                                                                            TDirectionSearchAlgortihm,
                                                                                            TCriticalOptimizationAttributes,
                                                                                            ZeroOrderOptimizationResults>
{

}
