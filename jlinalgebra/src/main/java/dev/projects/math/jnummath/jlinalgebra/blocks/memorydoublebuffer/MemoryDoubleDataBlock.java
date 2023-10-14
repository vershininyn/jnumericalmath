package dev.projects.math.jnummath.jlinalgebra.blocks.memorydoublebuffer;

import dev.projects.math.jnummath.jlinalgebra.blocks.IDataBlock;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;

import java.util.Arrays;

public class MemoryDoubleDataBlock implements IDataBlock {
    private int size = 0;
    private double[] memoryDataBlock = null;

    public MemoryDoubleDataBlock(int size)   {
        if (size < 1) throw new LoggableException("Unacceptable size");

        this.size = size;
        memoryDataBlock = new double[size];
        fillByValue(0.0);
    }


    public MemoryDoubleDataBlock(int size, double fillValue)   {
        this(size);

        fillByValue(fillValue);
    }

    public MemoryDoubleDataBlock(double[] data)   {
        if ((data == null) || (data.length < 1)) throw new LoggableException("Unacceptable array of data");

        this.size = data.length;
        memoryDataBlock = new double[data.length];
        System.arraycopy(data, 0, memoryDataBlock, 0, data.length);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public IDataBlock fillByValue(double value) {
        Arrays.fill(memoryDataBlock, value);

        return this;
    }

    @Override
    public IDataBlock fillByData(IDataBlock block)   {
        if ((block == null) || (block.getSize() != memoryDataBlock.length))
            throw new LoggableException("Unacceptable block");

        int size = block.getSize();

        for (int index = 0; index < size; index++) {
            memoryDataBlock[index] = block.readValue(index);
        }

        return this;
    }

    @Override
    public double readValue(int index)   {
        if ((index < 0) || (index > (getSize() - 1))) throw new LoggableException("Unacceptable index");

        //TODO: search for some other data structures

        return memoryDataBlock[index];
    }

    @Override
    public IDataBlock writeValue(int index, double value)   {
        if ((index < 0) || (index > (getSize() - 1))) throw new LoggableException("Unacceptable index");

        //TODO: search for some other data structures

        memoryDataBlock[index] = value;

        return this;
    }

    @Override
    public double getMaxElement()   {
        return Arrays.stream(memoryDataBlock).max().getAsDouble();
    }

    @Override
    public IDataBlock multiplyByCoefficient(double coeff)   {
        memoryDataBlock = Arrays.stream(memoryDataBlock).map(value -> coeff * value).toArray();

        return this;
    }

    @Override
    public IDataBlock multiplyByVector(final IDataBlock data)   {
        int size = getSize();

        if ((data == null)
                || (size != data.getSize())) throw new LoggableException("Unacceptable data block");

        double[] result = new double[size];

        for (int index = 0; index < size; index++) {
            result[index] = readValue(index) * data.readValue(index);
        }

        return new MemoryDoubleDataBlock(result);
    }

    @Override
    public double getInnerProduct(IDataBlock block)   {
        int size = getSize();

        if ((block == null)
                || (size != block.getSize())) throw new LoggableException("Unacceptable block");

        double product = 0.0;

        for (int index = 0; index < size; index++) {
            product += readValue(index) * block.readValue(index);
        }

        return product;
    }

    @Override
    public IDataBlock shiftByCoefficient(double coeff)   {
        for (int index = 0; index < getSize(); index++) {
            double value = readValue(index);

            value = (value < 0.0) ? (value - coeff) : (value + coeff);

            writeValue(index, value);
        }

        return this;
    }

    @Override
    public void close() throws Exception {
        memoryDataBlock = null;
    }
}
