package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.learning;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderLocalCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.FirstOrderCriticalOptimizationAttributes;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FirstOrderLearningCriticalOptimizationAttributes extends FirstOrderCriticalOptimizationAttributes {
    private double epsilonGradientCriticalValue, criticalStepValue;

    public FirstOrderLearningCriticalOptimizationAttributes(long maxIterationCount,
                                                            long maxFunctionComputeCount,
                                                            long maxGradientComputeCount,
                                                            long maxFunctionOutputRepeatedComputeCount,
                                                            long shutdownMillisecondsTime,
                                                            double expectedExtremum,
                                                            double epsilonLearningFunctionCriticalOutput,
                                                            double epsilonLearningGradientCriticalValue,
                                                            double criticalLearningStepValue,
                                                            double epsilonGradientCriticalEuclidNormValue)   {
        super(maxIterationCount,
                maxFunctionComputeCount,
                maxGradientComputeCount,
                maxFunctionOutputRepeatedComputeCount,
                shutdownMillisecondsTime,
                expectedExtremum,
                epsilonLearningFunctionCriticalOutput,
                epsilonGradientCriticalEuclidNormValue);

        setEpsilonGradientCriticalValue(epsilonLearningGradientCriticalValue);
        setCriticalStepValue(criticalLearningStepValue);
    }

    public static FirstOrderLearningCriticalOptimizationAttributes getStandartSet(double expectedExtremum,
                                                                                  double epsilonFunctionOutput)   {
        FirstOrderLearningCriticalOptimizationAttributes attrb = new FirstOrderLearningCriticalOptimizationAttributes();

        attrb.setMaxIterationCount(300000L);
        attrb.setMaxFunctionComputeCount(300000L);
        attrb.setMaxGradientComputeCount(300000L);
        attrb.setMaxFunctionOutputRepeatedComputeCount(5);
        attrb.setShutdownMillisecondsTime(1000*60*2);
        attrb.setExpectedExtremum(expectedExtremum);
        attrb.setEpsilonFunctionCriticalOutput(epsilonFunctionOutput);
        attrb.setEpsilonGradientCriticalValue(1.0E-60);
        attrb.setCriticalStepValue(1.0E-60);
        attrb.setEpsilonGradientCriticalValue(1.0E-60);

        return attrb;
    }

    @Override
    public ZeroOrderLocalCriticalOptimizationAttributes deepCopy()   {
        FirstOrderLearningCriticalOptimizationAttributes attrb = new FirstOrderLearningCriticalOptimizationAttributes();

        attrb.setMaxIterationCount(getMaxIterationCount());
        attrb.setMaxFunctionComputeCount(getMaxFunctionComputeCount());
        attrb.setMaxGradientComputeCount(getMaxGradientComputeCount());
        attrb.setMaxFunctionOutputRepeatedComputeCount(getMaxFunctionOutputRepeatedCount());
        attrb.setShutdownMillisecondsTime(getShutdownMillisecondsTime());
        attrb.setExpectedExtremum(getExpectedExtremum());
        attrb.setEpsilonFunctionCriticalOutput(getEpsilonFunctionCriticalOutput());
        attrb.setEpsilonGradientCriticalValue(getEpsilonGradientCriticalValue());
        attrb.setCriticalStepValue(getCriticalStepValue());
        attrb.setEpsilonGradientCriticalValue(getEpsilonGradientCriticalValue());

        return attrb;
    }
}
