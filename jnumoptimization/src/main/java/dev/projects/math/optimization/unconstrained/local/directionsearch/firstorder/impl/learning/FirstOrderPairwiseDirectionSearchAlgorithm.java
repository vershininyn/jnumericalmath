package dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.directionsearch.IDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.FirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.firstorder.impl.learning.FirstOrderOptimalVariables;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class FirstOrderPairwiseDirectionSearchAlgorithm
        extends FirstOrderLearningDirectionSearchAlgorithm
        implements IFirstOrderLearningDirectionSearchAlgorithm {

    private DenseVector pVector, earlyCorrectionGradient;

    public FirstOrderPairwiseDirectionSearchAlgorithm() {
        super(43);
    }

    @Override
    public void initializeProcess(IScalarDifferentiableFunction function) throws LoggableException {
        super.initializeProcess(function);
        earlyCorrectionGradient = DenseVector.getInstance(function.getVariablesCount(), 0.0);
    }

    @Override
    public double correctAlgorithm(int iterationCount,
                                   double initialStep,
                                   FirstOrderOptimalVariables optVariables,
                                   DenseVector currentPoint,
                                   DenseVector correctionGradient,
                                   IScalarDifferentiableFunction function) throws LoggableException {

        if (pVector == null) {
            pVector = correctionGradient.deepCopy();
        }

        DenseVector s = getInternalDirection().deepCopy();

        double coeff = (-1.0)*((1.0 + s.getInnerProduct(correctionGradient))
                /(correctionGradient.getInnerProduct(pVector) + 0.0000001));

        setInternalDirection(s.add(coeff, pVector).multiplyByCoefficient(1.0/s.getEuclidNorm()));

        double g1p = correctionGradient.getInnerProduct(earlyCorrectionGradient);

        if (g1p >= 0.0) {
            pVector = correctionGradient.deepCopy();
        } else {
            double orthogonalcoeff = (-1.0)*((correctionGradient.getInnerProduct(earlyCorrectionGradient))
                    /(earlyCorrectionGradient.getInnerProduct(earlyCorrectionGradient)));

            pVector = correctionGradient.deepCopy().add(orthogonalcoeff, earlyCorrectionGradient);
        }

        earlyCorrectionGradient = correctionGradient.deepCopy();

        return initialStep;
    }

    @Override
    public String getAlgorithmName() {
        return "A pairwise CDSA";
    }

    @Override
    public IDirectionSearchAlgorithm<IScalarDifferentiableFunction> deepCopy() throws LoggableException {
        return new FirstOrderPairwiseDirectionSearchAlgorithm();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        FirstOrderPairwiseDirectionSearchAlgorithm alg = (FirstOrderPairwiseDirectionSearchAlgorithm)(obj);

        return hashCode() == alg.hashCode();
    }
}
