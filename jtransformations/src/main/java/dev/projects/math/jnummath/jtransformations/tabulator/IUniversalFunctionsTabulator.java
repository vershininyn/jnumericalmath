package dev.projects.math.jnummath.jtransformations.tabulator;

import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;

import java.io.OutputStream;

public interface IUniversalFunctionsTabulator {
    IUniversalFunctionsTabulator setFunction(IScalarFunction scalarFunction)  ;

    IUniversalFunctionsTabulator setTabulatedGrid(IFunctionallyConditionedGrid tabulatedGrid)  ;

    IUniversalFunctionsTabulator setOutputStream(OutputStream outputStream)  ;

    IScalarFunction getScalarFunction();

    IFunctionallyConditionedGrid getTabulatedGrid();

    OutputStream getOutputStream();

    IUniversalFunctionsTabulator performTabulate(OutputStream dataDestination, final char delimeter)  ;

    IUniversalFunctionsTabulator performTabulate(final char delimeter)  ;
}
