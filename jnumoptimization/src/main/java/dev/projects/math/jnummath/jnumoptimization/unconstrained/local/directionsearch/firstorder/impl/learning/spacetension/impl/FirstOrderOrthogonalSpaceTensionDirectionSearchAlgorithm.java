package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseMatrix;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionMatrixCorrectionOperator;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.IDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.base.AbstractFirstOrderSpaceTensionDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class FirstOrderOrthogonalSpaceTensionDirectionSearchAlgorithm
                extends AbstractFirstOrderSpaceTensionDirectionSearchAlgorithm
                implements IFirstOrderLearningDirectionSearchAlgorithm {

    public FirstOrderOrthogonalSpaceTensionDirectionSearchAlgorithm(ISpaceTensionMatrixCorrectionOperator stoOperator)   {
        super(109, stoOperator);
    }

    @Override
    public DenseVector actualComputeDirection(IScalarDifferentiableFunction function,
                                              DenseVector currentPoint)   {
        DenseVector grad = function.computeGradient(currentPoint),
                hgx = getHMatrix().deepCopy().refreshableMultiply(getRefreshableEpsilon(), 10.0, grad),
                s = getInternalDirection().deepCopy();

        double ghg = hgx.getInnerProduct(grad),
                sgx = (-1.0)*s.getInnerProduct(grad);

        if (sgx < 1.0) {
            double coeff = (sgx - 1.0)/(ghg + 0.0000001);
            s = s.add(coeff, hgx);
        } else s = s.multiplyByCoefficient(1.0/(sgx + 0.0000001));

        DenseVector sk = s.deepCopy(),
                r = getHMatrix().refreshableMultiply(getRefreshableEpsilon(), 10.0, sk);

        double skCoeff = Math.sqrt(Math.max(r.getInnerProduct(sk), 0.0))/sk.getInnerProduct(sk);

        return sk.multiplyByCoefficient(skCoeff);
    }

    @Override
    protected DenseVector doActualInternalDirectionCorrection(DenseVector correctionGradient,
                                                              DenseMatrix currentMatrix,
                                                              DenseVector currentPoint,
                                                              IScalarDifferentiableFunction function)   {

        DenseVector grad = function.computeGradient(currentPoint),
                hgx = currentMatrix.multiplyByVector(grad),
                hg1 = currentMatrix.multiplyByVector(correctionGradient),
                s = getInternalDirection().deepCopy(),
                y = grad.deepCopy().substract(correctionGradient),
                hy = hgx.deepCopy().substract(hg1);

        double ghg = correctionGradient.getInnerProduct(hg1),
                sg1 = (-1.0)*((1.0 + s.getInnerProduct(correctionGradient))/(ghg)),
                yhy = y.getInnerProduct(hy),
                cCoeff = (-1.0)*((grad.getInnerProduct(hg1))/(y.getInnerProduct(hg1) + 0.000001));

        DenseVector v = grad.deepCopy().add(cCoeff, y),
                hv = hgx.deepCopy().add(cCoeff, hy);

        s = s.add(sg1, hg1);

        double vhv = Math.max(v.getInnerProduct(hv), 0.0),
                tCoeff = (-1.0)*((1.0 + s.getInnerProduct(v))/(vhv + yhy*1.0E-06));

        s = s.add(tCoeff, hv);

        return s.deepCopy();
    }

    @Override
    public IDirectionSearchAlgorithm<IScalarDifferentiableFunction> deepCopy()   {
        return new FirstOrderOrthogonalSpaceTensionDirectionSearchAlgorithm(getMatrixCorrectionOperator().deepCopy());
    }

    @Override
    public String getAlgorithmName() {
        return "A orthogonal STDSA sto= {"+getMatrixCorrectionOperator().getAlgorithmName()+"}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        FirstOrderOrthogonalSpaceTensionDirectionSearchAlgorithm alg = (FirstOrderOrthogonalSpaceTensionDirectionSearchAlgorithm)(obj);

        return hashCode() == alg.hashCode();
    }
}
