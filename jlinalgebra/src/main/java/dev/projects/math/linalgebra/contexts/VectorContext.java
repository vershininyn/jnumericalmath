package dev.projects.math.linalgebra.contexts;

import dev.projects.math.linalgebra.access.DataAccessStrategyType;
import dev.projects.math.linalgebra.access.IDataAccessStrategy;
import dev.projects.math.linalgebra.access.vector.VectorDataAccessStrategy;
import dev.projects.utils.exception.LoggableException;

public class VectorContext extends AbstractContext<VectorContext> {
    public VectorContext(int rowCount,
                         int columnCount,
                         int switchThreshold,
                         DataAccessStrategyType accessStrategyType) throws LoggableException {
        super(rowCount, columnCount, switchThreshold, accessStrategyType);

        setFluentContext(this);
    }

    public VectorContext(int rowCount,
                         int columnCount,
                         int switchThreshold,
                         double defaultValue,
                         DataAccessStrategyType accessStrategyType) throws LoggableException {
        this(rowCount, columnCount, switchThreshold, accessStrategyType);

        fillByValue(defaultValue);
    }

    public VectorContext[] splitAtIndex(int index, boolean middleToLeft) throws LoggableException {
        return null;
    }

    @Override
    protected IDataAccessStrategy getAccessStrategy(DataAccessStrategyType accessType) {
        return new VectorDataAccessStrategy(getRowCount(), getColumnCount(), accessType);
    }
}
