package dev.projects.math.jnummath.jlinalgebra.dense;

import dev.projects.math.jnummath.jkernel.ICloneable;
import dev.projects.math.jnummath.jkernel.ICountable;
import dev.projects.math.jnummath.jlinalgebra.VectorType;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;
import org.ejml.concurrency.EjmlConcurrency;
import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.NormOps_DDRM;
import org.ejml.dense.row.mult.MatrixMatrixMult_DDRM;
import org.ejml.dense.row.mult.VectorVectorMult_DDRM;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author vyn
 */
public class DenseVector extends DMatrixRMaj implements DMatrix, ICloneable<DenseVector>, ICountable {
    private VectorType vectorType = VectorType.isColumnVector;

    public static DenseVector getInstance(int size)   {
        if (size <= 0) {
            throw new LoggableException("Unacceptable vector size");
        }

        return DenseVector.getInstance(size, 0.0);
    }

    public static DenseVector getInstance(double[] data)   {
        if (data == null) {
            throw new LoggableException("Unacceptable array of double[]");
        }
        if (data.length <= 0) {
            throw new LoggableException("Unacceptable dimension");
        }

        DenseVector vector = new DenseVector(data.length);
        vector.setData(data);

        return vector;
    }

    public static DenseVector getInstance(DenseVector vector)   {
        if ((vector == null) || (vector.getSize() <= 0)) {
            throw new LoggableException("Unacceptable vector object");
        }

        return new DenseVector(vector);
    }

    public static DenseVector getInstance(int size, double value)   {
        if (size <= 0) {
            throw new LoggableException("Unacceptable row/column count");
        }

        double[] data = new double[size];

        Arrays.fill(data, value);

        return new DenseVector(data);
    }

    public static DenseVector getInstance(List<Double> data)   {
        if ((data == null) || (data.isEmpty())) {
            throw new LoggableException("Unacceptable data list");
        }

        int size = data.size();

        DenseVector vector = getInstance(size);

        for (int index = 0; index < size; index++) {
            vector.setValue(index, data.get(index));
        }

        return vector;
    }

    public static DenseVector getRandomizedVector(int length, double scalFactor)   {
        if (length <= 0) {
            throw new LoggableException("Unacceptable length of vector");
        }

        DenseVector out = getInstance(length);

        for (int index = 0; index < length; index++) {
            double value = scalFactor * (2.0 * ThreadLocalRandom.current().nextDouble() - 1.0);

            out.setValue(index, value);
        }

        return out.multiplyByCoefficient(1.0 / out.getEuclidNorm());
    }

    private DenseVector(int size) {
        super(size, 1);
        EjmlConcurrency.USE_CONCURRENT = true;
    }

    private DenseVector(double[] data) {
        super(data.length, 1, false, data);
        EjmlConcurrency.USE_CONCURRENT = true;
    }

    private DenseVector(DenseVector vector) {
        super(vector.getData());
        EjmlConcurrency.USE_CONCURRENT = true;
    }

    public VectorType getVectorType() {
        return vectorType;
    }

    @Override
    public int getSize() {
        return getNumElements();
    }

    public DenseVector shiftByCoefficient(double coeff)   {
        for (int index = 0; index < getSize(); index++) {
            double value = getValue(index);

            value = (value < 0.0) ? (value - coeff) : (value + coeff);

            setValue(index, value);
        }

        return this;
    }

    public DenseVector multiplyByCoefficient(double pCoefficient) {
        CommonOps_DDRM.scale(pCoefficient, this);
        return this;
    }

    public DenseMatrix multiplyByVector(DenseVector vector)   {
        if ((vector == null) || (vector.getVectorType() == VectorType.isColumnVector)) {
            throw new LoggableException("Unacceptable matrix");
        }

        DenseVector currentVector = this;
        DenseMatrix vvProduct = DenseMatrix.getInstance(getSize(), getSize());

        if (getVectorType() == VectorType.isRowVector) {
            currentVector = deepCopy().transposeInPlace();
        } else {
            if (getSize() != vector.getSize()) {
                throw new LoggableException("Unacceptable vector");
            }
        }

        MatrixMatrixMult_DDRM.mult_small(currentVector, vector, vvProduct);

        return vvProduct;
    }

    public DenseVector transposeInPlace() {
        CommonOps_DDRM.transpose(this);
        vectorType = (getVectorType() == VectorType.isColumnVector) ? VectorType.isRowVector : VectorType.isColumnVector;

        return this;
    }

