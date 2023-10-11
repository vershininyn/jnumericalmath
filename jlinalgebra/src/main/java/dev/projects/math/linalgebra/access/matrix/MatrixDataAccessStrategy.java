package dev.projects.math.linalgebra.access.matrix;

import dev.projects.math.linalgebra.access.IDataAccessStrategy;
import dev.projects.math.linalgebra.access.AbstractDataAccessStrategy;
import dev.projects.math.linalgebra.access.DataAccessStrategyType;
import dev.projects.math.linalgebra.storagestrategy.RegionDataBlockKey;
import dev.projects.utils.exception.LoggableException;

import java.util.List;

public class MatrixDataAccessStrategy
        extends AbstractDataAccessStrategy
        implements IDataAccessStrategy {
    public MatrixDataAccessStrategy(int rowCount, int columnCount) {
        super(rowCount, columnCount, DataAccessStrategyType.useMatrixDataAccess);
    }

    @Override
    public int getIndex(int row, int column) throws LoggableException {
        return getColumnCount() * row + column;
    }

    @Override
    public List<RegionDataBlockKey> generateKeys(int maxBlockSize) throws LoggableException {
        return null;
    }
}
