package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.impl.spacetension;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseMatrix;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionMatrixCorrectionOperator;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;

import java.util.Objects;

public class AlphaBetaSpaceTensionMatrixCorrectionOperator
        implements ISpaceTensionMatrixCorrectionOperator {

    private final int primeHashCode = 73;

    @Getter
    private double alphaTensionCoeff,
            betaTensionCoeff,
            epsilon = 1.0e-20,
            gamma = 0.00001,
            delta = 0.003;

    public AlphaBetaSpaceTensionMatrixCorrectionOperator(double alphaTensionCoefficient,
                                                         double betaTensionCoefficient)   {
        if (alphaTensionCoefficient <= 1.0) throw new LoggableException("Unacceptable alpha coefficient");
        if ((betaTensionCoefficient < 0.0) || (betaTensionCoefficient > 1.0)) throw new LoggableException("Unacceptable beta coefficient");
        if (alphaTensionCoefficient*betaTensionCoefficient <= 1.0) throw new LoggableException("Unacceptable alpha and beta coefficient");

        alphaTensionCoeff = alphaTensionCoefficient;
        betaTensionCoeff = betaTensionCoefficient;
    }

    @Override
    public DenseMatrix correctMatrix(DenseVector currentPoint,
                                     DenseVector correctionGradient,
                                     DenseVector internalDirection,
                                     DenseMatrix matrix,
                                     IScalarDifferentiableFunction function)   {
        DenseMatrix correctedMatrix = matrix.deepCopy();

        DenseVector grad = function.computeGradient(currentPoint),
                hg1 = correctedMatrix.refreshableMultiply(epsilon, 10.0, correctionGradient),
                hgx = correctedMatrix.refreshableMultiply(epsilon, 10.0, grad),
                y = grad.deepCopy().substract(correctionGradient),
                hy = hgx.deepCopy().substract(hg1);

        double yhy = y.getInnerProduct(hy),
                coeff = (-1.0)*((hy.getInnerProduct(correctionGradient)/(yhy + 0.0000001)) + 0.0000001);

        //System.out.println("yhy= "+yhy+",coeff= "+coeff);

        DenseVector v = correctionGradient.deepCopy().add(coeff, y),
                hv = hg1.deepCopy().add(coeff, hy);

        double vhv = v.getInnerProduct(hv),
                rCoeff = 1.0 + 2.0*(alphaTensionCoeff - 1.0)/(1.0 + vhv/(yhy + 0.0000001)),
                sqrtRCoeff = Math.sqrt(Math.max((1.0 - 1.0/rCoeff)/(yhy + 0.0000001) + 0.0000001, 0.0000001));

        //System.out.print("vhv= "+vhv+",rCoeff= "+rCoeff+",sqrtRCoeff= "+sqrtRCoeff);

        hy = hy.multiplyByCoefficient(sqrtRCoeff);

        DenseVector hyColumn = hy.deepCopy().convertToColumnVector(),
                hyRow = hy.deepCopy().convertToRowVector();

        correctedMatrix.substract(hyColumn.multiplyByVector(hyRow));

        if (vhv > gamma*yhy) {
            double sqrtCoeff = Math.sqrt(Math.max((1.0/betaTensionCoeff - 1.0)/(delta*yhy + vhv), 0.0));

            //System.out.println("sqrtCoeff2= "+sqrtCoeff);

            hv = hv.multiplyByCoefficient(sqrtCoeff);

            DenseVector hvColumn = hv.deepCopy().convertToColumnVector(),
                    hvRow = hv.deepCopy().convertToRowVector();

            correctedMatrix.add(hvColumn.multiplyByVector(hvRow));
        }

        return correctedMatrix;
    }

    @Override
    public String getAlgorithmName() {
        return "A alpha and beta sto,alpha={"+getAlphaTensionCoeff()+"},beta={"+getBetaTensionCoeff()+"}";
    }

    @Override
    public ISpaceTensionMatrixCorrectionOperator deepCopy()   {
        return new AlphaBetaSpaceTensionMatrixCorrectionOperator(alphaTensionCoeff,betaTensionCoeff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primeHashCode, alphaTensionCoeff, betaTensionCoeff);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        AlphaBetaSpaceTensionMatrixCorrectionOperator alg = (AlphaBetaSpaceTensionMatrixCorrectionOperator)(obj);

        return hashCode() == alg.hashCode();
    }
}
