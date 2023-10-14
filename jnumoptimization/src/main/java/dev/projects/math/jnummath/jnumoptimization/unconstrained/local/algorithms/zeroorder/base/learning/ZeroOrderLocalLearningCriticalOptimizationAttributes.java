package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.learning;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderLocalCriticalOptimizationAttributes;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZeroOrderLocalLearningCriticalOptimizationAttributes
            extends ZeroOrderLocalCriticalOptimizationAttributes {
    private double criticalStepValue = 0.0;

    public ZeroOrderLocalLearningCriticalOptimizationAttributes(long maxIterationCount,
                                                                long maxFunctionComputeCount,
                                                                long maxFunctionOutputRepeatedCount,
                                                                long shutdownMillisecondsTime,
                                                                double criticalLearningStepValue,
                                                                double expectedExtremum,
                                                                double epsilonFunctionCriticalOutput)   {
        setMaxIterationCount(maxIterationCount);
        setMaxFunctionComputeCount(maxFunctionComputeCount);
        setMaxFunctionOutputRepeatedComputeCount(maxFunctionOutputRepeatedCount);
        setShutdownMillisecondsTime(shutdownMillisecondsTime);
        setExpectedExtremum(expectedExtremum);
        setEpsilonFunctionCriticalOutput(epsilonFunctionCriticalOutput);
        setCriticalStepValue(criticalLearningStepValue);
    }

    public static ZeroOrderLocalLearningCriticalOptimizationAttributes getStandartSet(double expectedExtremum,
                                                                                      double epsilonFunctionOutput)   {
        return new ZeroOrderLocalLearningCriticalOptimizationAttributes(100000L,
                100000L,
                10,
                1000*60*2,
                1.0E-15,
                expectedExtremum,
                epsilonFunctionOutput);
    }

    @Override
    public ZeroOrderLocalCriticalOptimizationAttributes deepCopy()   {
        return new ZeroOrderLocalLearningCriticalOptimizationAttributes(getMaxIterationCount(),
                getMaxFunctionComputeCount(),
                getMaxFunctionOutputRepeatedCount(),
                getShutdownMillisecondsTime(),
                getCriticalStepValue(),
                getExpectedExtremum(),
                getEpsilonFunctionCriticalOutput());
    }
}
