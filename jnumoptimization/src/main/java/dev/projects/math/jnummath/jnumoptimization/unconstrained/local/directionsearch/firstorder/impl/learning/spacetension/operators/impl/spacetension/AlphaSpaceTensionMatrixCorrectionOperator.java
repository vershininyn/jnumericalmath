package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.impl.spacetension;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseMatrix;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionMatrixCorrectionOperator;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

import java.util.Objects;

public class AlphaSpaceTensionMatrixCorrectionOperator
        implements ISpaceTensionMatrixCorrectionOperator {

    private final int primeHashCode = 79;
    private final double alphaTensionCoefficient, epsilon = 1.0e-20;

    public AlphaSpaceTensionMatrixCorrectionOperator(double alphaCoefficient)   {
        if (alphaCoefficient <= 1.0) throw new LoggableException("Unacceptable alpha coefficient");

        alphaTensionCoefficient = alphaCoefficient;
    }

    @Override
    public DenseMatrix correctMatrix(DenseVector currentPoint,
                                     DenseVector correctionGradient,
                                     DenseVector internalDirection,
                                     DenseMatrix matrix,
                                     IScalarDifferentiableFunction function)   {
        DenseMatrix correctedMatrix = matrix.deepCopy();

        DenseVector hg1 = correctedMatrix.refreshableMultiply(epsilon, 10.0, correctionGradient);

        double ghg = hg1.getInnerProduct(correctionGradient),
                rCoeff = Math.sqrt(Math.max((1.0 - 1.0/alphaTensionCoefficient)/(ghg + 0.0000001), 0.0));

        hg1 = hg1.multiplyByCoefficient(rCoeff);

        DenseVector rColumn = hg1.deepCopy().convertToColumnVector(),
                rRow = hg1.deepCopy().convertToRowVector();

        return correctedMatrix.substract(rColumn.multiplyByVector(rRow));
    }

    @Override
    public String getAlgorithmName() {
        return "A alpha sto {alpha="+alphaTensionCoefficient+"}";
    }

    @Override
    public ISpaceTensionMatrixCorrectionOperator deepCopy()   {
        return new AlphaSpaceTensionMatrixCorrectionOperator(alphaTensionCoefficient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primeHashCode, alphaTensionCoefficient);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        AlphaSpaceTensionMatrixCorrectionOperator alg = (AlphaSpaceTensionMatrixCorrectionOperator)(obj);

        return hashCode() == alg.hashCode();
    }
}
