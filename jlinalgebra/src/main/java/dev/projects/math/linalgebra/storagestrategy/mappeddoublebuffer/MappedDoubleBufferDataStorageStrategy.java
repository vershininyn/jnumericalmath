package dev.projects.math.linalgebra.storagestrategy.mappeddoublebuffer;

import dev.projects.math.linalgebra.access.IDataAccessStrategy;
import dev.projects.math.linalgebra.blocks.IDataBlock;
import dev.projects.math.linalgebra.blocks.mappeddoublebuffer.MappedDoubleDataBlock;
import dev.projects.math.linalgebra.storagestrategy.IDataStorageStrategy;
import dev.projects.math.linalgebra.storagestrategy.RegionDataBlockKey;
import dev.projects.math.linalgebra.storagestrategy.AbstractDataStorageStrategy;
import dev.projects.utils.exception.LoggableException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappedDoubleBufferDataStorageStrategy
        extends AbstractDataStorageStrategy
        implements IDataStorageStrategy {
    private int actualBlockSize = 0;

    private final int DOUBLE_OFFSET = 8;

    private final Map<RegionDataBlockKey, IDataBlock> doubleDataBlocksMap = new HashMap<>(128);

    public MappedDoubleBufferDataStorageStrategy(int rowCount,
                                                 int columnCount,
                                                 IDataAccessStrategy accessStrategy) throws LoggableException {
        super(rowCount, columnCount, accessStrategy);

        actualBlockSize = Math.min(Integer.MAX_VALUE / DOUBLE_OFFSET, rowCount * columnCount);

        List<RegionDataBlockKey> keysList = accessStrategy.generateKeys(actualBlockSize);

        for(RegionDataBlockKey key: keysList) {
            doubleDataBlocksMap.put(key, new MappedDoubleDataBlock(actualBlockSize));
        }
    }

    public MappedDoubleBufferDataStorageStrategy(int rowCount,
                                                 int columnCount,
                                                 double defaultValue,
                                                 IDataAccessStrategy accessStrategy) throws LoggableException {
        this(rowCount, columnCount, accessStrategy);

        fillByValue(defaultValue);
    }

    @Override
    public IDataStorageStrategy fillByValue(double value) {
        doubleDataBlocksMap.values().forEach(block -> {
            try {
                block.fillByValue(value);
            } catch (LoggableException e) {
                throw new RuntimeException(e);
            }
        });

        return this;
    }

    @Override
    public double readValue(int row, int column) throws LoggableException {
        verifyRowAndColumn(row, column);

        int index = getAccessStrategy().getIndex(row, column);
        final int blockIndex = index / actualBlockSize;

        IDataBlock block = doubleDataBlocksMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getBlockIndex() == blockIndex)
                .findFirst()
                .get()
                .getValue();

        return block.readValue(index);
    }

    @Override
    public IDataStorageStrategy writeValue(int row, int column, double value) throws LoggableException {
        verifyRowAndColumn(row, column);

        int index = getAccessStrategy().getIndex(row, column);
        final int blockIndex = index / actualBlockSize;

        IDataBlock block = doubleDataBlocksMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getBlockIndex() == blockIndex)
                .findFirst()
                .get()
                .getValue();

        block.writeValue(index, value);

        return this;
    }

    @Override
    public Map<RegionDataBlockKey, IDataBlock> getData() {
        return doubleDataBlocksMap;
    }

    @Override
    public IDataStorageStrategy setData(Map<RegionDataBlockKey, IDataBlock> data) throws LoggableException {
        if ((data == null)
                || (data.isEmpty())
                || (data.size() != doubleDataBlocksMap.size()))
            throw new LoggableException("Unacceptable a data blocks list");

        for (RegionDataBlockKey regionKey : data.keySet()) {
            doubleDataBlocksMap.get(regionKey).fillByData(data.get(regionKey));
        }

        return this;
    }

    @Override
    public void close() throws Exception {
        doubleDataBlocksMap.values().forEach(block -> {
            try {
                if (block != null) {
                    block.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
