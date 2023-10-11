package dev.projects.math.transformations.functions;

import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class AbstractScalarDifferentiableFunctionTests {

    @Test
    public void functionTest() throws LoggableException {
        IScalarDifferentiableFunction fun = getFunction();

        Assertions.assertEquals(getCorrectOutput(), fun.computeOutput());
        Assertions.assertArrayEquals(getCorrectGradient(), fun.computeGradient().getData());
    }

    protected abstract IScalarDifferentiableFunction getFunction() throws LoggableException;

    protected abstract Double getCorrectOutput() throws LoggableException;

    protected abstract double[] getCorrectGradient() throws LoggableException;
}
