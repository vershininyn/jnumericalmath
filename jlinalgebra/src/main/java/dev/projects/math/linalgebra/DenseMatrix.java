package dev.projects.math.linalgebra;

import dev.projects.utils.exception.LoggableException;
import org.ejml.concurrency.EjmlConcurrency;
import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.NormOps_DDRM;
import org.ejml.dense.row.mult.MatrixMatrixMult_DDRM;
import org.ejml.dense.row.mult.MatrixVectorMult_DDRM;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author vyn
 */
public class DenseMatrix extends DMatrixRMaj implements DMatrix, ICloneable<DenseMatrix>, ICountable {
    public static DenseMatrix getInstance(int rowCount, int columnCount) throws LoggableException {
        if ((rowCount <= 0) || (columnCount <= 0)) {
            throw new LoggableException("Unacceptable row/column count");
        }

        return new DenseMatrix(rowCount, columnCount);
    }

    public static DenseMatrix getInstance(double[] pData, int rowCount, int columnCount) throws LoggableException {
        if (pData == null) {
            throw new LoggableException("Unacceptable collection of double[][]");
        }
        if ((pData.length <= 0) || (pData.length != rowCount * columnCount)) {
            throw new LoggableException("Unacceptable dimension");
        }

        DenseMatrix matrix = DenseMatrix.getInstance(rowCount, columnCount);
        matrix.setData(pData);

        return matrix;
    }

    public static DenseMatrix getInstance(final DenseMatrix matrix) throws LoggableException {
        if ((matrix == null)
                || (matrix.getRowCount() <= 0)
                || (matrix.getColumnCount() <= 0)) {
            throw new LoggableException("Unacceptable matrix object");
        }

        return new DenseMatrix(matrix);
    }

    public static DenseMatrix getDiagonal(double[] diagonal) throws LoggableException {
        if ((diagonal == null) || (diagonal.length <= 0)) {
            throw new LoggableException("Unacceptable row/column count");
        }

        return new DenseMatrix(diagonal);
    }

    public static DenseMatrix getDiagonal(int rowCount, int columnCount, double diagonalValue) throws LoggableException {
        if ((rowCount <= 0) || (columnCount <= 0)) {
            throw new LoggableException("Unacceptable row/column count");
        }

        double[] diag = new double[Math.min(rowCount, columnCount)];

        Arrays.fill(diag, diagonalValue);

        return new DenseMatrix(rowCount, columnCount, diag);
    }

    public static DenseMatrix getUnit(int rowCount, int columnCount) throws LoggableException {
        return getDiagonal(rowCount, columnCount, 1.0);
    }

    public static DenseMatrix getSquareUnit(int pWidth) throws LoggableException {
        return getUnit(pWidth, pWidth);
    }

    private DenseMatrix(int rowCount, int columnCount) {
        super(rowCount, columnCount);
        EjmlConcurrency.USE_CONCURRENT = true;
    }

    private DenseMatrix() {
    }

    private DenseMatrix(double[][] array) {
        super(array);
        EjmlConcurrency.USE_CONCURRENT = true;
    }

    private DenseMatrix(DenseMatrix matrix) {
        super(matrix);
        EjmlConcurrency.USE_CONCURRENT = true;
    }

    private DenseMatrix(double[] mainDiagonal) {
        this.setDiagonal(mainDiagonal);
        EjmlConcurrency.USE_CONCURRENT = true;
    }

    private DenseMatrix(int rowCount, int columnCount, double[] mainDiagonal) {
        super(CommonOps_DDRM.diagR(rowCount, columnCount, mainDiagonal));
        EjmlConcurrency.USE_CONCURRENT = true;
    }

    private DenseMatrix(int rowCount, int columnCount, boolean rowMajor, double[] data) {
        super(rowCount, columnCount, rowMajor, data);
        EjmlConcurrency.USE_CONCURRENT = true;
    }

    @Override
    public DenseMatrix deepCopy() {
        return new DenseMatrix(this);
    }

    @Override
    public int getSize() {
        return getNumElements();
    }

