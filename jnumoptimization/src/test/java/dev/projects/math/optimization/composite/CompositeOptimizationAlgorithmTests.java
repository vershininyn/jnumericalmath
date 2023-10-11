package dev.projects.math.optimization.composite;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.composite.CompositeOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.global.base.SimpleHyperCube;
import dev.projects.math.optimization.unconstrained.global.zeroorder.base.ZeroOrderGlobalCriticalOptimizationAttributes;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.FirstOrderOptimizationResults;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.learning.FirstOrderLearningCriticalOptimizationAttributes;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.FirstOrderPairwiseDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.firstorder.impl.learning.IFirstOrderLearningLinearSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.firstorder.impl.learning.LearningFirstOrderLinearSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.math.transformations.function.impl.GeneralizedRosenbrockScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CompositeOptimizationAlgorithmTests {
    @Test
    public void compositeOptimizationTest() throws LoggableException {
        IScalarDifferentiableFunction fun = new GeneralizedRosenbrockScalarDifferentiableFunction(DenseVector.getInstance(4, 0.0));

        FirstOrderLearningCriticalOptimizationAttributes
                attrb = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-06);
        IFirstOrderLearningLinearSearchAlgorithm
                lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(0.5, 0.8, 3.0);
        //ISpaceTensionMatrixCorrectionOperator
        //        alphaBetaOperator = new AlphaBetaSpaceTensionMatrixCorrectionOperator(30.0, 1.0/5.0);
        IFirstOrderLearningDirectionSearchAlgorithm
                dsAlgorithm = new FirstOrderPairwiseDirectionSearchAlgorithm();

        SimpleHyperCube cube = SimpleHyperCube.getInstance(10.0, DenseVector.getInstance(4, 0.0));

        CompositeOptimizationAlgorithm compositeAlg = new CompositeOptimizationAlgorithm(fun,attrb,lsAlgorithm,dsAlgorithm, cube);

        ZeroOrderGlobalCriticalOptimizationAttributes
                zeroOrderAttrb = ZeroOrderGlobalCriticalOptimizationAttributes.getStandartSet(0.0,
                1.0E-01,
                1.0E-02);

        FirstOrderOptimizationResults results = compositeAlg.doCompositeMinimization(zeroOrderAttrb, 10.0, 2.4, 5.0);

        System.out.println(results);

        Assertions.assertTrue(results.isSuccessfullyStopped());
    }
}
