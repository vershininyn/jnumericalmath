package dev.projects.math.jnummath.jtransformations.tabulator.interval;

import dev.projects.math.jnummath.jtransformations.tabulator.interval.impl.SimpleTemplateInterval;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimpleTemplateIntervalTests {

    @ParameterizedTest
    @CsvSource(value = {"", ""}, delimiter = ';')
    public void intervalGeneratedByPointsCountShouldBeCorrect(double minBoundary,
                                                              double maxBoundary,
                                                              int pointsCount) {
        final double[] expectedArray = generateExpectedArrayByPointCount(minBoundary, maxBoundary, pointsCount);

        double[] actualArray = SimpleTemplateInterval
                .getInstance(minBoundary, maxBoundary, pointsCount)
                .generateInterval()
                .getData();

        assertArrayEquals(expectedArray, actualArray);
    }

    @ParameterizedTest
    @CsvSource(value = {"", ""}, delimiter = ';')
    public void intervalGeneratedByStepShouldBeCorrect(double minBoundary,
                                                       double maxBoundary,
                                                       double step) {
        final double[] expectedArray = generateExpectedArrayByStep(minBoundary, maxBoundary, step);

        double[] actualArray = SimpleTemplateInterval
                .getInstance(minBoundary, maxBoundary, step)
                .generateInterval()
                .getData();

        assertArrayEquals(expectedArray, actualArray);
    }

    @ParameterizedTest
    @CsvSource(value = {"10.0D;5.0D;100", "5.0D;10.0D;-1"}, delimiter = ';')
    public void intervalCreationByPointsCountShouldThrowException(double minBoundary,
                                                                  double maxBoundary,
                                                                  int pointsCount) {
        assertThrows(Exception.class, () -> {
            SimpleTemplateInterval.getInstance(minBoundary, maxBoundary, pointsCount);
        });
    }

    @ParameterizedTest
    @CsvSource(value = {"10.0D;5.0D;-10.0D", "5.0D;10.0D;-10.0D"}, delimiter = ';')
    public void intervalCreationByStepShouldThrowException(double minBoundary,
                                                           double maxBoundary,
                                                           double step) {
        assertThrows(Exception.class, () -> {
            SimpleTemplateInterval.getInstance(minBoundary, maxBoundary, step);
        });
    }

    private double[] generateExpectedArrayByPointCount(double minBoundary, double maxBoundary, int pointsCount) {
        final double[] interval = new double[pointsCount];
        final double step = (maxBoundary - minBoundary) / (pointsCount);

        interval[0] = minBoundary;

        for (int vIndex = 1; vIndex < pointsCount; vIndex++) {
            interval[vIndex] = interval[vIndex - 1] + step;
        }

        return interval;
    }

    private double[] generateExpectedArrayByStep(double minBoundary, double maxBoundary, double step) {
        int pointsCount = Math.round((float)Math.ceil((maxBoundary - minBoundary)/(step)));

        return generateExpectedArrayByPointCount(minBoundary, maxBoundary, pointsCount);
    }
}
