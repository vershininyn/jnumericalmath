package dev.projects.math.linalgebra.contexts;

import dev.projects.math.linalgebra.access.DataAccessStrategyType;
import dev.projects.math.linalgebra.access.IDataAccessStrategy;
import dev.projects.math.linalgebra.access.matrix.MatrixDataAccessStrategy;
import dev.projects.utils.exception.LoggableException;

public class MatrixContext extends AbstractContext<MatrixContext> {

    public MatrixContext(int rowCount,
                         int columnCount,
                         int switchThreshold,
                         DataAccessStrategyType accessStrategyType) throws LoggableException {
        super(rowCount, columnCount, switchThreshold, accessStrategyType);

        setFluentContext(this);
    }

    public MatrixContext(int rowCount,
                         int columnCount,
                         int switchThreshold,
                         double defaultValue,
                         DataAccessStrategyType accessStrategyType) throws LoggableException {
        this(rowCount, columnCount, switchThreshold, accessStrategyType);

        getStorageStrategy().fillByValue(defaultValue);
    }

    @Override
    protected IDataAccessStrategy getAccessStrategy(DataAccessStrategyType accessType) {
        return new MatrixDataAccessStrategy(getRowCount(), getColumnCount());
    }
}
