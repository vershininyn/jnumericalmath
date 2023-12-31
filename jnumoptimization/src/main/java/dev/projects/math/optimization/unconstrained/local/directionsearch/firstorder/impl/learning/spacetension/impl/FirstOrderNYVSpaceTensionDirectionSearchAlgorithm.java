package dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.directionsearch.IDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.base.AbstractFirstOrderSpaceTensionDirectionSearchAlgorithmWithIDCOperator;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionInternalDirectionCorrectionOperator;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionMatrixCorrectionOperator;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class FirstOrderNYVSpaceTensionDirectionSearchAlgorithm
        extends AbstractFirstOrderSpaceTensionDirectionSearchAlgorithmWithIDCOperator
        implements IFirstOrderLearningDirectionSearchAlgorithm {

    public FirstOrderNYVSpaceTensionDirectionSearchAlgorithm(ISpaceTensionMatrixCorrectionOperator stoOperator,
                                                             ISpaceTensionInternalDirectionCorrectionOperator idcOperator) throws LoggableException {
        super(67,stoOperator, idcOperator);
    }

    @Override
    public DenseVector actualComputeDirection(IScalarDifferentiableFunction function, DenseVector currentPoint) throws LoggableException {
        DenseVector grad = function.computeGradient(currentPoint),
                hgx = getHMatrix().deepCopy().refreshableMultiply(getRefreshableEpsilon(), 10.0, grad);

        double coeff = (-1.0)*(1.0/Math.sqrt(Math.max(grad.getInnerProduct(hgx), 1.0)));

        return hgx.deepCopy().multiplyByCoefficient(coeff);
    }

    @Override
    public void reset() throws LoggableException {

    }

    @Override
    public String getAlgorithmName() {
        return "NYV STDSA {sto="+getMatrixCorrectionOperator().getAlgorithmName()+",idc= "+
                getInternalDirectionCorrectionOperator().getAlgorithmName()+"}";
    }

    @Override
    public IDirectionSearchAlgorithm<IScalarDifferentiableFunction> deepCopy() throws LoggableException {
        return new FirstOrderNYVSpaceTensionDirectionSearchAlgorithm(getMatrixCorrectionOperator().deepCopy(),
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

        FirstOrderNYVSpaceTensionDirectionSearchAlgorithm alg = (FirstOrderNYVSpaceTensionDirectionSearchAlgorithm)(obj);

        return hashCode() == alg.hashCode();
    }
}
