package dev.projects.math.optimization.unconstrained.global.zeroorder.base;

import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderLocalCriticalOptimizationAttributes;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;

public class ZeroOrderGlobalCriticalOptimizationAttributes
            extends ZeroOrderLocalCriticalOptimizationAttributes {

    @Getter
    private double epsilonGlobalByNormValue = 0.0;

    @Getter
    private long maxMultiplicityFactor = 0L;

    public ZeroOrderGlobalCriticalOptimizationAttributes(long maxIterationCount,
                                                         long maxFunctionComputeCount,
                                                         long maxFunctionOutputRepeatedCount,
                                                         long shutdownMillisecondsTime,
                                                         long maxMultiplicityFactor,
                                                         double expectedExtremum,
                                                         double epsilonCriticalFunctionOutput,
                                                         double epsilonGlobalByNormValue) throws LoggableException {
        setMaxIterationCount(maxIterationCount);
        setMaxFunctionComputeCount(maxFunctionComputeCount);
        setMaxFunctionOutputRepeatedComputeCount(maxFunctionOutputRepeatedCount);
        setMaxMultiplicityFactor(maxMultiplicityFactor);

        setShutdownMillisecondsTime(shutdownMillisecondsTime);
        setExpectedExtremum(expectedExtremum);
        setEpsilonFunctionCriticalOutput(epsilonCriticalFunctionOutput);
        setEpsilonGlobalByNormValue(epsilonGlobalByNormValue);
    }

    public static ZeroOrderGlobalCriticalOptimizationAttributes getStandartSet(double expectedExtremum,
                                                                               double epsilonCriticalFunctionOutput,
                                                                               double epsilonGlobalByNormValue) throws LoggableException {

        return new ZeroOrderGlobalCriticalOptimizationAttributes(1000000000L,
                1000000000L,
                5,
                1000*60*5,
                50,
                expectedExtremum,
                epsilonCriticalFunctionOutput,
                epsilonGlobalByNormValue);
    }

    public void setEpsilonGlobalByNormValue(double epsilon) throws LoggableException {
        if (epsilon <= 0.0) throw new LoggableException("Unacceptable global at norm epsilon value");

        epsilonGlobalByNormValue = epsilon;
    }

    public void setMaxMultiplicityFactor(long maxMultiplicityFactor) throws LoggableException {
        if (maxMultiplicityFactor <= 0L) throw new LoggableException("Unacceptable max multiplicity factor");

        this.maxMultiplicityFactor = maxMultiplicityFactor;
    }

    @Override
    public ZeroOrderLocalCriticalOptimizationAttributes deepCopy() throws LoggableException {
        return new ZeroOrderGlobalCriticalOptimizationAttributes(getMaxIterationCount(),
                getMaxFunctionComputeCount(),
                getMaxFunctionOutputRepeatedCount(),
                getShutdownMillisecondsTime(),
                getMaxMultiplicityFactor(),
                getExpectedExtremum(),
                getEpsilonFunctionCriticalOutput(),
                getEpsilonGlobalByNormValue());
    }
}