    public DenseVector add(DenseVector vector)   {
        if ((vector == null) || (!checkOtherVector(vector))) {
            throw new LoggableException("Unacceptable matrix");
        }

        CommonOps_DDRM.addEquals(this, vector);

        return this;
    }

    public DenseVector add(double coeff, DenseVector vector)   {
        if ((vector == null) || (!checkOtherVector(vector))) {
            throw new LoggableException("Unacceptable matrix");
        }

        CommonOps_DDRM.addEquals(this, coeff, vector);

        return this;
    }

    public DenseVector substract(DenseVector vector)   {
        if ((vector == null) || (!checkOtherVector(vector))) {
            throw new LoggableException("Unacceptable matrix");
        }

        CommonOps_DDRM.subtractEquals(this, vector);

        return this;
    }

    public DenseVector substract(double pCoeff, DenseVector vector)   {
        if ((vector == null) || (!checkOtherVector(vector))) {
            throw new LoggableException("Unacceptable matrix");
        }

        return add(-1.0 * pCoeff, vector);
    }

    public double getEuclidNorm() {
        return NormOps_DDRM.normP2(this);
    }

    public double getInnerProduct(DenseVector vector)   {
        if ((vector == null) || !checkOtherVector(vector)) {
            throw new LoggableException("Unacceptable vector");
        }

        return VectorVectorMult_DDRM.innerProd(this, vector);
    }

    public DenseVector convertToColumnVector() {
        if (getVectorType() == VectorType.isColumnVector) {
            return this;
        }

        transposeInPlace();

        return this;
    }

    public DenseVector convertToRowVector() {
        if (getVectorType() == VectorType.isRowVector) {
            return this;
        }

        transposeInPlace();

        return this;
    }

    public DenseVector extendByVector(DenseVector vector)   {
        if ((vector == null) || (vector.getSize() == 0)) throw new LoggableException("Unacceptable vector");

        return extendByArray(vector.getData());
    }

    public DenseVector extendByDouble(double value)   {
        return extendByArray(new double[]{value});
    }

    public DenseVector extendByArray(double[] array)   {
        if ((array == null) || (array.length == 0)) throw new LoggableException("Unacceptable array");

        double[] outArray = new double[getSize() + array.length];

        System.arraycopy(getData(), 0, outArray, 0, getSize());
        System.arraycopy(array, 0, outArray, getSize(), array.length);

        return DenseVector.getInstance(outArray);
    }

    public DenseVector[] splitMiddleToLeftAtIndex(int index)   {
        return splitAtIndex(index, true);
    }

    public DenseVector[] splitMiddleToRigthAtIndex(int index)   {
        return splitAtIndex(index, false);
    }

    public DenseVector[] splitAtIndex(int index, boolean middleToLeft)   {
        int size = getSize(), sizeMinusOne = size - 1;

        if ((index < 0) || (index > sizeMinusOne)) throw new LoggableException("Unacceptable index");

        double[] data = getData(), leftArray, rigthArray;

        if (index == 0) {
            leftArray = Arrays.copyOfRange(data, 0, 1);
            rigthArray = Arrays.copyOfRange(data, 1, size);
        } else if (index == sizeMinusOne) {
            leftArray = Arrays.copyOfRange(data, 0, sizeMinusOne);
            rigthArray = Arrays.copyOfRange(data, sizeMinusOne, size);
        } else {
            if (middleToLeft) {
                leftArray = Arrays.copyOfRange(data, 0, index + 1);
                rigthArray = Arrays.copyOfRange(data, index + 1, size);
            } else {
                leftArray = Arrays.copyOfRange(data, 0, index);
                rigthArray = Arrays.copyOfRange(data, index, size);
            }
        }

        return new DenseVector[]{DenseVector.getInstance(leftArray), DenseVector.getInstance(rigthArray)};
    }

    @Override
    public DenseVector deepCopy() {
        return new DenseVector(this);
    }

    public DenseVector setValue(int index, double value) {
        if (getVectorType() == VectorType.isColumnVector) {
            unsafe_set(index, 0, value);
        } else unsafe_set(0, index, value);

        return this;
    }

    public double getValue(int index) {
        if (getVectorType() == VectorType.isColumnVector) {
            return unsafe_get(index, 0);
        } else return unsafe_get(0, index);
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

    private boolean checkOtherVector(DenseVector vector) {
        return (getSize() == vector.getSize());
    }
}
