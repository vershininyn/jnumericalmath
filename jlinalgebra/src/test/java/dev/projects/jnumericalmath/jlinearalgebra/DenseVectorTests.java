package dev.projects.jnumericalmath.jlinearalgebra;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.utils.exception.LoggableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DenseVectorTests {

    @Test
    public void splitOneAtLeftTest() throws LoggableException {
        DenseVector[] array = DenseVector
                .getInstance(new double[]{0.0,1.0,2.0,3.0})
                .splitAtIndex(0, false);

        Assertions.assertArrayEquals(new double[]{0.0}, array[0].getData());
        Assertions.assertArrayEquals(new double[]{1.0,2.0,3.0}, array[1].getData());
    }

    @Test
    public void splitOneAtRigthTest() throws LoggableException {
        DenseVector[] array = DenseVector
                .getInstance(new double[]{0.0,1.0,2.0,3.0})
                .splitAtIndex(3, false);

        Assertions.assertArrayEquals(new double[]{0.0,1.0,2.0}, array[0].getData());
        Assertions.assertArrayEquals(new double[]{3.0}, array[1].getData());
    }

    @Test
    public void splitPartWithMiddleToLeftTest() throws LoggableException {
        DenseVector[] array = DenseVector
                .getInstance(new double[]{0.0,1.0,2.0,3.0})
                .splitAtIndex(1, true);

        Assertions.assertArrayEquals(new double[]{0.0,1.0}, array[0].getData());
        Assertions.assertArrayEquals(new double[]{2.0,3.0}, array[1].getData());
    }

    @Test
    public void splitPartWithMiddleToRigthTest() throws LoggableException {
        DenseVector[] array = DenseVector
                .getInstance(new double[]{0.0,1.0,2.0,3.0})
                .splitAtIndex(1, false);

        Assertions.assertArrayEquals(new double[]{0.0}, array[0].getData());
        Assertions.assertArrayEquals(new double[]{1.0,2.0,3.0}, array[1].getData());
    }

    @Test
    public void extendArrayTest() throws LoggableException {
        DenseVector vector = DenseVector.getInstance(3, 0.0);

        Assertions.assertArrayEquals(new double[]{0.0,0.0,0.0,1.0,1.0,1.0}, vector.extendByArray(new double[]{1.0,1.0,1.0}).getData());
    }
}
