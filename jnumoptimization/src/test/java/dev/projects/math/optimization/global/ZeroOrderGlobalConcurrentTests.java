package dev.projects.math.optimization.global;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.global.base.SimpleHyperCube;
import dev.projects.math.optimization.unconstrained.global.zeroorder.base.ZeroOrderGlobalCriticalOptimizationAttributes;
import dev.projects.math.optimization.unconstrained.global.zeroorder.concurrent.ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationMaster;
import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
import dev.projects.math.transformations.function.impl.GeneralizedRosenbrockScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ZeroOrderGlobalConcurrentTests {

    @Test
    public void globalConcurrentTest() throws LoggableException {
        IScalarFunction fun = new GeneralizedRosenbrockScalarDifferentiableFunction(DenseVector.getInstance(2, -5.0));

        double edgeLength = 50.0;

        SimpleHyperCube originalCube = SimpleHyperCube.getInstance(edgeLength, DenseVector.getInstance(2, 0.0));

        ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationMaster
                master = new ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationMaster(fun, originalCube);

        ZeroOrderGlobalCriticalOptimizationAttributes
                attrb = ZeroOrderGlobalCriticalOptimizationAttributes.getStandartSet(0.0,
                1.0E-01,
                1.0E-02);

        ZeroOrderOptimizationResults result = master.doConcurrentGlobalMinimization(attrb, 2, edgeLength, 1.2, 5.0);

        System.out.println(result);

        Assertions.assertTrue(result.isSuccessfullyStopped());
    }

}