    public OrthogonalTransformationResultDto doOrthogonalTransformationAtPlace(DenseVector y, DenseVector sum) throws LoggableException {
        if (y == null) throw new LoggableException("Unacceptable y vector");
        if (sum == null) throw new LoggableException("Unacceptable sum vector");
        if (y.getSize() != sum.getSize()) throw new LoggableException("y and sum vector must have same size");

        DenseVector yCopy = y.deepCopy(), sumCopy = sum.deepCopy();

        int varsCount = yCopy.getSize();

        yCopy.multiplyByCoefficient(1.0 / yCopy.getEuclidNorm())
                .shiftByCoefficient(0.01 / varsCount)
                .multiplyByCoefficient(1.0 / yCopy.getEuclidNorm());

        DenseVector iy = DenseVector.getInstance(varsCount, 0.0);

        boolean isEnd = false;

        for (int i = 0; i < varsCount; i++) {
            if (isEnd) break;

            iy.setValue(i, i);
            double ay = yCopy.getValue(i);

            for (int j = 0; j < i - 1; j++) {
                int jj = i - j;
                double y0 = yCopy.getValue(jj);

                if (Math.abs(ay) >= Math.abs(y0)) {
                    isEnd = true;
                    break;
                }

                yCopy.setValue(jj, ay);
                yCopy.setValue(jj + 1, y0);

                iy.setValue(jj + 1, iy.getValue(jj));
                iy.setValue(jj, i);
            }
        }

        DenseVector s = DenseVector.getInstance(varsCount, 0.0);

        double zz = 0.0, sn = 0.0;

        for (int k = 0; k < varsCount - 1; k++) {
            int i = (int) (iy.getValue(k)),
                    ii = (int) (iy.getValue(k + 1));

            zz = zz + Math.pow(yCopy.getValue(k), 2.0);

            double v = (-1.0) * (zz / yCopy.getValue(k + 1)),
                    w = 1.0 / Math.sqrt(zz + v * v);

            sn = sn + sumCopy.getValue(i) * yCopy.getValue(k);

            sumCopy.setValue(i, (sn + sumCopy.getValue(ii) * v) * w);

            for (int j = 0; j < varsCount; j++) {
                s.setValue(j, s.getValue(j) + yCopy.getValue(k) * get(i, j));
                set(i, j, w * (s.getValue(j) + v * get(ii, j)));
            }
        }

        int i = (int) (iy.getValue(varsCount - 1));
        double v = yCopy.getValue(varsCount - 1);

        zz = 1.0 / Math.sqrt(zz + v * v);
        sumCopy.setValue(i, zz * (sn + sumCopy.getValue(i) * v));

        for (int j = 0; j < varsCount; j++) {
            set(i, j, zz * (s.getValue(j) + v * get(i, j)));
        }

        return new OrthogonalTransformationResultDto(this, yCopy, sumCopy);
    }

    public DenseVector getColumn(int columnIndex) throws LoggableException {
        if ((columnIndex < 0) || (columnIndex > getColumnCount()))
            throw new LoggableException("Unacceptable column index");

        DenseVector column = DenseVector.getInstance(getRowCount(), 0.0);

        for (int index = 0; index < getRowCount(); index++) {
            column.setValue(index, unsafe_get(index, columnIndex));
        }

        return column;
    }

    public DenseVector getRow(int rowIndex) throws LoggableException {
        if ((rowIndex < 0) || (rowIndex > getRowCount())) throw new LoggableException("Unacceptable row index");

        DenseVector row = DenseVector.getInstance(getColumnCount(), 0.0);

        for (int index = 0; index < getRowCount(); index++) {
            row.setValue(index, unsafe_get(rowIndex, index));
        }

        return row;
    }

    public int getRowCount() {
        return getNumRows();
    }

    public int getColumnCount() {
        return getNumCols();
    }

    public double getMaxDiagonal() {
        int minIndex = getMinIndex();

        double max = 0.0;

        for (int index = 0; index < minIndex; index++) {
            max = Math.max(max, unsafe_get(index, index));
        }

        return max;
    }

    public DenseMatrix setDiagonal(DenseVector vector) throws LoggableException {
        if ((vector == null) || (vector.getData().length != Math.max(this.getColumnCount(), this.getRowCount()))) {
            throw new LoggableException("Unacceptable vector");
        }

        setDiagonal(vector.getData());

        return this;
    }

