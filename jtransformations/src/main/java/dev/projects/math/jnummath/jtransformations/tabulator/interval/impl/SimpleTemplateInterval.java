package dev.projects.math.jnummath.jtransformations.tabulator.interval.impl;

import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.tabulator.interval.ITemplateInterval;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@Builder
public class SimpleTemplateInterval implements ITemplateInterval {
    private double minBoundary = 0.0D;
    private double maxBoundary = 0.0D;
    private int betweenMinAndMaxPointsCount = 0;

    public static ITemplateInterval getInstance(final double minBoundary,
                                                final double maxBoundary,
                                                final double step) {
        checkBoundaries(minBoundary, maxBoundary);
        checkStep(step);

        return new SimpleTemplateInterval(minBoundary,
                maxBoundary,
                computePointsCountByStep(minBoundary, maxBoundary, step));
    }

    public static ITemplateInterval getInstance(final double minBoundary,
                                                final double maxBoundary,
                                                final int pointsCount) {
        checkBoundaries(minBoundary, maxBoundary);
        checkPointsCount(pointsCount);

        return new SimpleTemplateInterval(minBoundary, maxBoundary, pointsCount);
    }

    private static void checkBoundaries(final double minBoundary,
                                        final double maxBoundary) {
        if ((minBoundary == maxBoundary)
                || (minBoundary > maxBoundary)
                || (maxBoundary < minBoundary)) {

            throw new LoggableException("Unacceptable boundaries: (min != max) && (min < max) && (max > min)");
        }
    }

    private static void checkPointsCount(final int pointsCount) {
        if (pointsCount <= 0L) {
            throw new LoggableException("Unacceptable points count: it's can't be <= 0L");
        }
    }

    private static void checkStep(final double step) {
        if (step <= 0.0D) {
            throw new LoggableException("Unacceptable step: it's can't be <= 0.0D");
        }
    }

    private static void checkStep(final double minBoundary,
                                  final double maxBoundary,
                                  final double step) {
        checkBoundaries(minBoundary, maxBoundary);
        checkStep(step);

        if (minBoundary + step >= maxBoundary) {
            throw new LoggableException("Unacceptable step: it's value > 0.0D AND value <= (MAX_B - MIN_A)");
        }
    }

    private static int computePointsCountByStep(final double minBoundary,
                                                final double maxBoundary,
                                                final double step) {
        checkBoundaries(minBoundary, maxBoundary);
        checkStep(step);

        double pointsCount = (maxBoundary - minBoundary) / (step);

        return Math.round((float) Math.ceil(pointsCount));
    }

    private static double computeStepByPointsCount(final double minBoundary,
                                                   final double maxBoundary,
                                                   final int pointsCount) {
        checkBoundaries(minBoundary, maxBoundary);
        checkPointsCount(pointsCount);

        return (maxBoundary - minBoundary) / (pointsCount);
    }

    private SimpleTemplateInterval(double minBoundary, double maxBoundary, int pointsCount) {
        setBoundaries(minBoundary, maxBoundary);
        setBetweenMinAndMaxPointsCount(pointsCount);
    }

    @Override
    public ITemplateInterval setBoundaries(double minBoundary, double maxBoundary) {
        checkBoundaries(minBoundary, maxBoundary);

        this.minBoundary = minBoundary;
        this.maxBoundary = maxBoundary;

        return this;
    }

    @Override
    public ITemplateInterval setBetweenMinAndMaxPointsCount(int pointsCount) {
        checkPointsCount(pointsCount);

        this.betweenMinAndMaxPointsCount = pointsCount;

        return this;
    }

    public DenseVector generateInterval() {
        double minBoundary = getMinBoundary();
        double maxBoundary = getMaxBoundary();
        int pointsCount = getBetweenMinAndMaxPointsCount();

        checkBoundaries(minBoundary, maxBoundary);
        checkPointsCount(pointsCount);

        double step = (maxBoundary - minBoundary) / (pointsCount);

        final double[] interval = new double[pointsCount];

        interval[0] = minBoundary;

        for (int vIndex = 1; vIndex < pointsCount; vIndex++) {
            interval[vIndex] = interval[vIndex - 1] + step;
        }

        return DenseVector.getInstance(interval);
    }

    public DenseVector generateInterval(final double minBoundary,
                                        final double maxBoundary,
                                        final int pointsCount) {
        setBoundaries(minBoundary, maxBoundary);
        setBetweenMinAndMaxPointsCount(pointsCount);

        return generateInterval();
    }

    public DenseVector generateInterval(final double minBoundary,
                                        final double maxBoundary,
                                        final double step) {
        checkStep(minBoundary, maxBoundary, step);
        setBoundaries(minBoundary, maxBoundary);

        return generateInterval(minBoundary,
                maxBoundary,
                computePointsCountByStep(minBoundary, maxBoundary, step));
    }
}
