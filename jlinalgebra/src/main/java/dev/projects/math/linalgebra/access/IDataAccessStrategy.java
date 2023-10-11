package dev.projects.math.linalgebra.access;

import dev.projects.math.linalgebra.storagestrategy.RegionDataBlockKey;
import dev.projects.utils.exception.LoggableException;

import java.util.List;

public interface IDataAccessStrategy {
    int getRowCount();
    int getColumnCount();

    int getIndex(int row, int column) throws LoggableException;

    DataAccessStrategyType getType();
    void setType(DataAccessStrategyType type);

    List<RegionDataBlockKey> generateKeys(int maxBlockSize) throws LoggableException;
}
