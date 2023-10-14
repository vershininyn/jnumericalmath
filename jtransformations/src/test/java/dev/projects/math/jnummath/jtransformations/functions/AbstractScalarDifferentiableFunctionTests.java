package dev.projects.math.jnummath.jtransformations.functions;

import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class AbstractScalarDifferentiableFunctionTests {

    @Test
    public void functionTest()   {
        IScalarDifferentiableFunction fun = getFunction();

        Assertions.assertEquals(getCorrectOutput(), fun.computeOutput());
        Assertions.assertArrayEquals(getCorrectGradient(), fun.computeGradient().getData());
    }

    protected abstract IScalarDifferentiableFunction getFunction()  ;

    protected abstract Double getCorrectOutput()  ;

    protected abstract double[] getCorrectGradient()  ;
}
