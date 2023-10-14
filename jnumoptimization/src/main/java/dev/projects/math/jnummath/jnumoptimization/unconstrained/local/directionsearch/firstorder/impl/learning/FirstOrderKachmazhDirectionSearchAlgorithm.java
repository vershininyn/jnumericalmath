package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.IDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.FirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.impl.learning.FirstOrderOptimalVariables;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class FirstOrderKachmazhDirectionSearchAlgorithm
        extends FirstOrderLearningDirectionSearchAlgorithm
        implements IFirstOrderLearningDirectionSearchAlgorithm {

    @Getter
    @Setter
    private double qmin;

    public FirstOrderKachmazhDirectionSearchAlgorithm(double qMin) {
        super(37);
        qmin = qMin;
    }

    @Override
    public double correctAlgorithm(int iterationCount,
                                   double initialStep,
                                   FirstOrderOptimalVariables optVariables,
                                   DenseVector currentPoint,
                                   DenseVector correctionGradient,
                                   IScalarDifferentiableFunction function)   {
        DenseVector s = getInternalDirection().deepCopy();

        double coeff = (-1.0)*((1.0 + s.getInnerProduct(correctionGradient))
                /correctionGradient.getInnerProduct(correctionGradient));

        setInternalDirection(s.add(coeff, correctionGradient).multiplyByCoefficient(1.0/s.getEuclidNorm()));

        return initialStep;
    }

    @Override
    public void reset()   {
        qmin = 0.8;
    }

    @Override
    public String getAlgorithmName() {
        return "A Kachmazh CDSA";
    }

    @Override
    public IDirectionSearchAlgorithm<IScalarDifferentiableFunction> deepCopy()   {
        return new FirstOrderKachmazhDirectionSearchAlgorithm(qmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qmin, getPrimeHashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        FirstOrderKachmazhDirectionSearchAlgorithm alg = (FirstOrderKachmazhDirectionSearchAlgorithm)(obj);

        return hashCode() == alg.hashCode();
    }
}
