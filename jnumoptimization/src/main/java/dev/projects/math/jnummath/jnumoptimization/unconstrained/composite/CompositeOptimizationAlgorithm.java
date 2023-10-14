package dev.projects.math.jnummath.jnumoptimization.unconstrained.composite;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.global.zeroorder.concurrent.ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationMaster;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.learning.FirstOrderLearningCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.learning.IFirstOrderLearningUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.global.base.SimpleHyperCube;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.global.zeroorder.base.ZeroOrderGlobalCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.FirstOrderOptimizationResults;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.impl.learning.LearningFirstOrderUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.impl.learning.IFirstOrderLearningLinearSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;

public class CompositeOptimizationAlgorithm {

    private ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationMaster globalMaster = null;

    private IFirstOrderLearningUnconstrainedOptimizationAlgorithm localOptAlgorithm = null;

    public CompositeOptimizationAlgorithm(IScalarDifferentiableFunction function,
                                          FirstOrderLearningCriticalOptimizationAttributes attributes,
                                          IFirstOrderLearningLinearSearchAlgorithm lsAlgorithm,
                                          IFirstOrderLearningDirectionSearchAlgorithm dsAlgorithm,
                                          SimpleHyperCube cube)   {
        if (cube == null) throw new LoggableException("Unacceptable cube");
        if (function == null) throw new LoggableException("Unacceptable function");

        function.reset();

        localOptAlgorithm = new LearningFirstOrderUnconstrainedOptimizationAlgorithm((IScalarDifferentiableFunction) function.deepCopy(),
                attributes,
                lsAlgorithm,
                dsAlgorithm);

        globalMaster = new ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationMaster((IScalarFunction) function.deepCopy(), cube);
    }

    public FirstOrderOptimizationResults doCompositeMinimization(ZeroOrderGlobalCriticalOptimizationAttributes zeroOrderAttributes,
                                                                 double cubeEdgeLength,
                                                                 double rConstant,
                                                                 double cConstant)   {
        ZeroOrderOptimizationResults globalResults = globalMaster.doConcurrentGlobalMinimization(zeroOrderAttributes,
                2,
                cubeEdgeLength,
                rConstant,
                cConstant);

        localOptAlgorithm.getFunction().setVariables(globalResults.getOptimalApproximation());

        return localOptAlgorithm.minimize();
    }
}