    public DenseMatrix setDiagonal(double[] data) {
        int minIndex = getMinIndex();

        for (int index = 0; index < minIndex; index++) {
            unsafe_set(index, index, data[index]);
        }

        return this;
    }

    public DenseMatrix addDiagonal(double pValue) {
        int minIndex = getMinIndex();

        for (int index = 0; index < minIndex; index++) {
            unsafe_set(index, index, unsafe_get(index, index) + pValue);
        }

        return this;
    }

    public DenseMatrix multiplyRowByCoefficient(int row, double coefficient) throws LoggableException {
        if ((row < 0) || (row >= getRowCount())) throw new LoggableException("Unacceptable row index");

        CommonOps_DDRM.scaleRow(coefficient, this, row);

        return this;
    }

    public DenseMatrix multiplyColumnByCoefficient(int column, double coefficient) throws LoggableException {
        if ((column < 0) || (column >= getColumnCount())) throw new LoggableException("Unacceptable column index");

        CommonOps_DDRM.scaleCol(coefficient, this, column);

        return this;
    }

    public DenseMatrix multiplyByCoefficient(double coefficient) {
        CommonOps_DDRM.scale(coefficient, this);
        return this;
    }

    public DenseVector refreshableMultiply(double epsilon, double multiplicator, DenseVector vector) throws LoggableException {
        if (epsilon <= 0.0) {
            throw new LoggableException("Unacceptable epsilon");
        }
        if (vector == null) {
            throw new LoggableException("Unacceptable vector");
        }

        DenseVector mvProduct = multiplyByVector(vector);

        double epsDiag = epsilon * getMaxDiagonal();

        if ((epsilon * vector.getEuclidNorm() * mvProduct.getEuclidNorm()) > vector.getInnerProduct(mvProduct)) {
            epsDiag *= multiplicator;
            addDiagonal(epsDiag);
            mvProduct.add(epsDiag, vector);
        }

        return mvProduct;
    }

    public DenseVector multiplyByVector(DenseVector vector) throws LoggableException {
        if ((vector == null) || (getColumnCount() != vector.getSize())) {
            throw new LoggableException("Unacceptable vector");
        }

        DenseVector mvProduct = DenseVector.getInstance(getRowCount());

        MatrixVectorMult_DDRM.mult(this, vector, mvProduct);

        return mvProduct;
    }

    public DenseMatrix multiplyByMatrix(DenseMatrix matrix) throws LoggableException {
        if ((matrix == null) || (getColumnCount() != matrix.getRowCount()))
            throw new LoggableException("Unacceptable matrix");

        DenseMatrix mmProduct = new DenseMatrix(getRowCount(), matrix.getColumnCount());

        MatrixMatrixMult_DDRM.mult_small(this, matrix, mmProduct);

        return mmProduct;
    }

    public DenseMatrix add(DenseMatrix matrix) throws LoggableException {
        if ((matrix == null) || (!checkOtherMatrix(matrix))) {
            throw new LoggableException("Unacceptable matrix");
        }

        CommonOps_DDRM.addEquals(this, matrix);

        return this;
    }

    public DenseMatrix add(double coeff, DenseMatrix matrix) throws LoggableException {
        if ((matrix == null) || (!checkOtherMatrix(matrix))) {
            throw new LoggableException("Unacceptable matrix");
        }

        CommonOps_DDRM.addEquals(this, coeff, matrix);

        return this;
    }

    public DenseMatrix substract(DenseMatrix matrix) throws LoggableException {
        if ((matrix == null) || (!checkOtherMatrix(matrix))) {
            throw new LoggableException("Unacceptable matrix");
        }

        CommonOps_DDRM.subtractEquals(this, matrix);

        return this;
    }

    public double getEuclidNorm() {
        return NormOps_DDRM.normP2(this);
    }

    public double getMaxElement() {
        return CommonOps_DDRM.elementMax(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getData());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        return hashCode() == obj.hashCode();
    }

    private int getMinIndex() {
        return Math.min(getRowCount(), getColumnCount());
    }

    private boolean checkOtherMatrix(final DenseMatrix pMatrix) {
        return ((getRowCount() == pMatrix.getRowCount()) && (getColumnCount() == pMatrix.getColumnCount()));
    }
}
