package dev.projects.math.linalgebra.access;

import dev.projects.utils.exception.LoggableException;

public abstract class AbstractDataAccessStrategy {
    private int rowCount = 0, columnCount = 0;

    private DataAccessStrategyType accessType = null;

    public AbstractDataAccessStrategy(int rowCount,
                                      int columnCount,
                                      DataAccessStrategyType type) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        setType(type);
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setType(DataAccessStrategyType type) {
        this.accessType = type;
    }

    public DataAccessStrategyType getType() {
        return accessType;
    }

    public abstract int getIndex(int row, int column) throws LoggableException;

    protected void verifyIndexes(int blockIndex,
                                 int startRowIndex,
                                 int endRowIndex,
                                 int startColumnIndex,
                                 int endColumnIndex) throws LoggableException {
        if (blockIndex < 0) throw new LoggableException("Unacceptable the block index");

        if ((startRowIndex < 0)
                || (startRowIndex > endRowIndex)
                || (startRowIndex > getRowCount()))
            throw new LoggableException("Unacceptable the start row index");

        if ((endRowIndex < 0)
                || (endRowIndex < startRowIndex)
                || (endRowIndex > getRowCount()))
            throw new LoggableException("Unacceptable the end row index");

        if ((startColumnIndex < 0)
                || (startColumnIndex > endColumnIndex)
                || (startColumnIndex > getColumnCount()))
            throw new LoggableException("Unacceptable the start column index");

        if ((endColumnIndex < 0)
                || (endColumnIndex < startColumnIndex)
                || (endColumnIndex > getColumnCount()))
            throw new LoggableException("Unacceptable the end column index");
    }
}
