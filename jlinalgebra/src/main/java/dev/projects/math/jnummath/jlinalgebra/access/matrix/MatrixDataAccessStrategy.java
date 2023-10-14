package dev.projects.math.jnummath.jlinalgebra.access.matrix;

import dev.projects.math.jnummath.jlinalgebra.access.IDataAccessStrategy;
import dev.projects.math.jnummath.jlinalgebra.access.AbstractDataAccessStrategy;
import dev.projects.math.jnummath.jlinalgebra.access.DataAccessStrategyType;
import dev.projects.math.jnummath.jlinalgebra.storagestrategy.RegionDataBlockKey;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;

import java.util.List;

public class MatrixDataAccessStrategy
        extends AbstractDataAccessStrategy
        implements IDataAccessStrategy {
    public MatrixDataAccessStrategy(int rowCount, int columnCount) {
        super(rowCount, columnCount, DataAccessStrategyType.useMatrixDataAccess);
    }

    @Override
    public int getIndex(int row, int column)   {
        return getColumnCount() * row + column;
    }

    @Override
    public List<RegionDataBlockKey> generateKeys(int maxBlockSize)   {
        return null;
    }
}
