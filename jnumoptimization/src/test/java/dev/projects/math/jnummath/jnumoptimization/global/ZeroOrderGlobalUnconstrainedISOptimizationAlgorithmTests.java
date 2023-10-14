package dev.projects.math.jnummath.jnumoptimization.global;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.learning.FirstOrderLearningCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.learning.IFirstOrderLearningUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.global.base.SimpleHyperCube;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.global.zeroorder.ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.global.zeroorder.base.IZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.global.zeroorder.base.ZeroOrderGlobalCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.FirstOrderOptimizationResults;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.impl.learning.LearningFirstOrderUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.FirstOrderPairwiseDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.impl.learning.IFirstOrderLearningLinearSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.impl.learning.LearningFirstOrderLinearSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.GeneralizedRosenbrockScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ZeroOrderGlobalUnconstrainedISOptimizationAlgorithmTests {

    @Test
    public void globalOptimizationAlgorithmTest()   {
        globalOptimizationPhase();
    }

    @Test
    public void compositeOptimizationTest()   {
        DenseVector startPoint= globalOptimizationPhase();

        IScalarDifferentiableFunction fun = getFunction(startPoint);

        FirstOrderLearningCriticalOptimizationAttributes
                attrb = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-06);
        IFirstOrderLearningLinearSearchAlgorithm
                lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(0.5, 0.8, 3.0);
        IFirstOrderLearningDirectionSearchAlgorithm
                dsAlgorithm = new FirstOrderPairwiseDirectionSearchAlgorithm();

        IFirstOrderLearningUnconstrainedOptimizationAlgorithm
                optAlgortihm = new LearningFirstOrderUnconstrainedOptimizationAlgorithm(fun, attrb, lsAlgorithm, dsAlgorithm);

        FirstOrderOptimizationResults results = optAlgortihm.minimize();

        Assertions.assertTrue(results.isSuccessfullyStopped());
    }

    private DenseVector globalOptimizationPhase()   {
        IScalarDifferentiableFunction fun = getFunction(DenseVector.getInstance(2, -5.0));

        ZeroOrderGlobalCriticalOptimizationAttributes
                attrb = ZeroOrderGlobalCriticalOptimizationAttributes.getStandartSet(0.0,
                1.0E-01,
                1.0E-02);

        SimpleHyperCube cube = SimpleHyperCube.getInstance(10.0, DenseVector.getInstance(2, 0.0));

        IZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm
                optAlg = new ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm(2.4, 10.0, fun, attrb, cube);

        ZeroOrderOptimizationResults results = optAlg.minimize();

        System.out.println(results);

        Assertions.assertTrue(results.isSuccessfullyStopped());

        return results.getOptimalApproximation();
    }

    private IScalarDifferentiableFunction getFunction(DenseVector startPoint)   {
        return new GeneralizedRosenbrockScalarDifferentiableFunction(startPoint);
    }
}
