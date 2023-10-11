package dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base;

import dev.projects.math.linalgebra.ICloneable;
import dev.projects.utils.exception.LoggableException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class ZeroOrderLocalCriticalOptimizationAttributes
        extends ZeroOrderLocalCriticalOptimizationAttributesBase
        implements ICloneable<ZeroOrderLocalCriticalOptimizationAttributes> {
    @Getter @Setter
    private double expectedExtremum;

    @Getter
    private double epsilonFunctionCriticalOutput;

    public ZeroOrderLocalCriticalOptimizationAttributes() {}

    public void setEpsilonFunctionCriticalOutput(double epsilonFunctionCriticalOutput) throws LoggableException {
        if (epsilonFunctionCriticalOutput < 0.0) throw new LoggableException("Unacceptable epsilon function critical output value");

        this.epsilonFunctionCriticalOutput = epsilonFunctionCriticalOutput;
    }

    @Override
    public ZeroOrderLocalCriticalOptimizationAttributes deepCopy() throws LoggableException {
        ZeroOrderLocalCriticalOptimizationAttributes attrib =  new ZeroOrderLocalCriticalOptimizationAttributes();

        attrib.setExpectedExtremum(getExpectedExtremum());
        attrib.setEpsilonFunctionCriticalOutput(getEpsilonFunctionCriticalOutput());
        attrib.setMaxFunctionComputeCount(getMaxFunctionComputeCount());
        attrib.setMaxFunctionOutputRepeatedComputeCount(getMaxFunctionOutputRepeatedCount());
        attrib.setMaxIterationCount(getMaxIterationCount());
        attrib.setShutdownMillisecondsTime(getShutdownMillisecondsTime());

        return attrib;
    }
}
