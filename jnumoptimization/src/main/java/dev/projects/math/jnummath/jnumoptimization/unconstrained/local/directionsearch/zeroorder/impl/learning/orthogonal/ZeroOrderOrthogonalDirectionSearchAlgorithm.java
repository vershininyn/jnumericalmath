package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.zeroorder.impl.learning.orthogonal;

import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseMatrix;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.IDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.base.AbstractDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.zeroorder.IZeroOrderMatrixTransformationOperator;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;
import lombok.Getter;

import java.util.Arrays;

public class ZeroOrderOrthogonalDirectionSearchAlgorithm
                extends AbstractDirectionSearchAlgorithm<IScalarFunction>
                implements IZeroOrderOrthogonalDirectionSearchAlgorithm {

    @Getter
    private int directionRow;

    private DenseMatrix metricMatrix;

    public ZeroOrderOrthogonalDirectionSearchAlgorithm() {
        super(101);
    }

    @Override
    public void initializeProcess(IScalarFunction function)   {
        super.initializeProcess(function);
        metricMatrix = DenseMatrix.getSquareUnit(function.getInputDimensionSize());
    }

    @Override
    public void deinitializeProcess()   {

    }

    public void setDirectionRow(int row)   {
        if ((row < 0) || (row >= metricMatrix.getRowCount())) throw new LoggableException("Unacceptable direction row");

        directionRow = row;
    }

    @Override
    public DenseVector computeDirection(IScalarFunction function,
                                        DenseVector currentPoint,
                                        int directionRow)   {
        setDirectionRow(directionRow);

        return super.computeDirection(function, currentPoint);
    }

    @Override
    public DenseVector actualComputeDirection(IScalarFunction function,
                                              DenseVector currentPoint)   {
        return metricMatrix.getRow(getDirectionRow());
    }

    @Override
    public String getAlgorithmName() {
        return "A Learning orthogonal ZODSA";
    }

    @Override
    public IDirectionSearchAlgorithm<IScalarFunction> deepCopy()   {
        return new ZeroOrderOrthogonalDirectionSearchAlgorithm();
    }

    @Override
    public void reset()   {

    }

    @Override
    public Object[] performMetricMatrixTransformation(IZeroOrderMatrixTransformationOperator transformationOperator)   {
        Object[] result = transformationOperator.transformMatrix(metricMatrix);

        metricMatrix = (DenseMatrix) result[0];

        return Arrays.stream(result).skip(1).toArray();
    }
}
