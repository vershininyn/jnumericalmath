package dev.projects.math.optimization.unconstrained.local.directionsearch.zeroorder.impl.learning.orthogonal;

import dev.projects.math.linalgebra.DenseMatrix;
import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.directionsearch.IDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.base.AbstractDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.zeroorder.IZeroOrderMatrixTransformationOperator;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;
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
    public void initializeProcess(IScalarFunction function) throws LoggableException {
        super.initializeProcess(function);
        metricMatrix = DenseMatrix.getSquareUnit(function.getVariablesCount());
    }

    @Override
    public void deinitializeProcess() throws LoggableException {

    }

    public void setDirectionRow(int row) throws LoggableException {
        if ((row < 0) || (row >= metricMatrix.getRowCount())) throw new LoggableException("Unacceptable direction row");

        directionRow = row;
    }

    @Override
    public DenseVector computeDirection(IScalarFunction function,
                                        DenseVector currentPoint,
                                        int directionRow) throws LoggableException {
        setDirectionRow(directionRow);

        return super.computeDirection(function, currentPoint);
    }

    @Override
    public DenseVector actualComputeDirection(IScalarFunction function,
                                              DenseVector currentPoint) throws LoggableException {
        return metricMatrix.getRow(getDirectionRow());
    }

    @Override
    public String getAlgorithmName() {
        return "A Learning orthogonal ZODSA";
    }

    @Override
    public IDirectionSearchAlgorithm<IScalarFunction> deepCopy() throws LoggableException {
        return new ZeroOrderOrthogonalDirectionSearchAlgorithm();
    }

    @Override
    public void reset() throws LoggableException {

    }

    @Override
    public Object[] performMetricMatrixTransformation(IZeroOrderMatrixTransformationOperator transformationOperator) throws LoggableException {
        Object[] result = transformationOperator.transformMatrix(metricMatrix);

        metricMatrix = (DenseMatrix) result[0];

        return Arrays.stream(result).skip(1).toArray();
    }
}
