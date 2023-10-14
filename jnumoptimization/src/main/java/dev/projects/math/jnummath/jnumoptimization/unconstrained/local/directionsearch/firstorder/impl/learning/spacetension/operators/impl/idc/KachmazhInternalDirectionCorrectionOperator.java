package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.impl.idc;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseMatrix;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionInternalDirectionCorrectionOperator;
import dev.projects.utils.exception.LoggableException;

import java.util.Objects;

public class KachmazhInternalDirectionCorrectionOperator
                implements ISpaceTensionInternalDirectionCorrectionOperator {

    private int primeHashCode = 113;

    @Override
    public DenseVector correctInternalDirection(DenseVector currentInternalDirection,
                                                DenseVector correctionGradient,
                                                DenseMatrix currentMatrix)   {
        DenseVector s = currentInternalDirection,
                hg1 = currentMatrix.multiplyByVector(correctionGradient);

        double coeff = (-1.0)*((1.0 + s.getInnerProduct(correctionGradient))/(correctionGradient.getInnerProduct(hg1)));

        return s.deepCopy().add(coeff, hg1);
    }

    @Override
    public String getAlgorithmName() {
        return "A Kachmazh IDCO";
    }

    @Override
    public ISpaceTensionInternalDirectionCorrectionOperator deepCopy()   {
        return new KachmazhInternalDirectionCorrectionOperator();
    }

    @Override
    public int hashCode() {
        return Objects.hash(primeHashCode);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        KachmazhInternalDirectionCorrectionOperator alg = (KachmazhInternalDirectionCorrectionOperator)(obj);

        return hashCode() == alg.hashCode();
    }
}
