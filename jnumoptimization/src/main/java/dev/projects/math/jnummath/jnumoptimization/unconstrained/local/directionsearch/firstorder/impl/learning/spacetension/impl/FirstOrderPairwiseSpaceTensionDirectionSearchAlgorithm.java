package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseMatrix;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.IDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.base.AbstractFirstOrderSpaceTensionConjugateDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionMatrixCorrectionOperator;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class FirstOrderPairwiseSpaceTensionDirectionSearchAlgorithm
        extends AbstractFirstOrderSpaceTensionConjugateDirectionSearchAlgorithm
        implements IFirstOrderLearningDirectionSearchAlgorithm {

    private DenseVector pVector, earlyCorrectionGradient;

    public FirstOrderPairwiseSpaceTensionDirectionSearchAlgorithm(ISpaceTensionMatrixCorrectionOperator operator)   {
        super(71,operator);
    }

    @Override
    public String getAlgorithmName() {
        return "A pairwise STCDSA {sto="+getMatrixCorrectionOperator().getAlgorithmName()+"}";
    }

    @Override
    public void initializeProcess(IScalarDifferentiableFunction function)   {
        super.initializeProcess(function);
        earlyCorrectionGradient = DenseVector.getInstance(function.getInputDimensionSize(), 0.0);
    }

    @Override
    protected DenseVector doActualInternalDirectionCorrection(DenseVector correctionGradient,
                                                              DenseMatrix currentMatrix,
                                                              DenseVector currentPoint,
                                                              IScalarDifferentiableFunction function)   {
        if (pVector == null) {
            pVector = correctionGradient.deepCopy();
        }

        DenseVector s = getInternalDirection().deepCopy(),
                hp = currentMatrix.multiplyByVector(pVector),
                hg = currentMatrix.multiplyByVector(earlyCorrectionGradient);

        double coeff = (-1.0)*((1.0 + s.getInnerProduct(correctionGradient))
                /(correctionGradient.getInnerProduct(hp) + 0.0000001));

        double g1p = correctionGradient.getInnerProduct(hg);

        if (g1p >= 0.0) {
            pVector = currentMatrix.multiplyByVector(correctionGradient);
        } else {
            double orthogonalcoeff = (-1.0)*((correctionGradient.getInnerProduct(hg))
                    /(hg.getInnerProduct(hg)));

            pVector = correctionGradient.deepCopy().add(orthogonalcoeff, hg);
        }

        return s.add(coeff, hp)
                .multiplyByCoefficient(1.0/s.getEuclidNorm());
    }


    @Override
    public IDirectionSearchAlgorithm<IScalarDifferentiableFunction> deepCopy()   {
        return new FirstOrderPairwiseSpaceTensionDirectionSearchAlgorithm(getMatrixCorrectionOperator().deepCopy());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        FirstOrderPairwiseSpaceTensionDirectionSearchAlgorithm alg = (FirstOrderPairwiseSpaceTensionDirectionSearchAlgorithm)(obj);

        return hashCode() == alg.hashCode();
    }
}
