package dev.projects.math.jnummath.jlinalgebra.access;

import dev.projects.math.jnummath.jlinalgebra.storagestrategy.RegionDataBlockKey;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;

import java.util.List;

public interface IDataAccessStrategy {
    int getRowCount();
    int getColumnCount();

    int getIndex(int row, int column)  ;

    DataAccessStrategyType getType();
    void setType(DataAccessStrategyType type);

    List<RegionDataBlockKey> generateKeys(int maxBlockSize)  ;
}
