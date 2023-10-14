package dev.projects.math.jnummath.jlinalgebra.storagestrategy;

import dev.projects.math.jnummath.jlinalgebra.access.IDataAccessStrategy;
import dev.projects.math.jnummath.jlinalgebra.blocks.IDataBlock;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;

import java.util.Map;

public interface IDataStorageStrategy extends AutoCloseable {
    int getRowCount();

    void setRowCount(int rowCount)  ;

    int getColumnCount();

    void setColumnCount(int columnCount)  ;

    IDataStorageStrategy fillByValue(double value);

    double readValue(int row, int column)  ;

    IDataStorageStrategy writeValue(int row, int column, double value)  ;

    IDataAccessStrategy getAccessStrategy();

    void setAccessStrategy(IDataAccessStrategy accessStrategy)  ;

    Map<RegionDataBlockKey, IDataBlock> getData()  ;

    IDataStorageStrategy setData(Map<RegionDataBlockKey, IDataBlock> data)  ;
}
