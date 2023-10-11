package dev.projects.math.linalgebra.storagestrategy;

import dev.projects.math.linalgebra.access.IDataAccessStrategy;
import dev.projects.math.linalgebra.blocks.IDataBlock;
import dev.projects.utils.exception.LoggableException;

import java.util.Map;

public interface IDataStorageStrategy extends AutoCloseable {
    int getRowCount();

    void setRowCount(int rowCount) throws LoggableException;

    int getColumnCount();

    void setColumnCount(int columnCount) throws LoggableException;

    IDataStorageStrategy fillByValue(double value);

    double readValue(int row, int column) throws LoggableException;

    IDataStorageStrategy writeValue(int row, int column, double value) throws LoggableException;

    IDataAccessStrategy getAccessStrategy();

    void setAccessStrategy(IDataAccessStrategy accessStrategy) throws LoggableException;

    Map<RegionDataBlockKey, IDataBlock> getData() throws LoggableException;

    IDataStorageStrategy setData(Map<RegionDataBlockKey, IDataBlock> data) throws LoggableException;
}
