package dev.projects.math.jnummath.jtransformations.functions;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.MaxLScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class MaxLScalarDifferentiableFunctionTests
        extends AbstractScalarDifferentiableFunctionTests {
    @Override
    protected IScalarDifferentiableFunction getFunction()   {
        return new MaxLScalarDifferentiableFunction(DenseVector.getInstance(new double[]{-1.0,2.0,3.0}));
    }

    @Override
    protected Double getCorrectOutput()   {
        return 3.0;
    }

    @Override
    protected double[] getCorrectGradient()   {
        return new double[]{0.0,0.0,1.0};
    }
}
