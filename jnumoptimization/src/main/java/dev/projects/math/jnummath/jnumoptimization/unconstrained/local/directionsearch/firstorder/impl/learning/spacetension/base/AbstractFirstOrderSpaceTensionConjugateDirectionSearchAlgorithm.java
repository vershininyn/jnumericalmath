package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.base;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseMatrix;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionMatrixCorrectionOperator;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public abstract class AbstractFirstOrderSpaceTensionConjugateDirectionSearchAlgorithm
              extends  AbstractFirstOrderSpaceTensionDirectionSearchAlgorithm {
    public AbstractFirstOrderSpaceTensionConjugateDirectionSearchAlgorithm(int primeHash,
                                                                           ISpaceTensionMatrixCorrectionOperator stoOperator)   {
        super(primeHash, stoOperator);
    }

    @Override
    public DenseVector actualComputeDirection(IScalarDifferentiableFunction function,
                                              DenseVector currentPoint)   {
        DenseMatrix matrix = getHMatrix().deepCopy();

        DenseVector grad = function.computeGradient(currentPoint).deepCopy(),
                hg = matrix.refreshableMultiply(getRefreshableEpsilon(), 10.0, grad),
                s = getInternalDirection().deepCopy();

        double sg = (-1.0)*s.getInnerProduct(grad);

        if (sg < 1.0) {
            double coeff = ((sg - 1.0)/(grad.getInnerProduct(hg) + 0.0000001));
            s = s.add(coeff, hg);
        } else s = s.multiplyByCoefficient(1.0/(sg + 0.0000001));

        DenseVector hs = matrix.multiplyByVector(s),
                sk = s.multiplyByCoefficient(Math.sqrt(Math.max((s.getInnerProduct(hs))/(s.getInnerProduct(s)), 0.0)));

        setInternalDirection(s.deepCopy());

        return sk;
    }
}
