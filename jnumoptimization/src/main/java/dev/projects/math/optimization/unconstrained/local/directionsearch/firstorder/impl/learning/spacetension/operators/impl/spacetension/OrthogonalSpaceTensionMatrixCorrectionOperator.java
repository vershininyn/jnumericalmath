package dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.impl.spacetension;

import dev.projects.math.linalgebra.DenseMatrix;
import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionMatrixCorrectionOperator;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;

import java.util.Objects;

public class OrthogonalSpaceTensionMatrixCorrectionOperator
                implements ISpaceTensionMatrixCorrectionOperator {

    private int primeHashCode = 101;

    @Getter
    private double alphaTensionCoefficient = 0.0;

    public OrthogonalSpaceTensionMatrixCorrectionOperator(double alphaCoefficient) throws LoggableException {
        if (alphaCoefficient <= 0.0) throw new LoggableException("Unacceptable alpha coefficient");

        alphaTensionCoefficient = alphaCoefficient;
    }

    @Override
    public DenseMatrix correctMatrix(DenseVector currentPoint,
                                     DenseVector correctionGradient,
                                     DenseVector internalDirection,
                                     DenseMatrix matrix,
                                     IScalarDifferentiableFunction function) throws LoggableException {

        DenseMatrix correctedMatrix = matrix.deepCopy();

        DenseVector grad = function.computeGradient(currentPoint),
                hgx = matrix.multiplyByVector(grad),
                hg1 = matrix.multiplyByVector(correctionGradient),
                y = grad.deepCopy().substract(correctionGradient),
                hy = hgx.deepCopy().substract(hg1);

        double yhy = y.getInnerProduct(hy) + 0.0000001,
                cCoeff = (-1.0)*((grad.getInnerProduct(hg1))/(y.getInnerProduct(hg1) + 0.000001));

        DenseVector v = grad.deepCopy().add(cCoeff, y),
                hv = hgx.deepCopy().add(cCoeff, hy);

        double vhv = Math.max(v.getInnerProduct(hv), 0.0),
                rCoeff = 1.0 + 2.0*((getAlphaTensionCoefficient() - 1.0)/(1.0 + vhv/(yhy + 0.0000001))),
                sqrtCoeff = Math.sqrt(Math.max((1.0 - 1.0/rCoeff)/(yhy + 0.001), 0.0));

        hy = hy.multiplyByCoefficient(sqrtCoeff);

        DenseVector hyColumn = hy.deepCopy().convertToColumnVector(),
                hyRow = hy.deepCopy().convertToRowVector();

        correctedMatrix.substract(hyColumn.multiplyByVector(hyRow));

        return correctedMatrix;
    }

    @Override
    public String getAlgorithmName() {
        return "A orthogonal alpha sto {alpha="+getAlphaTensionCoefficient()+"}";
    }

    @Override
    public ISpaceTensionMatrixCorrectionOperator deepCopy() throws LoggableException {
        return new OrthogonalSpaceTensionMatrixCorrectionOperator(getAlphaTensionCoefficient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(primeHashCode, getAlphaTensionCoefficient());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        OrthogonalSpaceTensionMatrixCorrectionOperator alg = (OrthogonalSpaceTensionMatrixCorrectionOperator)(obj);

        return hashCode() == alg.hashCode();
    }
}
