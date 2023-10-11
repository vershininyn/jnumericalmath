package dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.directionsearch.IDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.base.AbstractFirstOrderSpaceTensionDirectionSearchAlgorithmWithIDCOperator;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionInternalDirectionCorrectionOperator;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionMatrixCorrectionOperator;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class FirstOrderNHGSpaceTensionDirectionSearchAlgorithm
        extends AbstractFirstOrderSpaceTensionDirectionSearchAlgorithmWithIDCOperator
        implements IFirstOrderLearningDirectionSearchAlgorithm {

    public FirstOrderNHGSpaceTensionDirectionSearchAlgorithm(ISpaceTensionMatrixCorrectionOperator stoOperator,
                                                             ISpaceTensionInternalDirectionCorrectionOperator idcOperator) throws LoggableException {
        super(61, stoOperator, idcOperator);
    }

    @Override
    public String getAlgorithmName() {
        return "NGH STDSA {sto="+getMatrixCorrectionOperator().getAlgorithmName()
                +",idc= "+getInternalDirectionCorrectionOperator().getAlgorithmName()+"}";
    }

    @Override
    public DenseVector actualComputeDirection(IScalarDifferentiableFunction function,
                                              DenseVector currentPoint) throws LoggableException {
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
                r = getHMatrix().deepCopy().refreshableMultiply(getRefreshableEpsilon(), 10.0, sk);

        double skCoeff = Math.sqrt(Math.max(r.getInnerProduct(sk), 0.0))/sk.getInnerProduct(sk);

        setInternalDirection(s.deepCopy());

        return sk.multiplyByCoefficient(skCoeff);
    }

    @Override
    public IDirectionSearchAlgorithm<IScalarDifferentiableFunction> deepCopy() throws LoggableException {
        return new FirstOrderNHGSpaceTensionDirectionSearchAlgorithm(getMatrixCorrectionOperator().deepCopy(),
                                                                    getInternalDirectionCorrectionOperator().deepCopy());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        FirstOrderNHGSpaceTensionDirectionSearchAlgorithm alg = (FirstOrderNHGSpaceTensionDirectionSearchAlgorithm)(obj);

        return hashCode() == alg.hashCode();
    }
}
