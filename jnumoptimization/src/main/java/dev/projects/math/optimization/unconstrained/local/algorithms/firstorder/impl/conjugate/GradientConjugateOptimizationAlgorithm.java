package dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.impl.conjugate;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.algorithms.IUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.AbstractFirstOrderUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.FirstOrderCriticalOptimizationAttributes;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.FirstOrderOptimizationResults;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.conjugate.IGradientConjugateStepStrategy;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.conjugate.IGradientConjugateUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.IFirstOrderDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.firstorder.IFirstOrderLinearSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class GradientConjugateOptimizationAlgorithm
        extends AbstractFirstOrderUnconstrainedOptimizationAlgorithm<IFirstOrderLinearSearchAlgorithm,
                IFirstOrderDirectionSearchAlgorithm,
                FirstOrderCriticalOptimizationAttributes>
        implements IGradientConjugateUnconstrainedOptimizationAlgorithm
{

    private IGradientConjugateStepStrategy stepStrategy;

    public GradientConjugateOptimizationAlgorithm(IScalarDifferentiableFunction function,
                                                  FirstOrderCriticalOptimizationAttributes attributes,
                                                  IFirstOrderLinearSearchAlgorithm lsAlgorithm,
                                                  IFirstOrderDirectionSearchAlgorithm dsAlgorithm,
                                                  IGradientConjugateStepStrategy stepStrategy) throws LoggableException {
        super(function, attributes, lsAlgorithm, dsAlgorithm);

        setStepStrategy(stepStrategy);
    }

    @Override
    public String getAlgorithmName() {
        return "Gradient conjugate optimization alg";
    }

    @Override
    protected void doOptimizationCycle() throws LoggableException {
        throw new LoggableException("Unsupported for conjugate gradient opt alg");
    }

    @Override
    public DenseVector computeDelta(int actualIterationCount) throws LoggableException {
        throw new LoggableException("Unsupported for conjugate gradient opt alg");
    }

    @Override
    public void setStepStrategy(IGradientConjugateStepStrategy stepStrategy) throws LoggableException {
        if (stepStrategy == null) throw new LoggableException("Unacceptable step strategy");

        this.stepStrategy = stepStrategy;
    }

    @Override
    public IGradientConjugateStepStrategy getStrategy() {
        return this.stepStrategy;
    }

    @Override
    public IUnconstrainedOptimizationAlgorithm<IScalarDifferentiableFunction,
                IFirstOrderLinearSearchAlgorithm,
                IFirstOrderDirectionSearchAlgorithm,
                FirstOrderCriticalOptimizationAttributes,
                FirstOrderOptimizationResults> deepCopy() throws LoggableException {

        return new GradientConjugateOptimizationAlgorithm(
                (IScalarDifferentiableFunction) getFunction().deepCopy(),
                (FirstOrderCriticalOptimizationAttributes)getCriticalOptimizationAttributes().deepCopy(),
                (IFirstOrderLinearSearchAlgorithm)getLinearSearchAlgorithm().deepCopy(),
                (IFirstOrderDirectionSearchAlgorithm)getDirectionSearchAlgorithm().deepCopy(),
                getStrategy().deepCopy());
    }
}
