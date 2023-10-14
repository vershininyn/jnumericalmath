package dev.projects.math.jnummath.jlinalgebra.contexts;

import dev.projects.math.jnummath.jlinalgebra.access.DataAccessStrategyType;
import dev.projects.math.jnummath.jlinalgebra.access.IDataAccessStrategy;
import dev.projects.math.jnummath.jlinalgebra.blocks.IDataBlock;
import dev.projects.math.jnummath.jlinalgebra.storagestrategy.IDataStorageStrategy;
import dev.projects.math.jnummath.jlinalgebra.storagestrategy.RegionDataBlockKey;
import dev.projects.math.jnummath.jlinalgebra.storagestrategy.mappeddoublebuffer.MappedDoubleBufferDataStorageStrategy;
import dev.projects.math.jnummath.jlinalgebra.storagestrategy.memory.MemoryDataStorageStrategy;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;

import java.util.Map;

public abstract class AbstractContext<TContext> implements AutoCloseable {
    private int rowCount = 0, columnCount = 0, size = 0, switchThreshold = 0;

    private IDataStorageStrategy storageStrategy = null;

    private TContext fluentContext = null;

    public AbstractContext(int rowCount,
                           int columnCount,
                           int switchThreshold,
                           DataAccessStrategyType accessStrategyType)   {
        if (rowCount < 1) throw new LoggableException("Unacceptable rowCount");
        if (columnCount < 1) throw new LoggableException("Unacceptable columnCount");
        if (accessStrategyType == null) throw new LoggableException("Unacceptable accessStrategy");

        setRowCount(rowCount);
        setColumnCount(columnCount);
        setSize(rowCount * columnCount);
        setSwitchThreshold(switchThreshold);

        setStorageStrategy(switchStorageStrategy(rowCount, columnCount, accessStrategyType));
    }

    public int getSize() {
        return size;
    }

    public TContext setSize(int size) {
        this.size = size;

        return fluentContext;
    }

    public int getRowCount() {
        return rowCount;
    }

    public TContext setRowCount(int rowCount) {
        this.rowCount = rowCount;

        return fluentContext;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public TContext setColumnCount(int columnCount) {
        this.columnCount = columnCount;

        return fluentContext;
    }

    public int getSwitchThreshold() {
        return switchThreshold;
    }

    public TContext setSwitchThreshold(int switchThreshold) {
        this.switchThreshold = switchThreshold;

        return fluentContext;
    }

    public DataAccessStrategyType getAccessStrategyType() {
        return getStorageStrategy().getAccessStrategy().getType();
    }

    public TContext setAccessStrategyType(DataAccessStrategyType type)   {
        if (storageStrategy != null) {
            storageStrategy.setAccessStrategy(getAccessStrategy(type));
        }

        return fluentContext;
    }

    public Map<RegionDataBlockKey, IDataBlock> getData()   {
        return storageStrategy.getData();
    }

    public TContext setData(Map<RegionDataBlockKey, IDataBlock> data)   {
        if ((data == null) || (data.isEmpty())) throw new LoggableException("Unacceptable list of data blocks");

        storageStrategy.setData(data);

        return fluentContext;
    }

    public IDataStorageStrategy getStorageStrategy() {
        return storageStrategy;
    }

    private TContext setStorageStrategy(IDataStorageStrategy storageStrategy)   {
        if (storageStrategy == null) throw new LoggableException("Unacceptable a storage strategy");

        this.storageStrategy = storageStrategy;

        return fluentContext;
    }

    public void close() throws Exception {
        if (storageStrategy != null) {
            storageStrategy.close();
        }
    }

    public TContext fillByValue(double value) {
        getStorageStrategy().fillByValue(value);

        return fluentContext;
    }

    public double readValue(int row, int column)   {
        return storageStrategy.readValue(row, column);
    }

    public TContext writeValue(int row, int column, double value)   {
        storageStrategy.writeValue(row, column, value);

        return fluentContext;
    }

    protected IDataStorageStrategy switchStorageStrategy(int rowCount,
                                                         int columnCount,
                                                         DataAccessStrategyType accessStrategyType)   {
        IDataAccessStrategy accessStrategy = getAccessStrategy(accessStrategyType);

        return (getSize() > getSwitchThreshold())
                ? (new MappedDoubleBufferDataStorageStrategy(rowCount, columnCount, accessStrategy))
                : (new MemoryDataStorageStrategy(rowCount, columnCount, accessStrategy));

    }

    protected abstract IDataAccessStrategy getAccessStrategy(DataAccessStrategyType accessType);

    protected void setFluentContext(TContext context) {
        this.fluentContext = context;
    }
}
