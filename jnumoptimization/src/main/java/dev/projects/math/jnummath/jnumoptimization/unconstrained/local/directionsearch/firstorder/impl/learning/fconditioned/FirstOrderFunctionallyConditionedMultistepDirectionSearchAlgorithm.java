package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.fconditioned;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.IDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.impl.learning.FirstOrderOptimalVariables;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class FirstOrderFunctionallyConditionedMultistepDirectionSearchAlgorithm
        extends AbstractFirstOrderFunctionallyConditionedLearningDirectionSearchAlgorithm
        implements IFirstOrderLearningDirectionSearchAlgorithm {

    private DenseVector pVector;

    public FirstOrderFunctionallyConditionedMultistepDirectionSearchAlgorithm(IFunctionallyConditionedTransformCoefficientFunction transformFunction)   {
        super(127, transformFunction);
    }

    @Override
    public IDirectionSearchAlgorithm<IScalarDifferentiableFunction> deepCopy()   {
        return new FirstOrderFunctionallyConditionedMultistepDirectionSearchAlgorithm(getTransformFunction().deepCopy());
    }

    @Override
    public String getAlgorithmName() {
        return "Multistep DSA with FC={" + getTransformFunction().getName() + "}";
    }

    @Override
    public double correctAlgorithm(int iterationCount,
                                   double initialStep,
                                   FirstOrderOptimalVariables optVariables,
                                   DenseVector currentPoint,
                                   DenseVector correctionGradient,
                                   IScalarDifferentiableFunction function)   {
        if (pVector == null) {
            pVector = correctionGradient.deepCopy();
        }

        DenseVector s = getInternalDirection().deepCopy();

        double coeff = (-1.0) * ((1.0 + s.getInnerProduct(correctionGradient)) / (correctionGradient.getInnerProduct(pVector) + 0.0000001));

        setInternalDirection(s.add(coeff, pVector).multiplyByCoefficient(1.0 / s.getEuclidNorm()));

        double g1p = correctionGradient.getInnerProduct(pVector),
                orthogonalCoeff = (g1p)/(pVector.getInnerProduct(pVector) + 0.0000001),
                transformCoeff = getTransformFunction().computeTransformCoefficient(correctionGradient, pVector)*orthogonalCoeff;

        pVector = correctionGradient.deepCopy().substract(transformCoeff, pVector);

        return initialStep;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        FirstOrderFunctionallyConditionedMultistepDirectionSearchAlgorithm
                dsAlgorithm = (FirstOrderFunctionallyConditionedMultistepDirectionSearchAlgorithm) (obj);

        return hashCode() == dsAlgorithm.hashCode();
    }
}
