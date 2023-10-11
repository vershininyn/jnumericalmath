package dev.projects.math.transformations.tabulator;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.utils.exception.LoggableException;

import java.util.Set;

public interface TabulatedGrid {
    TabulatedGrid setBoundaries(double minBoundary, double maxBoundary) throws LoggableException;

    TabulatedGrid setBetweenMinAndMaxPointsCount(long pointsCount) throws LoggableException;

    TabulatedGrid setDimension(long dimension) throws LoggableException;

    double getMinBoundary();

    double getMaxBoundary();

    long getBetweenMinAndMaxPointsCount();

    Set<DenseVector> getHyperCubeGrid();

    TabulatedGrid update() throws LoggableException;

    TabulatedGrid update(double minBoundary, double maxBoundary, long pointsCount, long dimension) throws LoggableException;
}
