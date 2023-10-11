package dev.projects.math.linalgebra.storagestrategy;

import dev.projects.math.linalgebra.access.IDataAccessStrategy;
import dev.projects.utils.exception.LoggableException;

public abstract class AbstractDataStorageStrategy {
    private int rowCount = 0, columnCount = 0;
    private IDataAccessStrategy accessStrategy = null;

    public AbstractDataStorageStrategy(int rowCount,
                                       int columnCount,
                                       IDataAccessStrategy accessStrategy) throws LoggableException {
        if (rowCount < 0) throw new LoggableException("Unacceptable rowCount");
        if (columnCount < 0) throw new LoggableException("Unacceptable columnCount");
        if (accessStrategy == null) throw new LoggableException("Unacceptable strategy");

        setRowCount(rowCount);
        setColumnCount(columnCount);
        setAccessStrategy(accessStrategy);
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) throws LoggableException {
        if (rowCount < 0) throw new LoggableException("Unacceptable rowCount");

        this.rowCount = rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) throws LoggableException {
        if (columnCount < 0) throw new LoggableException("Unacceptable columnCount");

        this.columnCount = columnCount;
    }

    public IDataAccessStrategy getAccessStrategy() {
        return accessStrategy;
    }

    public void setAccessStrategy(IDataAccessStrategy accessStrategy) throws LoggableException {
        if ((accessStrategy == null)
                || (accessStrategy.getRowCount() != getRowCount())
                || (accessStrategy.getColumnCount() != getColumnCount()))
            throw new LoggableException("Unacceptable a access storage strategy");

        this.accessStrategy = accessStrategy;
    }

    protected void verifyRowAndColumn(int row, int column) throws LoggableException {
        if ((row < 0) || (row > (getRowCount() - 1))) throw new LoggableException("Unacceptable row");
        if ((column < 0) || (column > (getColumnCount() - 1))) throw new LoggableException("Unacceptable column");
    }
}
