package dev.projects.math.transformations.tabulator;

import dev.projects.math.transformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;

import java.io.OutputStream;

public interface UniversalFunctionsTabulator {
    UniversalFunctionsTabulator setFunction(IScalarFunction scalarFunction) throws LoggableException;

    UniversalFunctionsTabulator setTabulatedGrid(TabulatedGrid tabulatedGrid) throws LoggableException;

    UniversalFunctionsTabulator setOutputStream(OutputStream outputStream) throws LoggableException;

    IScalarFunction getScalarFunction();

    TabulatedGrid getTabuletedGrid();

    OutputStream getOutputStream();

    UniversalFunctionsTabulator performTabulate(OutputStream dataDestination, final char delimeter) throws LoggableException;

    UniversalFunctionsTabulator performTabulate(final char delimeter) throws LoggableException;
}
