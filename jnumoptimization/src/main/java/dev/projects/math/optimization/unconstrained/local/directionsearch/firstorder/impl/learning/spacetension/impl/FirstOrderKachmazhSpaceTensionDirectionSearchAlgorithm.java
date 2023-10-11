package dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.impl;

import dev.projects.math.linalgebra.DenseMatrix;
import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.directionsearch.IDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.base.AbstractFirstOrderSpaceTensionConjugateDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionMatrixCorrectionOperator;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class FirstOrderKachmazhSpaceTensionDirectionSearchAlgorithm
        extends AbstractFirstOrderSpaceTensionConjugateDirectionSearchAlgorithm
        implements IFirstOrderLearningDirectionSearchAlgorithm {

    public FirstOrderKachmazhSpaceTensionDirectionSearchAlgorithm(ISpaceTensionMatrixCorrectionOperator operator) throws LoggableException {
        super(53, operator);
    }

    @Override
    public String getAlgorithmName() {
        return "A Kachmazh STCDSA {sto="+getMatrixCorrectionOperator().getAlgorithmName()+"}";
    }

    @Override
    protected DenseVector doActualInternalDirectionCorrection(DenseVector correctionGradient,
                                                              DenseMatrix currentMatrix,
                                                              DenseVector currentPoint,
                                                              IScalarDifferentiableFunction function) throws LoggableException {
        DenseVector s = getInternalDirection().deepCopy(),
                hg1 = currentMatrix.multiplyByVector(correctionGradient);

        double coeff = (-1.0)*((1.0 + s.getInnerProduct(correctionGradient))/(correctionGradient.getInnerProduct(hg1) + 0.0000001));

        return s.add(coeff, hg1).multiplyByCoefficient(1.0/s.getEuclidNorm());
    }

    @Override
    public IDirectionSearchAlgorithm<IScalarDifferentiableFunction> deepCopy() throws LoggableException {
        return new FirstOrderKachmazhSpaceTensionDirectionSearchAlgorithm(getMatrixCorrectionOperator().deepCopy());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        FirstOrderKachmazhSpaceTensionDirectionSearchAlgorithm alg = (FirstOrderKachmazhSpaceTensionDirectionSearchAlgorithm)(obj);

        return hashCode() == alg.hashCode();
    }
}
