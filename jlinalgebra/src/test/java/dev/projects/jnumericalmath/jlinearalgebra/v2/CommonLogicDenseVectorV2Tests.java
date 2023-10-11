package dev.projects.jnumericalmath.jlinearalgebra.v2;

import dev.projects.math.linalgebra.entities.IVector;
import dev.projects.math.linalgebra.entities.DenseVectorV2;
import dev.projects.utils.exception.LoggableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommonLogicDenseVectorV2Tests {
    @Test
    public void dimensionMistakeTest() throws LoggableException {
        Assertions.assertThrows(LoggableException.class, () -> {
            try (IVector vector = DenseVectorV2.getInstance(new double[]{})) {}
        });
    }

    @Test
    public void indexMistakeTest() throws LoggableException {
        Assertions.assertThrows(LoggableException.class, () -> {
            try (IVector vector = DenseVectorV2.getInstance(new double[]{0.0})){
                Assertions.assertEquals(0.0, vector.getValue(0));
                Assertions.assertEquals(0.0, vector.getValue(1));
            }
        });
    }
}
