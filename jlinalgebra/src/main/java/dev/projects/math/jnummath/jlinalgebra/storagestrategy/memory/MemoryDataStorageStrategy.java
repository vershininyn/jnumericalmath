package dev.projects.math.jnummath.jlinalgebra.storagestrategy.memory;

import dev.projects.math.jnummath.jlinalgebra.access.IDataAccessStrategy;
import dev.projects.math.jnummath.jlinalgebra.blocks.IDataBlock;
import dev.projects.math.jnummath.jlinalgebra.blocks.memorydoublebuffer.MemoryDoubleDataBlock;
import dev.projects.math.jnummath.jlinalgebra.storagestrategy.RegionDataBlockKey;
import dev.projects.math.jnummath.jlinalgebra.storagestrategy.AbstractDataStorageStrategy;
import dev.projects.math.jnummath.jlinalgebra.storagestrategy.IDataStorageStrategy;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;

import java.util.Map;
import java.util.stream.Collectors;

public class MemoryDataStorageStrategy
        extends AbstractDataStorageStrategy
        implements IDataStorageStrategy {
    private MemoryDoubleDataBlock doubleDataBlock = null;

    public MemoryDataStorageStrategy(int rowCount,
                                     int columnCount,
                                     IDataAccessStrategy accessStrategy)   {
        super(rowCount, columnCount, accessStrategy);

        doubleDataBlock = new MemoryDoubleDataBlock(rowCount * columnCount);
    }

    public MemoryDataStorageStrategy(int rowCount,
                                     int columnCount,
                                     double defaultValue,
                                     IDataAccessStrategy accessStrategy)   {
        this(rowCount, columnCount, accessStrategy);

        fillByValue(defaultValue);
    }

    @Override
    public IDataStorageStrategy fillByValue(double value) {
        doubleDataBlock.fillByValue(value);

        return this;
    }

    @Override
    public double readValue(int row, int column)   {
        verifyRowAndColumn(row, column);

        int index = getAccessStrategy().getIndex(row, column);

        return doubleDataBlock.readValue(index);
    }

    @Override
    public IDataStorageStrategy writeValue(int row, int column, double value)   {
        verifyRowAndColumn(row, column);

        int index = getAccessStrategy().getIndex(row, column);

        doubleDataBlock.writeValue(index, value);

        return this;
    }

    @Override
    public Map<RegionDataBlockKey, IDataBlock> getData()   {
        return Map.of(new RegionDataBlockKey(0, 0, getRowCount(), 0, getColumnCount()),
                doubleDataBlock);
    }

    @Override
    public IDataStorageStrategy setData(Map<RegionDataBlockKey, IDataBlock> data)   {
        if ((data == null)
                || (data.isEmpty())
                || (data.size() != 1)) throw new LoggableException("Unacceptable data");

        RegionDataBlockKey key = data.keySet().stream().collect(Collectors.toList()).get(0);
        IDataBlock block = data.get(key);
        IDataAccessStrategy accessStrategy = getAccessStrategy();

        for (int rowIndex = key.getStartRowIndex(); rowIndex < key.getEndRowIndex(); rowIndex++) {
            for (int columnIndex = key.getStartColumnIndex(); columnIndex < key.getEndColumnIndex(); columnIndex++) {
                int index = accessStrategy.getIndex(rowIndex, columnIndex);

                writeValue(rowIndex, columnIndex, block.readValue(index));
            }
        }

        return this;
    }

    @Override
    public void close() throws Exception {
        doubleDataBlock.close();
        doubleDataBlock = null;
    }
}
