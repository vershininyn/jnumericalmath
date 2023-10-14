package dev.projects.math.jnummath.jtransformations.tabulator.interval;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;

public interface ITemplateInterval {
    ITemplateInterval setBoundaries(final double minBoundary,
                                    final double maxBoundary);

    double getMinBoundary();

    double getMaxBoundary();

    ITemplateInterval setBetweenMinAndMaxPointsCount(final int pointsCount);

    int getBetweenMinAndMaxPointsCount();

    DenseVector generateInterval();

    DenseVector generateInterval(final double minBoundary,
                                 final double maxBoundary,
                                 final double step);

    DenseVector generateInterval(final double minBoundary,
                                 final double maxBoundary,
                                 final int pointsCount);
}
