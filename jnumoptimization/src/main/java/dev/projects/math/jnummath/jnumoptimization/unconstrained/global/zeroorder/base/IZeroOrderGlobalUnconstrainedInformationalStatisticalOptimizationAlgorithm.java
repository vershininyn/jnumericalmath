package dev.projects.math.jnummath.jnumoptimization.unconstrained.global.zeroorder.base;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.global.base.SimpleHyperCube;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.IUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.zeroorder.IZeroOrderDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.zeroorder.IZeroOrderLinearSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;

public interface IZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm
        extends IUnconstrainedOptimizationAlgorithm<IScalarFunction,
        IZeroOrderLinearSearchAlgorithm,
        IZeroOrderDirectionSearchAlgorithm,
        ZeroOrderGlobalCriticalOptimizationAttributes,
        ZeroOrderOptimizationResults> {

    SimpleHyperCube getOriginalCube()  ;

    void setOriginalCube(SimpleHyperCube cube)  ;

    ZeroOrderOptimizationResults optimize(IScalarFunction function,
                                          ZeroOrderGlobalCriticalOptimizationAttributes attributes,
                                          SimpleHyperCube originalHyperCube)  ;

}
