package dev.projects.math.optimization.global;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.global.base.SimpleHyperCube;
import dev.projects.utils.exception.LoggableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimpleHyperDivideTests {

    @Test
    public void hyperCubeTest() throws LoggableException {
        SimpleHyperCube cube = SimpleHyperCube.getInstance(1.0, DenseVector.getInstance(2, 0.0));

        Assertions.assertEquals(-0.5, cube.getAlphaPoint().getValue(0));
        Assertions.assertEquals(-0.5, cube.getAlphaPoint().getValue(1));

        Assertions.assertEquals(0.5, cube.getBetaPoint().getValue(0));
        Assertions.assertEquals(0.5, cube.getBetaPoint().getValue(1));
    }

}
