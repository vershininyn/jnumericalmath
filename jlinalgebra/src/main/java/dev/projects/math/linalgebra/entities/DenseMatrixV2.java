package dev.projects.math.linalgebra.entities;

import dev.projects.math.linalgebra.contexts.MatrixContext;
import dev.projects.math.linalgebra.OrthogonalTransformationResultDto;
import dev.projects.math.linalgebra.access.DataAccessStrategyType;
import dev.projects.math.linalgebra.access.IDataAccessStrategy;
import dev.projects.math.linalgebra.blocks.IDataBlock;
import dev.projects.math.linalgebra.blocks.memorydoublebuffer.MemoryDoubleDataBlock;
import dev.projects.math.linalgebra.storagestrategy.RegionDataBlockKey;
import dev.projects.utils.exception.LoggableException;

import java.util.Arrays;
import java.util.Map;

public class DenseMatrixV2 implements IMatrix {
    private final static int MAX_SWITCH_THRESHOLD = 3000;

    private MatrixContext context = null;

    public static IMatrix getInstance(int rowCount,
                                      int columnCount,
                                      int switchThreshold) throws LoggableException {
        if (rowCount <= 0) throw new LoggableException("Unacceptable row count");
        if (columnCount <= 0) throw new LoggableException("Unacceptable column count");
        if (switchThreshold <= 0) throw new LoggableException("Unacceptable the switch threshold");

        return new DenseMatrixV2(rowCount, columnCount, switchThreshold);
    }

    public static IMatrix getInstance(int rowCount,
                                      int columnCount) throws LoggableException {
        return new DenseMatrixV2(rowCount, columnCount, MAX_SWITCH_THRESHOLD);
    }

    public static IMatrix getInstance(double[] data,
                                      int rowCount,
                                      int columnCount,
                                      int switchThreshold) throws LoggableException {
        if (rowCount <= 0) throw new LoggableException("Unacceptable row count");
        if (columnCount <= 0) throw new LoggableException("Unacceptable column count");
        if (switchThreshold <= 0) throw new LoggableException("Unacceptable the switch threshold");
        if (data == null) throw new LoggableException("Unacceptable collection of double[]");
        if ((data.length <= 0)
                || (data.length != (rowCount * columnCount))) throw new LoggableException("Unacceptable dimension");

        IMatrix matrix = getInstance(rowCount, columnCount, switchThreshold);

        matrix.setData(Map.of(new RegionDataBlockKey(0, 0, rowCount, 0, columnCount),
                new MemoryDoubleDataBlock(data)));

        return matrix;
    }

    public static IMatrix getInstance(double[] data,
                                      int rowCount,
                                      int columnCount) throws LoggableException {
        return getInstance(data, rowCount, columnCount, MAX_SWITCH_THRESHOLD);
    }

    public static IMatrix getInstance(IMatrix matrix) throws LoggableException {
        if ((matrix == null)
                || (matrix.getRowCount() <= 0)
                || (matrix.getColumnCount() <= 0)) throw new LoggableException("Unacceptable matrix object");

        return new DenseMatrixV2(matrix);
    }

    public static IMatrix getDiagonal(double[] diagonal) throws LoggableException {
        if ((diagonal == null)
                || (diagonal.length <= 0)) throw new LoggableException("Unacceptable the diagonal data array");

        //TODO
        return null;
    }

    public static IMatrix getDiagonal(int rowCount, int columnCount, double diagonalValue) throws LoggableException {
        if ((rowCount <= 0) || (columnCount <= 0)) {
            throw new LoggableException("Unacceptable row/column count");
        }

        double[] diag = new double[Math.min(rowCount, columnCount)];

        Arrays.fill(diag, diagonalValue);

        //TODO
        return null;
    }

    public static IMatrix getUnit(int rowCount, int columnCount) throws LoggableException {
        return getDiagonal(rowCount, columnCount, 1.0);
    }

    public static IMatrix getSquareUnit(int size) throws LoggableException {
        return getUnit(size, size);
    }

    private DenseMatrixV2(int rowCount,
                          int columnCount,
                          int switchThreshold) throws LoggableException {
        context = new MatrixContext(rowCount, columnCount, switchThreshold, DataAccessStrategyType.useMatrixDataAccess);
    }

