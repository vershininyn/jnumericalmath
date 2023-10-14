package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.base.StopCriteria;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
public class FirstOrderOptimizationResults extends ZeroOrderOptimizationResults {
    private long actualGradientComputeCount = 0;

    public FirstOrderOptimizationResults(long actualIterationCount,
                                         long actualFunctionComputeCount,
                                         long actualFunctionRepeatedCount,
                                         Duration actualNanoseconds,
                                         double actualFunctionOutput,
                                         long actualGradientComputeCount,
                                         DenseVector optimalApproximation,
                                         StopCriteria stopCriteria,
                                         String algorithmName) {
        setActualGradientComputeCount(actualGradientComputeCount);
        setActualFunctionComputeCount(actualFunctionComputeCount);
        setActualTimePeriod(actualNanoseconds);

        setActualFunctionOutput(actualFunctionOutput);
        setActualIterationCount(actualIterationCount);
        setOptimalApproximation(optimalApproximation.deepCopy());

        setActualFunctionOutputRepeatedCount(actualFunctionRepeatedCount);
        setStopCriteria(stopCriteria);
        setAlgorithmName(algorithmName);
    }

    @Override
    public String toString() {
        return super.toString()+",gradComputeCount = "+getActualGradientComputeCount();
    }
}
