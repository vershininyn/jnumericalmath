package dev.projects.math.jnummath.jlinalgebra.contexts;

import dev.projects.math.jnummath.jlinalgebra.access.DataAccessStrategyType;
import dev.projects.math.jnummath.jlinalgebra.access.IDataAccessStrategy;
import dev.projects.math.jnummath.jlinalgebra.access.vector.VectorDataAccessStrategy;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;

public class VectorContext extends AbstractContext<VectorContext> {
    public VectorContext(int rowCount,
                         int columnCount,
                         int switchThreshold,
                         DataAccessStrategyType accessStrategyType)   {
        super(rowCount, columnCount, switchThreshold, accessStrategyType);

        setFluentContext(this);
    }

    public VectorContext(int rowCount,
                         int columnCount,
                         int switchThreshold,
                         double defaultValue,
                         DataAccessStrategyType accessStrategyType)   {
        this(rowCount, columnCount, switchThreshold, accessStrategyType);

        fillByValue(defaultValue);
    }

    public VectorContext[] splitAtIndex(int index, boolean middleToLeft)   {
        return null;
    }

    @Override
    protected IDataAccessStrategy getAccessStrategy(DataAccessStrategyType accessType) {
        return new VectorDataAccessStrategy(getRowCount(), getColumnCount(), accessType);
    }
}