    private DenseMatrixV2(int rowCount,
                          int columnCount,
                          int switchThreshold,
                          double defaultValue) throws LoggableException {
        this(rowCount, columnCount, switchThreshold);

        context.fillByValue(defaultValue);
    }

    private DenseMatrixV2(IMatrix matrix) throws LoggableException {
        if (matrix == null) throw new LoggableException("Unacceptable matrix");

        context = new MatrixContext(matrix.getRowCount(),
                matrix.getColumnCount(),
                matrix.getSwitchThreshold(),
                DataAccessStrategyType.useMatrixDataAccess);

        context.setData(matrix.getData());
    }

    @Override
    public IMatrix deepCopy() throws LoggableException {
        return new DenseMatrixV2(this);
    }

    @Override
    public int getSize() {
        return getRowCount() * getColumnCount();
    }

    @Override
    public int getRowCount() {
        return context.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return context.getColumnCount();
    }

    @Override
    public int getSwitchThreshold() {
        return context.getSwitchThreshold();
    }

    @Override
    public double getEuclidNorm() throws LoggableException {
        return 0;
    }

    @Override
    public double getMaxElement() throws LoggableException {
        return 0;
    }

    @Override
    public IMatrix multiplyByCoefficient(double coeff) throws LoggableException {
        return null;
    }

    @Override
    public IMatrix setData(Map<RegionDataBlockKey, IDataBlock> data) throws LoggableException {
        return null;
    }

    @Override
    public Map<RegionDataBlockKey, IDataBlock> getData() {
        return null;
    }

    @Override
    public OrthogonalTransformationResultDto doOrthogonalTransformationAtPlace(IVector y, IVector sum) throws LoggableException {
        return null;
    }

    @Override
    public IVector getColumn(int column) throws LoggableException {
        return null;
    }

    @Override
    public IVector getRow(int row) throws LoggableException {
        return null;
    }

    @Override
    public double getMaxDiagonal() {
        return 0;
    }

    @Override
    public IMatrix setDiagonal(IVector vector) throws LoggableException {
        return null;
    }

    @Override
    public IMatrix setDiagonal(double[] data) {
        return null;
    }

    @Override
    public IMatrix addDiagonal(double value) {
        return null;
    }

    @Override
    public IMatrix multiplyRowByCoefficient(int row, double coeff) throws LoggableException {
        return null;
    }

    @Override
    public IMatrix multiplyColumnByCoefficient(int column, double coeff) throws LoggableException {
        return null;
    }

    @Override
    public IVector refreshableMultiply(double epsilon, double multiplicator, IVector vector) throws LoggableException {
        return null;
    }

    @Override
    public IVector multiplyByVector(IVector vector) throws LoggableException {
        return null;
    }

    @Override
    public IMatrix multiplyByMatrix(IMatrix matrix) throws LoggableException {
        return null;
    }

    @Override
    public IMatrix add(IMatrix matrix) throws LoggableException {
        return add(1.0, matrix);
    }

    @Override
    public IMatrix add(double coeff, IMatrix matrix) throws LoggableException {
        return null;
    }

    @Override
    public IMatrix substract(IMatrix matrix) throws LoggableException {
        return substract(1.0, matrix);
    }

    @Override
    public IMatrix substract(double coeff, IMatrix matrix) throws LoggableException {
        return null;
    }

    @Override
    public IMatrix fillByRegion(IDataBlock block, RegionDataBlockKey region) throws LoggableException {
        if (block == null) throw new LoggableException("Unacceptable block");
        if ((region == null)) throw new LoggableException("Unacceptable region");

        IDataAccessStrategy accessStrategy = context.getStorageStrategy().getAccessStrategy();

        for (int rowIndex = region.getStartRowIndex(); rowIndex < region.getEndRowIndex(); rowIndex++) {
            for (int columnIndex = region.getStartColumnIndex(); columnIndex < region.getEndColumnIndex(); columnIndex++) {
                double value = block.readValue(accessStrategy.getIndex(rowIndex, columnIndex));

                context.writeValue(rowIndex, columnIndex, value);
            }
        }

        return this;
    }

    @Override
    public void close() throws Exception {
        if (context != null) {
            context.close();
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
