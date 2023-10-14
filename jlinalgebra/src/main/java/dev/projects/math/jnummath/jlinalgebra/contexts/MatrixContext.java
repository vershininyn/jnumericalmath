package dev.projects.math.jnummath.jlinalgebra.contexts;

import dev.projects.math.jnummath.jlinalgebra.access.matrix.MatrixDataAccessStrategy;
import dev.projects.math.jnummath.jlinalgebra.access.DataAccessStrategyType;
import dev.projects.math.jnummath.jlinalgebra.access.IDataAccessStrategy;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;

public class MatrixContext extends AbstractContext<MatrixContext> {

    public MatrixContext(int rowCount,
                         int columnCount,
                         int switchThreshold,
                         DataAccessStrategyType accessStrategyType)   {
        super(rowCount, columnCount, switchThreshold, accessStrategyType);

        setFluentContext(this);
    }

    public MatrixContext(int rowCount,
                         int columnCount,
                         int switchThreshold,
                         double defaultValue,
                         DataAccessStrategyType accessStrategyType)   {
        this(rowCount, columnCount, switchThreshold, accessStrategyType);

        getStorageStrategy().fillByValue(defaultValue);
    }

    @Override
    protected IDataAccessStrategy getAccessStrategy(DataAccessStrategyType accessType) {
        return new MatrixDataAccessStrategy(getRowCount(), getColumnCount());
    }
}
