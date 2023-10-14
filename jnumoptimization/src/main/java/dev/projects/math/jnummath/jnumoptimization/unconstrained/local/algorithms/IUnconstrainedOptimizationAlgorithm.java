package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderLocalCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.IDirectionSearchAlgorithm;
import dev.projects.math.transformations.ICloneable;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.base.ILinearSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;
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

    void setFunction(TFunction function)  ;
    TFunction getFunction();

    void setDirectionSearchAlgorithm(TDirectionSearchAlgorithm dsAlgorithm)  ;
    TDirectionSearchAlgorithm getDirectionSearchAlgorithm();

    void setLinearSearchAlgorithm(TLinearSearchAlgorithm lsAlgorithm)  ;
    TLinearSearchAlgorithm getLinearSearchAlgorithm();

    void setCriticalOptimizationAttributes(TOptimizationAttributes attributes)  ;
    TOptimizationAttributes getCriticalOptimizationAttributes();

    TOptimizationResults minimize()  ;

    TOptimizationResults getOptimizationResults(int actualIterationCount, Duration period)  ;
}
