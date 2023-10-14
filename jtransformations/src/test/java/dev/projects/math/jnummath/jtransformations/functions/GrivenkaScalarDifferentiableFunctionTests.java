package dev.projects.math.jnummath.jtransformations.functions;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.GrivenkaScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class GrivenkaScalarDifferentiableFunctionTests
                extends AbstractScalarDifferentiableFunctionTests{
    @Override
    protected IScalarDifferentiableFunction getFunction()   {
        return new GrivenkaScalarDifferentiableFunction(DenseVector.getInstance(new double[]{0.0}));
    }

    @Override
    protected Double getCorrectOutput()   {
        return 1.0 - Math.cos(0.0);
    }

    @Override
    protected double[] getCorrectGradient()   {
        return new double[]{Math.sin(0.0)};
    }
}
