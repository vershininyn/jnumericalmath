package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.base.StopCriteria;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.base.StopCriteriaQuality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ZeroOrderOptimizationResults {
    private long actualIterationCount = 0, actualFunctionComputeCount = 0, actualFunctionOutputRepeatedCount = 0;
    private double actualFunctionOutput = 0.0;
    private String algorithmName = "";

    private Duration actualTimePeriod;
    private DenseVector optimalApproximation;
    private StopCriteria stopCriteria;

    @Override
    public String toString() {
        StringBuffer strBuffer = new StringBuffer();

        strBuffer.append("iterCount = " + getActualIterationCount());
        strBuffer.append(",funComputeCount = " + getActualFunctionComputeCount());
        strBuffer.append(",ms = " + getActualTimePeriod().toMillis() + "ms");
        strBuffer.append(",funOutput = " + getActualFunctionOutput());
        strBuffer.append(",funRepeatedCount = "+getActualFunctionOutputRepeatedCount());
        strBuffer.append(",stopCriteria = " + getStopCriteria());
        strBuffer.append(",algName = " + getAlgorithmName());

        int size = getOptimalApproximation().getSize();

        if ((size > 0) && (size <= 15)) {
            strBuffer.append(",x* = {");

            for (int i = 0; i < size; i++) {
                strBuffer.append(optimalApproximation.get(i));

                if ((i + 1) != size) strBuffer.append(",");
            }

            strBuffer.append("}");
        }

        return strBuffer.toString();
    }

    public boolean isSuccessfullyStopped() {
        return getStopCriteria().getStopCriteriaEnum().isSuccessfullyStopped();
    }

    public StopCriteriaQuality getStopCriteriaQuality() {
        return getStopCriteria().getStopCriteriaEnum().getQualityOfCriteria();
    }
}
