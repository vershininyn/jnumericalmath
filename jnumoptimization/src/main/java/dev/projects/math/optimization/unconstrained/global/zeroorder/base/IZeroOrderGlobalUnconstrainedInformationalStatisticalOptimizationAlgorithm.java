package dev.projects.math.optimization.unconstrained.global.zeroorder.base;

import dev.projects.math.optimization.unconstrained.global.base.SimpleHyperCube;
import dev.projects.math.optimization.unconstrained.local.algorithms.IUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.math.optimization.unconstrained.local.directionsearch.zeroorder.IZeroOrderDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.zeroorder.IZeroOrderLinearSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;

public interface IZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm
        extends IUnconstrainedOptimizationAlgorithm<IScalarFunction,
        IZeroOrderLinearSearchAlgorithm,
        IZeroOrderDirectionSearchAlgorithm,
        ZeroOrderGlobalCriticalOptimizationAttributes,
        ZeroOrderOptimizationResults> {

    SimpleHyperCube getOriginalCube() throws LoggableException;

    void setOriginalCube(SimpleHyperCube cube) throws LoggableException;

    ZeroOrderOptimizationResults optimize(IScalarFunction function,
                                          ZeroOrderGlobalCriticalOptimizationAttributes attributes,
                                          SimpleHyperCube originalHyperCube) throws LoggableException;

}
