package dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.base;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.directionsearch.base.AbstractDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.firstorder.impl.learning.FirstOrderOptimalVariables;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class FirstOrderLearningDirectionSearchAlgorithm
                        extends AbstractDirectionSearchAlgorithm<IScalarDifferentiableFunction> {
    private int primeHashCode;

    public FirstOrderLearningDirectionSearchAlgorithm(int primeHash) {
        super(primeHash);
    }

    @Override
    public DenseVector actualComputeDirection(IScalarDifferentiableFunction function, DenseVector currentPoint) throws LoggableException {
        DenseVector grad = function.computeGradient(currentPoint).deepCopy(),
                s = getInternalDirection().deepCopy();

        double sg = (-1.0)*s.getInnerProduct(grad);

        if (sg < 1.0) {
            double coeff = ((sg - 1.0)/(grad.getInnerProduct(grad) + 0.00001));
            s = s.add(coeff, grad);
        } else s = s.multiplyByCoefficient(1.0 / sg);

        DenseVector sk = s.deepCopy().multiplyByCoefficient(1.0/Math.sqrt(s.getInnerProduct(s)));

        setInternalDirection(s.deepCopy());

        return sk;
    }

    public abstract double correctAlgorithm(int iterationCount,
                                            double initialStep,
                                            FirstOrderOptimalVariables optVariables,
                                            DenseVector currentPoint,
                                            DenseVector correctionGradient,
                                            IScalarDifferentiableFunction function) throws LoggableException;
}
