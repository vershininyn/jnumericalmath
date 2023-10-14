package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderLocalCriticalOptimizationAttributes;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;

@Getter
public class FirstOrderCriticalOptimizationAttributes
        extends ZeroOrderLocalCriticalOptimizationAttributes {
    private long maxGradientComputeCount = 0;
    private double criticalGradientEuclidNormEpsilon = 0.0;

    public FirstOrderCriticalOptimizationAttributes() {}

    public FirstOrderCriticalOptimizationAttributes(long maxIterationCount,
                                                    long maxFunctionComputeCount,
                                                    long maxGradientComputeCount,
                                                    long maxFunctionOutputRepeatedComputeCount,
                                                    long shutdownMillisecondsTime,
                                                    double expectedExtremum,
                                                    double epsilonFunctionCriticalOutput,
                                                    double epsilonCriticalGradientNorm)   {

        setMaxGradientComputeCount(maxGradientComputeCount);
        setMaxFunctionComputeCount(maxFunctionComputeCount);
        setMaxIterationCount(maxIterationCount);
        setMaxFunctionOutputRepeatedComputeCount(maxFunctionOutputRepeatedComputeCount);
        setEpsilonFunctionCriticalOutput(epsilonFunctionCriticalOutput);
        setExpectedExtremum(expectedExtremum);
        setShutdownMillisecondsTime(shutdownMillisecondsTime);
        setCriticalGradientEuclidNormEpsilon(epsilonCriticalGradientNorm);
    }

    public void setMaxGradientComputeCount(long maxGradientComputeCount)   {
        if (maxGradientComputeCount < 0) throw new LoggableException("Unacceptable max gradient compute count value");

        this.maxGradientComputeCount = maxGradientComputeCount;
    }

    public void setCriticalGradientEuclidNormEpsilon(double epsilon)  {
        if (epsilon < 0.0) throw new LoggableException("Unacceptable critical gradient norm epsilon");

        this.criticalGradientEuclidNormEpsilon = epsilon;
    }

    public double getCriticalGradientEuclidNormEpsilon() {
        return this.criticalGradientEuclidNormEpsilon;
    }

    @Override
    public ZeroOrderLocalCriticalOptimizationAttributes deepCopy()   {
        return new FirstOrderCriticalOptimizationAttributes(getMaxIterationCount(),
                getMaxFunctionComputeCount(),
                getMaxGradientComputeCount(),
                getMaxFunctionOutputRepeatedCount(),
                getShutdownMillisecondsTime(),
                getExpectedExtremum(),
                getEpsilonFunctionCriticalOutput(),
                getCriticalGradientEuclidNormEpsilon());
    }
}
