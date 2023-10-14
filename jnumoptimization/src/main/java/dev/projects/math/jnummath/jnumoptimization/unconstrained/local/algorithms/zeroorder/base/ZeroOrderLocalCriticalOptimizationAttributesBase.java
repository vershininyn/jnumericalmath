package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base;

import dev.projects.utils.exception.LoggableException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ZeroOrderLocalCriticalOptimizationAttributesBase {
    @Getter
    private long maxIterationCount,
            maxFunctionComputeCount,
            maxFunctionOutputRepeatedCount,
            shutdownMillisecondsTime;

    public void setMaxIterationCount(long maxIterationCount)   {
        if (maxIterationCount < 0) throw new LoggableException("Unacceptable max iteration count value");

        this.maxIterationCount = maxIterationCount;
    }

    public void setMaxFunctionComputeCount(long maxFunctionComputeCount)   {
        if (maxFunctionComputeCount < 0) throw new LoggableException("Unacceptable max function compute count value");

        this.maxFunctionComputeCount = maxFunctionComputeCount;
    }

    public void setMaxFunctionOutputRepeatedComputeCount(long maxFunctionOutputRepeatedCount)   {
        if (maxFunctionOutputRepeatedCount < 0) throw new LoggableException("Unacceptable max function output repeated count value");

        this.maxFunctionOutputRepeatedCount = maxFunctionOutputRepeatedCount;
    }

    public void setShutdownMillisecondsTime(long shutdownMillisecondsTime)   {
        if (shutdownMillisecondsTime <= 0) throw new LoggableException("Unacceptable shutdown milliseconds time value");

        this.shutdownMillisecondsTime = shutdownMillisecondsTime;
    }
}
