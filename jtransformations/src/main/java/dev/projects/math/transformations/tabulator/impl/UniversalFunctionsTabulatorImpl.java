package dev.projects.math.transformations.tabulator.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
import dev.projects.math.transformations.tabulator.TabulatedGrid;
import dev.projects.math.transformations.tabulator.UniversalFunctionsTabulator;
import dev.projects.utils.exception.LoggableException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

@EqualsAndHashCode
@Getter
public class UniversalFunctionsTabulatorImpl implements UniversalFunctionsTabulator {
    private IScalarFunction scalarFunction;
    private TabulatedGrid tabuletedGrid;
    private OutputStream outputStream;

    public static UniversalFunctionsTabulator getInstance(TabulatedGrid tabuletedGrid,
                                                          IScalarFunction scalarFunction,
                                                          OutputStream outputStream) throws LoggableException {
        checkGrid(tabuletedGrid);
        checkFunction(scalarFunction);
        checkOutputStream(outputStream);

        return new UniversalFunctionsTabulatorImpl(tabuletedGrid, scalarFunction, outputStream);
    }

    public static UniversalFunctionsTabulator getInstance(TabulatedGrid tabuletedGrid,
                                                          IScalarFunction scalarFunction) throws LoggableException {
        checkGrid(tabuletedGrid);
        checkFunction(scalarFunction);

        return new UniversalFunctionsTabulatorImpl(tabuletedGrid, scalarFunction);
    }

    public static void checkFunction(IScalarFunction scalarFunction) throws LoggableException {
        if (scalarFunction == null) {
            throw new LoggableException("Unacceptable scalarFunction: it's can't be NULL");
        }
    }

    public static void checkGrid(TabulatedGrid grid) throws LoggableException {
        if (grid == null) {
            throw new LoggableException("Unacceptable grid: it's can't be NULL");
        }
    }

    public static void checkOutputStream(OutputStream outputStream) throws LoggableException {
        if (outputStream == null) {
            throw new LoggableException("Unacceptable output stream: (it must specified) AND (it's can't be NULL)");
        }
    }

    private UniversalFunctionsTabulatorImpl(TabulatedGrid tabulatedGrid,
                                            IScalarFunction scalarFunction,
                                            OutputStream outputStream) throws LoggableException {
        setFunction(scalarFunction);
        setTabulatedGrid(tabulatedGrid);
        setOutputStream(outputStream);
    }

    private UniversalFunctionsTabulatorImpl(TabulatedGrid tabulatedGrid,
                                            IScalarFunction scalarFunction) throws LoggableException {
        setFunction(scalarFunction);
        setTabulatedGrid(tabulatedGrid);
    }

    @Override
    public UniversalFunctionsTabulator setFunction(IScalarFunction scalarFunction) throws LoggableException {
        checkFunction(scalarFunction);

        this.scalarFunction = scalarFunction;

        return this;
    }


    @Override
    public UniversalFunctionsTabulator setTabulatedGrid(TabulatedGrid tabulatedGrid) throws LoggableException {
        checkGrid(tabulatedGrid);

        this.tabuletedGrid = tabulatedGrid;

        return this;
    }

    @Override
    public UniversalFunctionsTabulator setOutputStream(OutputStream outputStream) throws LoggableException {
        checkOutputStream(outputStream);

        this.outputStream = outputStream;

        return this;
    }

    @Override
    public UniversalFunctionsTabulator performTabulate(OutputStream dataDestination, final char delimeter) throws LoggableException {
        setOutputStream(dataDestination);

        return performTabulate(delimeter);
    }

    @Override
    public UniversalFunctionsTabulator performTabulate(final char delimeter) throws LoggableException, RuntimeException {
        checkFunction(getScalarFunction());
        checkGrid(getTabuletedGrid());
        checkOutputStream(getOutputStream());

        final IScalarFunction scalarFunction = getScalarFunction();
        final PrintWriter printWriter = new PrintWriter(getOutputStream());

        getTabuletedGrid()
                .getHyperCubeGrid()
                .stream()
                .map(point -> {
                    final Map.Entry<DenseVector, Double> entry;

                    try {
                        entry = Map.entry(point, scalarFunction.computeOutput(point));
                    } catch (LoggableException ex) {
                        throw new RuntimeException(ex);
                    }

                    return entry;
                })
                .map(entry -> {
                    DenseVector point = entry.getKey();
                    Double value = entry.getValue();

                    StringJoiner joiner = new StringJoiner(String.valueOf(delimeter), "", "");

                    int dimVariablesCount = point.getSize();

                    for(int dimIndex = 0; dimIndex < dimVariablesCount + 1; dimIndex++) {
                        joiner.add(String.valueOf(point.getValue(dimIndex)));
                    }

                    joiner.add(String.valueOf(value));

                    return joiner.toString();
                })
                .forEach(line -> {
                    printWriter.println(line);
                });

        return this;
    }
}
