package dev.projects.math.jnummath.jtransformations.tabulator.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;
import dev.projects.math.jnummath.jtransformations.tabulator.IFunctionallyConditionedGrid;
import dev.projects.math.jnummath.jtransformations.tabulator.IUniversalFunctionsTabulator;
import dev.projects.utils.exception.LoggableException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

@EqualsAndHashCode
@Getter
public class UniversalFunctionsTabulator implements IUniversalFunctionsTabulator {
    private IScalarFunction scalarFunction;
    private IFunctionallyConditionedGrid tabulatedGrid;
    private OutputStream outputStream;

    public static IUniversalFunctionsTabulator getInstance(IFunctionallyConditionedGrid tabuletedGrid,
                                                           IScalarFunction scalarFunction,
                                                           OutputStream outputStream)   {
        checkGrid(tabuletedGrid);
        checkFunction(scalarFunction);
        checkOutputStream(outputStream);

        return new UniversalFunctionsTabulator(tabuletedGrid, scalarFunction, outputStream);
    }

    public static IUniversalFunctionsTabulator getInstance(IFunctionallyConditionedGrid tabuletedGrid,
                                                           IScalarFunction scalarFunction)   {
        checkGrid(tabuletedGrid);
        checkFunction(scalarFunction);

        return new UniversalFunctionsTabulator(tabuletedGrid, scalarFunction);
    }

    public static void checkFunction(IScalarFunction scalarFunction)   {
        if (scalarFunction == null) {
            throw new LoggableException("Unacceptable scalarFunction: it's can't be NULL");
        }
    }

    public static void checkGrid(IFunctionallyConditionedGrid grid)   {
        if (grid == null) {
            throw new LoggableException("Unacceptable grid: it's can't be NULL");
        }
    }

    public static void checkOutputStream(OutputStream outputStream)   {
        if (outputStream == null) {
            throw new LoggableException("Unacceptable output stream: (it must specified) AND (it's can't be NULL)");
        }
    }

    private UniversalFunctionsTabulator(IFunctionallyConditionedGrid tabulatedGrid,
                                        IScalarFunction scalarFunction,
                                        OutputStream outputStream)   {
        setFunction(scalarFunction);
        setTabulatedGrid(tabulatedGrid);
        setOutputStream(outputStream);
    }

    private UniversalFunctionsTabulator(IFunctionallyConditionedGrid tabulatedGrid,
                                        IScalarFunction scalarFunction)   {
        setFunction(scalarFunction);
        setTabulatedGrid(tabulatedGrid);
    }

    @Override
    public IUniversalFunctionsTabulator setFunction(IScalarFunction scalarFunction)   {
        checkFunction(scalarFunction);

        this.scalarFunction = scalarFunction;

        return this;
    }


    @Override
    public IUniversalFunctionsTabulator setTabulatedGrid(IFunctionallyConditionedGrid tabulatedGrid)   {
        checkGrid(tabulatedGrid);

        this.tabulatedGrid = tabulatedGrid;

        return this;
    }

    @Override
    public IUniversalFunctionsTabulator setOutputStream(OutputStream outputStream)   {
        checkOutputStream(outputStream);

        this.outputStream = outputStream;

        return this;
    }

    @Override
    public IUniversalFunctionsTabulator performTabulate(OutputStream dataDestination, final char delimeter)   {
        setOutputStream(dataDestination);

        return performTabulate(delimeter);
    }

    @Override
    public IUniversalFunctionsTabulator performTabulate(final char delimeter) {
        checkFunction(getScalarFunction());
        checkGrid(getTabulatedGrid());
        checkOutputStream(getOutputStream());

        final IScalarFunction scalarFunction = getScalarFunction();
        final PrintWriter printWriter = new PrintWriter(getOutputStream());

        this.getTabulatedGrid()
                .getGrid()
                .stream()
                .map(point -> Map.entry(point, scalarFunction.computeOutput(point)))
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
