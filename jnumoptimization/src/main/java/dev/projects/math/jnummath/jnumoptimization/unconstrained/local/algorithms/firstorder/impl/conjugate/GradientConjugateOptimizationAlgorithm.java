package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.impl.conjugate;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.conjugate.IGradientConjugateStepStrategy;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.conjugate.IGradientConjugateUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.IFirstOrderDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.IFirstOrderLinearSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.IUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.AbstractFirstOrderUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.FirstOrderCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.FirstOrderOptimizationResults;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
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
                                                  IGradientConjugateStepStrategy stepStrategy)   {
        super(function, attributes, lsAlgorithm, dsAlgorithm);

        setStepStrategy(stepStrategy);
    }

    @Override
    public String getAlgorithmName() {
        return "Gradient conjugate optimization alg";
    }

    @Override
    protected void doOptimizationCycle()   {
        throw new LoggableException("Unsupported for conjugate gradient opt alg");
    }

    @Override
    public DenseVector computeDelta(int actualIterationCount)   {
        throw new LoggableException("Unsupported for conjugate gradient opt alg");
    }

    @Override
    public void setStepStrategy(IGradientConjugateStepStrategy stepStrategy)   {
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
                FirstOrderOptimizationResults> deepCopy()   {

        return new GradientConjugateOptimizationAlgorithm(
                (IScalarDifferentiableFunction) getFunction().deepCopy(),
                (FirstOrderCriticalOptimizationAttributes)getCriticalOptimizationAttributes().deepCopy(),
                (IFirstOrderLinearSearchAlgorithm)getLinearSearchAlgorithm().deepCopy(),
                (IFirstOrderDirectionSearchAlgorithm)getDirectionSearchAlgorithm().deepCopy(),
                getStrategy().deepCopy());
    }
}
