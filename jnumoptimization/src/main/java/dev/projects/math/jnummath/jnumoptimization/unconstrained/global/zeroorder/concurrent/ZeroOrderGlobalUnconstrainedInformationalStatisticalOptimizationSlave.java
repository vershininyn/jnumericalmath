package dev.projects.math.jnummath.jnumoptimization.unconstrained.global.zeroorder.concurrent;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.global.zeroorder.base.IZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm;
import dev.projects.utils.exception.LoggableException;

import java.util.concurrent.Callable;

public class ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationSlave
            implements Callable<ZeroOrderOptimizationResults> {

    private IZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm
                optAlgorithm;

    public ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationSlave(IZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm optAlgorithm)   {
        this.optAlgorithm = optAlgorithm;
    }

    @Override
    public ZeroOrderOptimizationResults call() throws Exception {
        return optAlgorithm.minimize();
    }
}
