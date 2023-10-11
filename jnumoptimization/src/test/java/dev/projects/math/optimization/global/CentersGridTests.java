package dev.projects.math.optimization.global;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.global.base.CentersGrid;
import dev.projects.math.optimization.unconstrained.global.base.GridExtensionResult;
import dev.projects.utils.exception.LoggableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CentersGridTests {

    @Test
    public void multiDimensionalGridTest() throws LoggableException {
        CentersGrid grid = new CentersGrid();

        GridExtensionResult result = grid.extendGridToAnotherDimensions(2,2,-1.0,1.0);

        Assertions.assertEquals(0.25, result.getEdgeLength());

        List<DenseVector> centersList = result.getCentersList();

        Assertions.assertEquals(-0.25, centersList.get(0).getValue(0));
        Assertions.assertEquals(-0.25, centersList.get(0).getValue(1));

        Assertions.assertEquals(0.25, centersList.get(1).getValue(0));
        Assertions.assertEquals(-0.25, centersList.get(1).getValue(1));

        Assertions.assertEquals(-0.25, centersList.get(2).getValue(0));
        Assertions.assertEquals(0.25, centersList.get(2).getValue(1));

        Assertions.assertEquals(0.25, centersList.get(3).getValue(0));
        Assertions.assertEquals(0.25, centersList.get(3).getValue(1));
    }

}
