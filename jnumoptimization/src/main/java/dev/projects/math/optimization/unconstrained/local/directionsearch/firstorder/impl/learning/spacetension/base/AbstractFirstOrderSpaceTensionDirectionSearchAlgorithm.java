package dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.base;

import dev.projects.math.linalgebra.DenseMatrix;
import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.FirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionMatrixCorrectionOperator;
import dev.projects.math.optimization.unconstrained.local.linearsearch.firstorder.impl.learning.FirstOrderOptimalVariables;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public abstract class AbstractFirstOrderSpaceTensionDirectionSearchAlgorithm
        extends FirstOrderLearningDirectionSearchAlgorithm {

    @Getter
    private final double refreshableEpsilon = 1.0e-20;

    @Getter @Setter
    private DenseMatrix hMatrix;

    @Getter
    private ISpaceTensionMatrixCorrectionOperator matrixCorrectionOperator;

    public AbstractFirstOrderSpaceTensionDirectionSearchAlgorithm(int primeHash,
                                                                  ISpaceTensionMatrixCorrectionOperator stoOperator) throws LoggableException {
        super(primeHash);
        setMatrixCorrectionOperator(stoOperator);
    }

    @Override
    public void initializeProcess(IScalarDifferentiableFunction function) throws LoggableException {
        super.initializeProcess(function);
        setHMatrix(DenseMatrix.getSquareUnit(function.getVariablesCount()));
    }

    public void setMatrixCorrectionOperator(ISpaceTensionMatrixCorrectionOperator operator) throws LoggableException {
        if (operator == null) throw new LoggableException("Unacceptable matrix correction operator");

        matrixCorrectionOperator = operator;
    }

    @Override
    public double correctAlgorithm(int iterationCount,
                                   double initialStep,
                                   FirstOrderOptimalVariables optVariables,
                                   DenseVector currentPoint,
                                   DenseVector correctionGradient,
                                   IScalarDifferentiableFunction function) throws LoggableException {
        DenseVector correctedInternalDirection = doActualInternalDirectionCorrection(correctionGradient,
                getHMatrix(),
                currentPoint,
                function);

        DenseMatrix correctedMatrix =
                getMatrixCorrectionOperator().correctMatrix(currentPoint,
                        correctionGradient,
                        getInternalDirection(),
                        getHMatrix(),
                        function);

        setHMatrix(correctedMatrix.deepCopy());
        setInternalDirection(correctedInternalDirection.deepCopy());

        return getInitialStep(initialStep);
    }

    protected abstract DenseVector doActualInternalDirectionCorrection(DenseVector correctionGradient,
                                                                       DenseMatrix currentMatrix,
                                                                       DenseVector currentPoint,
                                                                       IScalarDifferentiableFunction function) throws LoggableException;

    protected double getInitialStep(double currentInitialStep) {
        double maxElement = getHMatrix().getMaxElement();

        if (maxElement < 0.1*getRefreshableEpsilon()) {
            maxElement = 1.0/maxElement;

            getHMatrix().multiplyByCoefficient(maxElement);

            return currentInitialStep/Math.sqrt(maxElement);
        }

        return currentInitialStep;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrimeHashCode(), getMatrixCorrectionOperator());
    }
}
