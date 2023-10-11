package dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.impl.learning.orthogonal;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.linalgebra.OrthogonalTransformationResultDto;
import dev.projects.math.optimization.unconstrained.local.algorithms.IUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.AbstractZeroOrderUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.learning.ZeroOrderLocalLearningCriticalOptimizationAttributes;
import dev.projects.math.optimization.unconstrained.local.base.StopCriteriaEnumeration;
import dev.projects.math.optimization.unconstrained.local.directionsearch.zeroorder.impl.learning.base.IZeroOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.zeroorder.impl.learning.orthogonal.IZeroOrderOrthogonalDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.base.CriticalGradientNormValueIsAchievedException;
import dev.projects.math.optimization.unconstrained.local.linearsearch.zeroorder.impl.learning.IZeroOrderLearningLinearSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.zeroorder.impl.learning.ZeroOrderLearningLinearSearchAlgorithmStateDto;
import dev.projects.math.optimization.unconstrained.local.linearsearch.zeroorder.impl.learning.ZeroOrderOptimalVariables;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;

public class ZeroOrderLearningOrthogonalUnconstrainedOptimizationAlgorithm
                extends AbstractZeroOrderUnconstrainedOptimizationAlgorithm<IZeroOrderLearningLinearSearchAlgorithm,
                        IZeroOrderOrthogonalDirectionSearchAlgorithm,
        ZeroOrderLocalLearningCriticalOptimizationAttributes>
                implements IZeroOrderLearningOrthogonalUnconstrainedOptimizationAlgorithm
{

    public ZeroOrderLearningOrthogonalUnconstrainedOptimizationAlgorithm(IScalarFunction function,
                                                                         ZeroOrderLocalLearningCriticalOptimizationAttributes attributes,
                                                                         IZeroOrderLearningLinearSearchAlgorithm lsAlgorithm,
                                                                         IZeroOrderOrthogonalDirectionSearchAlgorithm dsAlgorithm) throws LoggableException {
        super(function, attributes, lsAlgorithm, dsAlgorithm);
    }

    public ZeroOrderLearningOrthogonalUnconstrainedOptimizationAlgorithm(ZeroOrderLocalLearningCriticalOptimizationAttributes attributes,
                                                                         IZeroOrderLearningLinearSearchAlgorithm lsAlgorithm,
                                                                         IZeroOrderOrthogonalDirectionSearchAlgorithm dsAlgorithm) throws LoggableException {
        super(attributes, lsAlgorithm, dsAlgorithm);
    }

    @Override
    public String getAlgorithmName() {
        return "A Learning ZOOA {ls= "+getLinearSearchAlgorithm().getAlgorithmName()
                +",ds= "+getDirectionSearchAlgorithm().getAlgorithmName()+"}";
    }

    @Override
    public void initializeOptimizationProcess(IScalarFunction function) throws LoggableException {
        getDirectionSearchAlgorithm().initializeProcess(function);
        getLinearSearchAlgorithm().initializeProcess(function);
    }

    @Override
    protected void doOptimizationCycle() throws LoggableException {
        IScalarFunction fun = getFunction();
        IZeroOrderOrthogonalDirectionSearchAlgorithm dsAlgorithm = getDirectionSearchAlgorithm();
        IZeroOrderLearningLinearSearchAlgorithm lsAlgorithm = getLinearSearchAlgorithm();

        int variablesCount = fun.getVariablesCount();

        double qqsp = Math.exp(Math.log(0.2)/variablesCount);

        DenseVector y = DenseVector.getInstance(variablesCount, 0.0),
                sum = DenseVector.getInstance(variablesCount, 0.0);

        int localIterationCount = 0;
        boolean isEnd = false;

        while(true) {
            if (isEnd) break;

            localIterationCount++;
            y = (localIterationCount % 5 == 0) ? (sum.deepCopy()) : (DenseVector.getRandomizedVector(variablesCount, 0.5));

            final DenseVector finalY = y, finalSum = sum;

            Object[] result = dsAlgorithm.performMetricMatrixTransformation((matrix) -> {
                OrthogonalTransformationResultDto dto = matrix.doOrthogonalTransformationAtPlace(finalY,finalSum);

                return new Object[]{dto.getMatrix(),dto.getY(),dto.getSum()};
            });

            y = ((DenseVector) result[0]).deepCopy();
            sum = ((DenseVector) result[1]).deepCopy();

            for(int k=0; k < variablesCount; k++) {
                DenseVector currentDirection = dsAlgorithm.computeDirection(fun, getCurrentApproximation(), k);

                setIterationCount(getIterationCount() + 1);

                double step = 0.0;

                try {
                    step = lsAlgorithm.computeStep(fun, getCurrentApproximation(), currentDirection);
                } catch (CriticalGradientNormValueIsAchievedException e) {

                }

                ZeroOrderOptimalVariables optVariables = lsAlgorithm.getOptimalVariables();
                ZeroOrderLearningLinearSearchAlgorithmStateDto dto = lsAlgorithm.getState();

                setCurrentApproximation(getCurrentApproximation().deepCopy().add(step, currentDirection));

                double apsp = Math.sqrt(((dto.getF3() - dto.getF2())/(dto.getH3() - dto.getH2())
                        + (dto.getF1() - dto.getF2())/(dto.getH2() - dto.getH1()))/(dto.getH3() - dto.getH1()));

                if (apsp < 0.025) apsp = 0.025;
                if (apsp > 40.0) apsp = 40.0;

                apsp = 1.0/(apsp + 0.01);

                final double correctionCoeff = 0.5*(1.0 - apsp);
                final int finalK = k;

                dsAlgorithm.performMetricMatrixTransformation(matrix
                        -> new Object[]{matrix.multiplyRowByCoefficient(finalK,correctionCoeff)});

                sum.setValue(k, (sum.getValue(k)*qqsp + dto.getH2()*(1.0 - qqsp))/(correctionCoeff));

                if (checkStopCondition()) {
                    isEnd = true;
                    break;
                }
            }
        }

        fun.setVariables(getCurrentApproximation());
    }

    @Override
    public IUnconstrainedOptimizationAlgorithm<IScalarFunction,
            IZeroOrderLearningLinearSearchAlgorithm,
                IZeroOrderOrthogonalDirectionSearchAlgorithm,
            ZeroOrderLocalLearningCriticalOptimizationAttributes,
                ZeroOrderOptimizationResults> deepCopy() throws LoggableException {
        return new ZeroOrderLearningOrthogonalUnconstrainedOptimizationAlgorithm((IScalarFunction) getFunction().deepCopy(),
                (ZeroOrderLocalLearningCriticalOptimizationAttributes)getCriticalOptimizationAttributes().deepCopy(),
                (IZeroOrderLearningLinearSearchAlgorithm) getLinearSearchAlgorithm().deepCopy(),
                (IZeroOrderOrthogonalDirectionSearchAlgorithm) getDirectionSearchAlgorithm().deepCopy());
    }

    @Override
    public String toString() {
        return getAlgorithmName();
    }

    @Override
    protected boolean checkOtherCondition() throws LoggableException {
        IZeroOrderLearningDirectionSearchAlgorithm dsAlgorithm = getDirectionSearchAlgorithm();
        IZeroOrderLearningLinearSearchAlgorithm lsAlgorithm = getLinearSearchAlgorithm();

        ZeroOrderLocalLearningCriticalOptimizationAttributes attrb = getCriticalOptimizationAttributes();

        boolean condition = dsAlgorithm.getCurrentDirection().getEuclidNorm()*lsAlgorithm.getInitialStep()
                <= attrb.getCriticalStepValue();

        return checkCriteria(StopCriteriaEnumeration.STOPPED_BY_CRITICAL_LEARNING_EUCLID_DIRECTION_NORM,
                            "||s[k]||*Hn <= " + attrb.getCriticalStepValue(), condition);
    }

    @Override
    protected DenseVector computeDelta(int actualIterationCount) throws LoggableException {
        throw new LoggableException("A unsupported operation");
    }
}
