package dev.projects.math.jnummath.jtransformations.functions;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.MaxQScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class MaxQScalarDefferentiableFunctionTests extends AbstractScalarDifferentiableFunctionTests {
    @Override
    protected IScalarDifferentiableFunction getFunction()   {
        return new MaxQScalarDifferentiableFunction(DenseVector.getInstance(new double[]{-1.0,2.0,3.0}));
    }

    @Override
    protected Double getCorrectOutput()   {
        return 9.0;
    }

    @Override
    protected double[] getCorrectGradient()   {
        return new double[]{0.0,0.0,6.0};
    }
}
