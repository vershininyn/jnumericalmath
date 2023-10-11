package dev.projects.math.optimization.unconstrained.local.algorithms;

import dev.projects.math.linalgebra.ICloneable;
import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderLocalCriticalOptimizationAttributes;
import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.math.optimization.unconstrained.local.directionsearch.IDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.base.ILinearSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;

import java.time.Duration;

public interface IUnconstrainedOptimizationAlgorithm<TFunction extends IScalarFunction,
                                                        TLinearSearchAlgorithm extends ILinearSearchAlgorithm<TFunction>,
                                                        TDirectionSearchAlgorithm extends IDirectionSearchAlgorithm<TFunction>,
                                                        TOptimizationAttributes extends ZeroOrderLocalCriticalOptimizationAttributes,
                                                        TOptimizationResults extends ZeroOrderOptimizationResults>
                    extends ICloneable<IUnconstrainedOptimizationAlgorithm<TFunction,
                                                                                                TLinearSearchAlgorithm,
                                                                                                TDirectionSearchAlgorithm,
                                                                                                TOptimizationAttributes,
                                                                                                TOptimizationResults>>
{
    String getAlgorithmName();

    void setFunction(TFunction function) throws LoggableException;
    TFunction getFunction();

    void setDirectionSearchAlgorithm(TDirectionSearchAlgorithm dsAlgorithm) throws LoggableException;
    TDirectionSearchAlgorithm getDirectionSearchAlgorithm();

    void setLinearSearchAlgorithm(TLinearSearchAlgorithm lsAlgorithm) throws LoggableException;
    TLinearSearchAlgorithm getLinearSearchAlgorithm();

    void setCriticalOptimizationAttributes(TOptimizationAttributes attributes) throws LoggableException;
    TOptimizationAttributes getCriticalOptimizationAttributes();

    TOptimizationResults minimize() throws LoggableException;

    TOptimizationResults getOptimizationResults(int actualIterationCount, Duration period) throws LoggableException;
}
