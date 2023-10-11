package dev.projects.jnumericalmath.jlinearalgebra.v2;

import dev.projects.math.linalgebra.entities.IVector;
import dev.projects.math.linalgebra.entities.DenseVectorV2;
import dev.projects.utils.exception.LoggableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemoryDenseVectorV2Tests {
    @Test
    public void multiplyByCoefficientTest() throws LoggableException {
        try (IVector vector = DenseVectorV2.getInstance(new double[]{1.0, 2.0, 3.0, 4.0})) {
            IVector checkedVector = vector.multiplyByCoefficient(0.5);

            Assertions.assertEquals(0.5, checkedVector.getValue(0));
            Assertions.assertEquals(1.0, checkedVector.getValue(1));
            Assertions.assertEquals(1.5, checkedVector.getValue(2));
            Assertions.assertEquals(2.0, checkedVector.getValue(3));
        } catch (Exception e) {
            throw new LoggableException(e);
        }
    }

    @Test
    public void innerProductTest() throws LoggableException {
        try (IVector vector0 = DenseVectorV2.getInstance(new double[]{1.0, 1.0, 1.0, 1.0});
             IVector vector1 = DenseVectorV2.getInstance(new double[]{1.0, 1.0, 1.0, 1.0})) {

            Assertions.assertEquals(4.0, vector0.getInnerProduct(vector1));
        } catch (Exception e) {
            throw new LoggableException(e);
        }
    }

    @Test
    public void innerProductDimensionMistakeTest() throws LoggableException {
        try (IVector vector0 = DenseVectorV2.getInstance(new double[]{1.0, 1.0, 1.0, 1.0, 1.0});
             IVector vector1 = DenseVectorV2.getInstance(new double[]{1.0, 1.0, 1.0, 1.0})) {

            Assertions.assertThrows(Exception.class, () -> {
                vector0.getInnerProduct(vector1);
            });
        } catch (Exception e) {
            throw new LoggableException(e);
        }
    }

    @Test
    public void maxElementTest() throws LoggableException {
        try (IVector vector = DenseVectorV2.getInstance(new double[]{1.0, 2.0, 3.0, 4.0})) {
            Assertions.assertEquals(4.0, vector.getMaxElement());
        } catch (Exception e) {
            throw new LoggableException(e);
        }
    }

    @Test
    public void computeEuclidNormTest() throws LoggableException {
        try (IVector vector = DenseVectorV2.getInstance(new double[]{1.0, 1.0, 1.0, 1.0, 1.0})) {
            Assertions.assertEquals(Math.sqrt(5.0), vector.getEuclidNorm());
        } catch (Exception e) {
            throw new LoggableException(e);
        }
    }

    @Test
    public void shiftByCoefficientTest() throws LoggableException {
        try (IVector vector = DenseVectorV2.getInstance(new double[]{-1.0, -1.0, -1.0, 1.0, 1.0, 1.0})) {
            IVector shiftedVector = vector.shiftByCoefficient(1.0);

            Assertions.assertEquals(-2.0, shiftedVector.getValue(0));
            Assertions.assertEquals(-2.0, shiftedVector.getValue(1));
            Assertions.assertEquals(-2.0, shiftedVector.getValue(2));

            Assertions.assertEquals(2.0, shiftedVector.getValue(3));
            Assertions.assertEquals(2.0, shiftedVector.getValue(4));
            Assertions.assertEquals(2.0, shiftedVector.getValue(5));
        } catch (Exception e) {
            throw new LoggableException(e);
        }
    }
}
