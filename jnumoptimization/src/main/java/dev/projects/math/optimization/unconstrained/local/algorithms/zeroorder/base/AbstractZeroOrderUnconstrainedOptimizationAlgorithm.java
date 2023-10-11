package dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base;

import dev.projects.math.optimization.unconstrained.local.algorithms.UnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.zeroorder.IZeroOrderDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.zeroorder.IZeroOrderLinearSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;

import java.time.Duration;

public abstract class AbstractZeroOrderUnconstrainedOptimizationAlgorithm<TLinearSearchAlgoritm extends IZeroOrderLinearSearchAlgorithm,
                                                            TDirectionSearchAlgorithm extends IZeroOrderDirectionSearchAlgorithm,
                                                            TCriticalOptimizationAttributes extends ZeroOrderLocalCriticalOptimizationAttributes>
                extends UnconstrainedOptimizationAlgorithm<IScalarFunction,
                                                                            TLinearSearchAlgoritm,
                                                                            TDirectionSearchAlgorithm,
                                                                            TCriticalOptimizationAttributes,
                                                                            ZeroOrderOptimizationResults>
{


    public AbstractZeroOrderUnconstrainedOptimizationAlgorithm(IScalarFunction function,
                                                               TCriticalOptimizationAttributes attributes,
                                                               TLinearSearchAlgoritm lsAlgorithm,
                                                               TDirectionSearchAlgorithm dsAlgorithm) throws LoggableException {
        super(function, attributes, lsAlgorithm, dsAlgorithm);
    }

    public AbstractZeroOrderUnconstrainedOptimizationAlgorithm(TCriticalOptimizationAttributes attributes,
                                                               TLinearSearchAlgoritm lsAlgorithm,
                                                               TDirectionSearchAlgorithm dsAlgorithm) throws LoggableException {
        super(attributes, lsAlgorithm, dsAlgorithm);
    }

    @Override
    protected int getIterationDenominator() {
        return getFunction().getVariablesCount();
    }

    @Override
    public ZeroOrderOptimizationResults getOptimizationResults(int actualIterationCount, Duration period) throws LoggableException {
        IScalarFunction fun = getFunction();

        return new ZeroOrderOptimizationResults(actualIterationCount,
                fun.getOutputComputeCount(),
                getRepeatedFunctionOutputCount(),
                fun.computeOutput(),
                getAlgorithmName(),
                period,
                getCurrentApproximation(),
                getStopCriteria());
    }
}
