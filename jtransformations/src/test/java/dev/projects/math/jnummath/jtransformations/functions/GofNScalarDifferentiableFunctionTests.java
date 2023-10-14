package dev.projects.math.jnummath.jtransformations.functions;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.GofNScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class GofNScalarDifferentiableFunctionTests extends AbstractScalarDifferentiableFunctionTests{
    @Override
    protected IScalarDifferentiableFunction getFunction()   {
        return new GofNScalarDifferentiableFunction(DenseVector.getInstance(new double[]{-1.0,2.0,3.0}));
    }

    @Override
    protected Double getCorrectOutput()   {
        return 5.0;
    }

    @Override
    protected double[] getCorrectGradient()   {
        return new double[]{-1.0,-1.0,2.0};
    }
}
