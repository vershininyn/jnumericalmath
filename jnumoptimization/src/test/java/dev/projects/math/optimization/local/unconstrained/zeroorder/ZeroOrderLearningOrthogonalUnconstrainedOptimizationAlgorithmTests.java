package dev.projects.math.optimization.local.unconstrained.zeroorder;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.learning.IZeroOrderLearningUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.learning.ZeroOrderLocalLearningCriticalOptimizationAttributes;
import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.impl.learning.orthogonal.ZeroOrderLearningOrthogonalUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.zeroorder.impl.learning.orthogonal.IZeroOrderOrthogonalDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.zeroorder.impl.learning.orthogonal.ZeroOrderOrthogonalDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.zeroorder.impl.learning.IZeroOrderLearningLinearSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.zeroorder.impl.learning.ZeroOrderLearningRandomLinearSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
import dev.projects.math.transformations.function.impl.GeneralizedRosenbrockScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ZeroOrderLearningOrthogonalUnconstrainedOptimizationAlgorithmTests {

    @Test
    public void zeroOrderLearningOrthogonalUnconstrainedOptimizationAlgorithmTest() throws LoggableException {
        IScalarFunction fun = new GeneralizedRosenbrockScalarDifferentiableFunction(DenseVector.getRandomizedVector(2,10));

        ZeroOrderLocalLearningCriticalOptimizationAttributes attrb =
                ZeroOrderLocalLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        IZeroOrderLearningLinearSearchAlgorithm lsAlgorithm =
                new ZeroOrderLearningRandomLinearSearchAlgorithm(15.0);
        IZeroOrderOrthogonalDirectionSearchAlgorithm dsAlgorithm =
                new ZeroOrderOrthogonalDirectionSearchAlgorithm();

        IZeroOrderLearningUnconstrainedOptimizationAlgorithm optAlgorithm =
                new ZeroOrderLearningOrthogonalUnconstrainedOptimizationAlgorithm(fun,attrb,lsAlgorithm,dsAlgorithm);

        ZeroOrderOptimizationResults result = optAlgorithm.minimize();
        DenseVector optimum = result.getOptimalApproximation();

        Assertions.assertTrue(Math.abs(1.0 - optimum.get(0)) <= 1.0E-02);
        Assertions.assertTrue(Math.abs(1.0 - optimum.get(1)) <= 1.0E-02);
    }
}
