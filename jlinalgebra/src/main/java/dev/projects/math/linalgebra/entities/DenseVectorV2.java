package dev.projects.math.linalgebra.entities;

import dev.projects.math.linalgebra.contexts.VectorContext;
import dev.projects.math.linalgebra.VectorType;
import dev.projects.math.linalgebra.access.DataAccessStrategyType;
import dev.projects.math.linalgebra.blocks.IDataBlock;
import dev.projects.math.linalgebra.blocks.memorydoublebuffer.MemoryDoubleDataBlock;
import dev.projects.math.linalgebra.storagestrategy.RegionDataBlockKey;
import dev.projects.utils.exception.LoggableException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class DenseVectorV2 implements IVector {
    private final static int MAX_SWITCH_THRESHOLD = 3000;
    private VectorType vectorType = VectorType.isColumnVector;

    private VectorContext context = null;

    public static IVector getInstance(int size, int switchThreshold) throws LoggableException {
        if (switchThreshold < 1) throw new LoggableException("Unacceptable a threshold");
        if (size < 1) throw new LoggableException("Unacceptable vector size");

        return new DenseVectorV2(size, 1, switchThreshold, DataAccessStrategyType.useColumnVectorDataAccess);
    }

    public static IVector getInstance(int size) throws LoggableException {
        return getInstance(size, MAX_SWITCH_THRESHOLD);
    }

    public static IVector getInstance(double[] data, int switchThreshold) throws LoggableException {
        if (data == null) throw new LoggableException("Unacceptable array of double[]");
        if (data.length < 1) throw new LoggableException("Unacceptable dimension");
        if (switchThreshold < 1) throw new LoggableException("Unacceptable a switch threshold");

        IVector vector = getInstance(data.length, switchThreshold);
        vector.setData(Map.of(new RegionDataBlockKey(0,0, vector.getSize(), 0, 1),
                new MemoryDoubleDataBlock(data)));

        return vector;
    }

    public static IVector getInstance(double[] data) throws LoggableException {
        return getInstance(data, MAX_SWITCH_THRESHOLD);
    }

    public static IVector getInstance(int size, int switchThreshold, double value) throws LoggableException {
        if (size < 1) throw new LoggableException("Unacceptable size");

        double[] data = new double[size];
        Arrays.fill(data, value);

        return getInstance(data, switchThreshold);
    }

    public static IVector getInstance(IVector vector) throws LoggableException {
        if ((vector == null) || (vector.getSize() < 1)) throw new LoggableException("Unacceptable vector object");

        return new DenseVectorV2(vector);
    }

    public static IVector getInstance(List<Double> data) throws LoggableException {
        if ((data == null) || (data.isEmpty())) throw new LoggableException("Unacceptable data list");

        int size = data.size();
        IVector vector = getInstance(size);

        for (int index = 0; index < size; index++) {
            vector.setValue(index, data.get(index));
        }

        return vector;
    }

    public static IVector getRandomizedVector(int length, double scalFactor) throws LoggableException {
        if (length < 1) throw new LoggableException("Unacceptable length of vector");

        IVector out = getInstance(length);
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        for (int index = 0; index < length; index++) {
            double value = scalFactor * (2.0 * rnd.nextDouble() - 1.0);

            out.setValue(index, value);
        }

        return out;
    }

    private DenseVectorV2(int rowCount,
                          int columnCount,
                          int switchThreshold,
                          DataAccessStrategyType accessStrategyType) throws LoggableException {
        context = new VectorContext(rowCount, columnCount, switchThreshold, accessStrategyType);
    }

    private DenseVectorV2(int rowCount,
                          int columnCount,
                          int switchThreshold,
                          double defaultValue,
                          DataAccessStrategyType accessStrategyType) throws LoggableException {
        this(rowCount, columnCount, switchThreshold, accessStrategyType);

        context.fillByValue(defaultValue);
    }

    private DenseVectorV2(IVector vector) throws LoggableException {
        if (vector == null) throw new LoggableException("Unacceptable vector");

        context = new VectorContext(vector.getRowCount(), vector.getColumnCount(), vector.getSwitchThreshold(), vector.getAccessStrategyType());

        setData(vector.getData());
    }

    @Override
    public IVector deepCopy() throws LoggableException {
        return new DenseVectorV2(this);
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
    public double getEuclidNorm() throws LoggableException {
        return Math.sqrt(getInnerProduct(this));
    }

    @Override
    public double getMaxElement() throws LoggableException {
        double maxValue = Double.NEGATIVE_INFINITY;

        for (IDataBlock block : getData().values()) {
            maxValue = Math.max(maxValue, block.getMaxElement());
        }

        return maxValue;
    }

    @Override
    public IVector multiplyByCoefficient(double coeff) throws LoggableException {
        for (IDataBlock block : getData().values()) {
            block.multiplyByCoefficient(coeff);
        }

        return this;
    }

    @Override
    public VectorType getVectorType() {
        return vectorType;
    }

    @Override
    public IVector shiftByCoefficient(double coeff) throws LoggableException {
        for (IDataBlock block : getData().values()) {
            block.shiftByCoefficient(coeff);
        }

        return this;
    }

    @Override
    public IMatrix multiplyByVector(IVector vector) throws LoggableException {
        if ((vector == null)
                || (vector.getVectorType() != VectorType.isRowVector)
                || (vector.getRowCount() != 1)) throw new LoggableException("Unacceptable vector");

        Map<RegionDataBlockKey, IDataBlock> currentMap = getData();
        Map<RegionDataBlockKey, IDataBlock> otherMap = vector.getData();

        if ((otherMap == null)
                || (otherMap.isEmpty())) throw new LoggableException("Unacceptable vector: the map is empty");

        if ((currentMap.size() != otherMap.size()))
            throw new LoggableException("Unacceptable vector: size of the map is diff");

        IMatrix matrix = DenseMatrixV2.getInstance(getRowCount(), vector.getColumnCount());

        for(RegionDataBlockKey key: currentMap.keySet()) {
            IDataBlock currentBlock = currentMap.get(key);
            if (currentBlock == null) throw new LoggableException("The data block of current vector is null");

            IDataBlock otherBlock = otherMap.get(key);
            if (otherBlock == null) throw new LoggableException("The data block of vector is null");

            IDataBlock subMatrix = currentBlock.multiplyByVector(otherBlock);

            matrix.fillByRegion(subMatrix, key);
        }

        return matrix;
    }

    @Override
    public IVector setData(Map<RegionDataBlockKey, IDataBlock> data) throws LoggableException {
        if ((data == null) || (data.isEmpty())) throw new LoggableException("Unacceptable list of data blocks");

        //TODO: verify a list of data blocks

        context.setData(data);

        return this;
    }

    @Override
    public Map<RegionDataBlockKey, IDataBlock> getData() throws LoggableException {
        return context.getData();
    }

    @Override
    public IVector transposeInPlace() {
        return null;
    }

    @Override
    public IVector add(IVector vector) throws LoggableException {
        return add(1.0, vector);
    }

    @Override
    public IVector add(double coeff, IVector vector) throws LoggableException {
        return null;
    }

    @Override
    public IVector substract(IVector vector) throws LoggableException {
        return substract(1.0, vector);
    }

    @Override
    public IVector substract(double coeff, IVector vector) throws LoggableException {
        return null;
    }

    @Override
    public double getInnerProduct(IVector vector) throws LoggableException {
        if (vector == null) throw new LoggableException("Unacceptable vector");

        List<IDataBlock> currentBlocks = getData().values().stream().toList(),
                otherBlocks = vector.getData().values().stream().toList();

        if ((otherBlocks == null)
                || (otherBlocks.isEmpty())
                || (currentBlocks.size() != otherBlocks.size()))
            throw new LoggableException("Unacceptable vector: blocks count is diff");

        int size = currentBlocks.size();
        double product = 0.0;

        for (int index = 0; index < size; index++) {
            product += currentBlocks.get(index).getInnerProduct(otherBlocks.get(index));
        }

        return product;
    }

    @Override
    public IVector convertToColumnVector() throws LoggableException {
        if (vectorType == VectorType.isColumnVector) return this;

        vectorType = VectorType.isColumnVector;
        context.setAccessStrategyType(DataAccessStrategyType.useColumnVectorDataAccess);

        return this;
    }

    @Override
    public IVector convertToRowVector() throws LoggableException {
        if (vectorType == VectorType.isRowVector) return this;

        vectorType = VectorType.isRowVector;
        context.setAccessStrategyType(DataAccessStrategyType.useRowVectorDataAccess);

        return this;
    }

    @Override
    public IVector extendByVector(IVector vector) throws LoggableException {
        return null;
    }

    @Override
    public IVector extendByDouble(double value) throws LoggableException {
        return extendByArray(new double[]{value});
    }

    @Override
    public IVector extendByArray(double[] array) throws LoggableException {
        return null;
    }

    @Override
    public IVector[] splitMiddleToLeftAtIndex(int index) throws LoggableException {
        return splitAtIndex(index, true);
    }

    @Override
    public IVector[] splitMiddleToRigthAtIndex(int index) throws LoggableException {
        return splitAtIndex(index, false);
    }

    @Override
    public IVector[] splitAtIndex(int index, boolean middleToLeft) throws LoggableException {
        return new IVector[0];
    }

    @Override
    public IVector setValue(int index, double value) throws LoggableException {
        int row = (getVectorType() == VectorType.isColumnVector) ? (index) : (0),
                column = (getVectorType() == VectorType.isColumnVector) ? (0) : (index);

        context.writeValue(row, column, value);

        return this;
    }

    @Override
    public double getValue(int index) throws LoggableException {
        int row = (getVectorType() == VectorType.isColumnVector) ? (index) : (0),
                column = (getVectorType() == VectorType.isColumnVector) ? (0) : (index);

        return context.readValue(row, column);
    }

    @Override
    public int getSwitchThreshold() {
        return context.getSwitchThreshold();
    }

    @Override
    public DataAccessStrategyType getAccessStrategyType() {
        return context.getAccessStrategyType();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public void close() throws Exception {
        if (context != null) {
            context.close();
        }
    }
}
