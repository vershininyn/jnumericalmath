package dev.projects.math.optimization.unconstrained.local.base;

import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.StringJoiner;

@Getter
@Setter
public class TotalStatisticsOptimizationResults<TOptimizationResults extends ZeroOrderOptimizationResults> {
    private int totalActualIterationCount = 0,
            totalActualFunctionComputeCount = 0,
            totalMaxFunctionOutputRepeatedCount = 0;
    private Duration totalActualTimePeriod = Duration.ZERO;

    public TotalStatisticsOptimizationResults() {}

    @Override
    public String toString() {
        StringJoiner strJoiner = new StringJoiner(",");

        strJoiner.add("totalActualIterationCount= "+getTotalActualIterationCount());
        strJoiner.add("totalActualFunctionComputeCount= "+getTotalActualFunctionComputeCount());
        strJoiner.add("totalMaxFunctionOutputRepeatedCount= "+getTotalMaxFunctionOutputRepeatedCount());
        strJoiner.add("totalActualTimePeriod= "+getTotalActualTimePeriod()+"ms");

        return strJoiner.toString();
    }

    public void plusOneResult(TOptimizationResults results) {
        totalActualIterationCount += results.getActualIterationCount();
        totalActualFunctionComputeCount += results.getActualFunctionComputeCount();
        totalMaxFunctionOutputRepeatedCount += results.getActualFunctionOutputRepeatedCount();
        totalActualTimePeriod = totalActualTimePeriod.plus(results.getActualTimePeriod());
    }
}
