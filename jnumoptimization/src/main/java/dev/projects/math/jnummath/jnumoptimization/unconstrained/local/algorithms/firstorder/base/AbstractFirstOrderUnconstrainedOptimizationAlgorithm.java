package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.UnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.base.StopCriteriaEnumeration;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.IFirstOrderDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.IFirstOrderLinearSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

import java.time.Duration;

public abstract class AbstractFirstOrderUnconstrainedOptimizationAlgorithm<TLinearSearchAlgorithm extends IFirstOrderLinearSearchAlgorithm,
                                                                    TDirectionSearchAlgorithm extends IFirstOrderDirectionSearchAlgorithm,
                                                                    TCriticalOptimizationAttributes extends FirstOrderCriticalOptimizationAttributes>
        extends UnconstrainedOptimizationAlgorithm<IScalarDifferentiableFunction,
                                                                            TLinearSearchAlgorithm,
                                                                            TDirectionSearchAlgorithm,
                                                                            TCriticalOptimizationAttributes,
                                                                            FirstOrderOptimizationResults>

{


    public AbstractFirstOrderUnconstrainedOptimizationAlgorithm(IScalarDifferentiableFunction function,
                                                                TCriticalOptimizationAttributes attributes,
                                                                TLinearSearchAlgorithm lsAlgorithm,
                                                                TDirectionSearchAlgorithm dsAlgorithm)   {
        super(function, attributes, lsAlgorithm, dsAlgorithm);
    }

    public AbstractFirstOrderUnconstrainedOptimizationAlgorithm(TCriticalOptimizationAttributes attributes,
                                                                TLinearSearchAlgorithm lsAlgorithm,
                                                                TDirectionSearchAlgorithm dsAlgorithm)   {
        super(attributes, lsAlgorithm, dsAlgorithm);
    }

    @Override
    public boolean checkOtherCondition()   {
        IScalarDifferentiableFunction fun = getFunction();
        FirstOrderCriticalOptimizationAttributes attrb = getCriticalOptimizationAttributes();

        long gradientComputeCount = fun.getGradientComputeCount();
        double gradNorm = fun.computeGradient().getEuclidNorm();

        return checkCriteria(StopCriteriaEnumeration.STOPPED_BY_GRADIENT_COMPUTE_COUNT,
                "gradCompCount >= " + attrb.getMaxGradientComputeCount(),
                gradientComputeCount >= attrb.getMaxGradientComputeCount());

        /*return checkCriteria(StopCriteriaEnumeration.STOPPED_BY_GRADIENT_COMPUTE_COUNT,
                    "gradCompCount >= " + attrb.getMaxGradientComputeCount(),
                        gradientComputeCount >= attrb.getMaxGradientComputeCount())
                || checkCriteria(StopCriteriaEnumeration.STOPPED_BY_CRITICAL_EUCLID_GRADIENT_NORM,
                                "||G[k]|| <= " + attrb.getCriticalGradientEuclidNormEpsilon(),
                        gradNorm <= attrb.getCriticalGradientEuclidNormEpsilon());
         */
    }

    @Override
    public FirstOrderOptimizationResults getOptimizationResults(int actualIterationCount, Duration milliseconds)   {
        IScalarDifferentiableFunction fun = getFunction();

        return new FirstOrderOptimizationResults(actualIterationCount,
                fun.getOutputComputeCount(),
                getRepeatedFunctionOutputCount(),
                milliseconds,
                fun.computeOutput(),
                fun.getGradientComputeCount(),
                fun.getVariables(),
                getStopCriteria(),
                getAlgorithmName());
    }
}
