package dev.projects.math.transformations.model;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.model.impl.HyperPlaneModel;
import dev.projects.utils.exception.LoggableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HyperPlaneModelTests {
    private final HyperPlaneModel model = new HyperPlaneModel(3, 3);

    public HyperPlaneModelTests() throws LoggableException {
    }

    @Test
    public void hyperPlaneOutputTest() throws LoggableException {
        double out0 = model.computeOutput(DenseVector.getInstance(new double[]{1.0, 1.0, 1.0}),
                DenseVector.getInstance(new double[]{1.0, 1.0, 1.0})),
                out1 = model.computeOutput(DenseVector.getInstance(new double[]{2.0, 2.0, 2.0}),
                        DenseVector.getInstance(new double[]{2.0, 2.0, 2.0}));

        Assertions.assertNotEquals(out0, out1);
    }

    @Test
    public void hyperPlaneGradientTest() throws LoggableException {
        DenseVector grad0 = model.computeGradient(DenseVector.getInstance(new double[]{1.0, 1.0, 1.0}), DenseVector.getInstance(new double[]{1.0, 1.0, 1.0})),
                grad1 = model.computeGradient(DenseVector.getInstance(new double[]{2.0, 2.0, 2.0}), DenseVector.getInstance(new double[]{2.0, 2.0, 2.0}));

        Assertions.assertNotEquals(grad0.getValue(0), grad1.getValue(0));
        Assertions.assertNotEquals(grad0.getValue(1), grad1.getValue(1));
        Assertions.assertNotEquals(grad0.getValue(2), grad1.getValue(2));

        Assertions.assertArrayEquals(new double[]{1.0, 1.0, 1.0}, grad0.getData());
        Assertions.assertArrayEquals(new double[]{2.0, 2.0, 2.0}, grad1.getData());
    }
}
