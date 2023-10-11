package dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.base;

import dev.projects.math.linalgebra.DenseMatrix;
import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionMatrixCorrectionOperator;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionInternalDirectionCorrectionOperator;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;

import java.util.Objects;

public abstract class AbstractFirstOrderSpaceTensionDirectionSearchAlgorithmWithIDCOperator
        extends AbstractFirstOrderSpaceTensionDirectionSearchAlgorithm {

    @Getter
    private ISpaceTensionInternalDirectionCorrectionOperator internalDirectionCorrectionOperator;

    public AbstractFirstOrderSpaceTensionDirectionSearchAlgorithmWithIDCOperator(int primeHashCode,
                                                                                 ISpaceTensionMatrixCorrectionOperator stoOperator,
                                                                                 ISpaceTensionInternalDirectionCorrectionOperator idcOperator) throws LoggableException {
        super(primeHashCode, stoOperator);
        setInternalDirectionCorrectionOperator(idcOperator);
    }

    public void setInternalDirectionCorrectionOperator(ISpaceTensionInternalDirectionCorrectionOperator operator) throws LoggableException {
        if (operator == null) throw new LoggableException("Unacceptable a internal direction correction operator");

        internalDirectionCorrectionOperator = operator;
    }


    @Override
    protected DenseVector doActualInternalDirectionCorrection(DenseVector correctionGradient,
                                                              DenseMatrix currentMatrix,
                                                              DenseVector currentPoint,
                                                              IScalarDifferentiableFunction function) throws LoggableException {
        return getInternalDirectionCorrectionOperator().correctInternalDirection(getInternalDirection(),
                                                                                 correctionGradient,
                                                                                    currentMatrix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrimeHashCode(), getMatrixCorrectionOperator(), getInternalDirectionCorrectionOperator());
    }
}
