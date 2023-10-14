package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.base.TotalStatisticsOptimizationResults;
import lombok.Getter;
import lombok.Setter;

import java.util.StringJoiner;

@Getter
@Setter
public class FirstOrderTotalStatisticsOptimizationResults
                extends TotalStatisticsOptimizationResults<FirstOrderOptimizationResults> {

    private int totalActualGradientComputeCount = 0;

    @Override
    public String toString() {
        StringJoiner strJoiner = new StringJoiner(",");

        strJoiner.add(super.toString());
        strJoiner.add("totalActualGradientComputeCount= "+getTotalActualGradientComputeCount());

        return strJoiner.toString();
    }

    @Override
    public void plusOneResult(FirstOrderOptimizationResults results) {
        super.plusOneResult(results);
        totalActualGradientComputeCount += results.getActualGradientComputeCount();
    }
}
