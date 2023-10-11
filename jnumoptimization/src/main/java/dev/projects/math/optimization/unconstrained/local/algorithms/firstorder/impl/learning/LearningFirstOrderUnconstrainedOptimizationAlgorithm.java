package dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.impl.learning;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.algorithms.IUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.AbstractFirstOrderUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.FirstOrderOptimizationResults;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.learning.FirstOrderLearningCriticalOptimizationAttributes;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.learning.IFirstOrderLearningUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.base.StopCriteriaEnumeration;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.base.CriticalGradientNormValueIsAchievedException;
import dev.projects.math.optimization.unconstrained.local.linearsearch.firstorder.impl.learning.FirstOrderOptimalVariables;
import dev.projects.math.optimization.unconstrained.local.linearsearch.firstorder.impl.learning.IFirstOrderLearningLinearSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

import java.util.Objects;

public class LearningFirstOrderUnconstrainedOptimizationAlgorithm
        extends AbstractFirstOrderUnconstrainedOptimizationAlgorithm<IFirstOrderLearningLinearSearchAlgorithm,
        IFirstOrderLearningDirectionSearchAlgorithm,
                FirstOrderLearningCriticalOptimizationAttributes>
        implements IFirstOrderLearningUnconstrainedOptimizationAlgorithm {

    public LearningFirstOrderUnconstrainedOptimizationAlgorithm(IScalarDifferentiableFunction function,
                                                                FirstOrderLearningCriticalOptimizationAttributes attributes,
                                                                IFirstOrderLearningLinearSearchAlgorithm lsAlgorithm,
                                                                IFirstOrderLearningDirectionSearchAlgorithm dsAlgorithm) throws LoggableException {
        super(function,attributes,lsAlgorithm,dsAlgorithm);
    }

    public LearningFirstOrderUnconstrainedOptimizationAlgorithm(FirstOrderLearningCriticalOptimizationAttributes attributes,
                                                                IFirstOrderLearningLinearSearchAlgorithm lsAlgorithm,
                                                                IFirstOrderLearningDirectionSearchAlgorithm dsAlgorithm) throws LoggableException {
        super(attributes,lsAlgorithm,dsAlgorithm);
    }

    @Override
    public String getAlgorithmName() {
        return "A Learning FOOA {ls= "+getLinearSearchAlgorithm().getAlgorithmName()+",ds= "+getDirectionSearchAlgorithm().getAlgorithmName()+"}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLinearSearchAlgorithm(), getDirectionSearchAlgorithm());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        LearningFirstOrderUnconstrainedOptimizationAlgorithm otherAlg = (LearningFirstOrderUnconstrainedOptimizationAlgorithm) obj;

        return ((getDirectionSearchAlgorithm().equals(otherAlg.getDirectionSearchAlgorithm()))
                && (getLinearSearchAlgorithm().equals(otherAlg.getLinearSearchAlgorithm())));
    }

    @Override
    public String toString() {
        return getAlgorithmName();
    }

    @Override
    public void initializeOptimizationProcess(IScalarDifferentiableFunction function) throws LoggableException {
        getDirectionSearchAlgorithm().initializeProcess(function);
        getLinearSearchAlgorithm().initializeProcess(function);
    }

    @Override
    public DenseVector computeDelta(int actualIterationCount) throws LoggableException {
        IScalarDifferentiableFunction fun = getFunction();
        IFirstOrderLearningLinearSearchAlgorithm lsAlg = getLinearSearchAlgorithm();
        IFirstOrderLearningDirectionSearchAlgorithm dsAlg = getDirectionSearchAlgorithm();

        DenseVector currentDirection = dsAlg.computeDirection(fun, getCurrentApproximation());

        double step = 0.0;

        try {
            step = lsAlg.computeStep(fun, getCurrentApproximation(), currentDirection);
        } catch(CriticalGradientNormValueIsAchievedException e) {
            super.checkStopCondition();
        }

        //System.out.println("step= "+step+", cdNorm= "+currentDirection.getEuclidNorm());

        double initialStep = dsAlg.correctAlgorithm(actualIterationCount,
                        lsAlg.getInitialStep(),
                        lsAlg.getOptimalVariables(),
                        getCurrentApproximation(),
                        lsAlg.getCorrectionGradient(),
                        fun);

        lsAlg.setInitialStep(initialStep);

        return currentDirection.deepCopy().multiplyByCoefficient(step);
    }

    @Override
    public boolean checkOtherCondition() throws LoggableException {
        FirstOrderLearningCriticalOptimizationAttributes attrb = getCriticalOptimizationAttributes();
        FirstOrderOptimalVariables optVars = getLinearSearchAlgorithm().getOptimalVariables();
        DenseVector currentDirection = getDirectionSearchAlgorithm().getCurrentDirection();

        double gradientEuclidNormCriteria = optVars.getGm().getEuclidNorm()*optVars.getHm(),
                directionEuclidNormCriteria = currentDirection.getEuclidNorm()*optVars.getHm();

        return (super.checkOtherCondition()
                || checkCriteria(StopCriteriaEnumeration.STOPPED_BY_CRITICAL_LEARNING_EUCLID_GRADIENT_NORM,
                    "||Grad[k]||*Hm <= "+attrb.getEpsilonGradientCriticalValue(),
                        gradientEuclidNormCriteria <= attrb.getEpsilonGradientCriticalValue())
                || checkCriteria(StopCriteriaEnumeration.STOPPED_BY_CRITICAL_LEARNING_EUCLID_DIRECTION_NORM,
                    "||Direction[k]||*Hm <= "+attrb.getCriticalStepValue(),
                        directionEuclidNormCriteria <= attrb.getCriticalStepValue()));
    }

    @Override
    protected boolean checkFunctionOutput(IScalarDifferentiableFunction function, FirstOrderLearningCriticalOptimizationAttributes attributes) {
        FirstOrderOptimalVariables optVars = getLinearSearchAlgorithm().getOptimalVariables();

        double criteria = Math.abs(optVars.getFm() - attributes.getExpectedExtremum());

        return checkCriteria(StopCriteriaEnumeration.STOPPED_BY_CRITICAL_FUNCTION_VALUE,
                "|Fm - F*| <= "+attributes.getEpsilonFunctionCriticalOutput(),
                    criteria <= attributes.getEpsilonFunctionCriticalOutput());
    }

    @Override
    public IUnconstrainedOptimizationAlgorithm<IScalarDifferentiableFunction,
                IFirstOrderLearningLinearSearchAlgorithm,
            IFirstOrderLearningDirectionSearchAlgorithm,
                FirstOrderLearningCriticalOptimizationAttributes,
                FirstOrderOptimizationResults> deepCopy() throws LoggableException {
        return new LearningFirstOrderUnconstrainedOptimizationAlgorithm(
                (FirstOrderLearningCriticalOptimizationAttributes) getCriticalOptimizationAttributes().deepCopy(),
                (IFirstOrderLearningLinearSearchAlgorithm) getLinearSearchAlgorithm().deepCopy(),
                (IFirstOrderLearningDirectionSearchAlgorithm) getDirectionSearchAlgorithm().deepCopy());
    }
}
