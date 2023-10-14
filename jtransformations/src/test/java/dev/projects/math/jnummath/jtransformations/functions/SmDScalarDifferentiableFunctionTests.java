package dev.projects.math.jnummath.jtransformations.functions;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.SmDScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class SmDScalarDifferentiableFunctionTests
        extends AbstractScalarDifferentiableFunctionTests {
    @Override
    protected IScalarDifferentiableFunction getFunction()   {
        return new SmDScalarDifferentiableFunction(DenseVector.getInstance(new double[]{-1.0,2.0,3.0}));
    }

    @Override
    protected Double getCorrectOutput()   {
        return 762.0;
    }

    @Override
    protected double[] getCorrectGradient()   {
        return new double[]{-1.0,32.0,729.0};
    }
}
