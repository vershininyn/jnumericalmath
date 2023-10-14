package dev.projects.math.jnummath.jtransformations.functions;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.GeneralizedRosenbrockScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class GeneralizedRosenbrockScalarDifferentiableFunctionTests
                extends AbstractScalarDifferentiableFunctionTests {
    @Override
    protected IScalarDifferentiableFunction getFunction()   {
        return new GeneralizedRosenbrockScalarDifferentiableFunction(DenseVector.getInstance(new double[]{-1.0,2.0}));
    }

    @Override
    protected Double getCorrectOutput()   {
        return 104.0;
    }

    @Override
    protected double[] getCorrectGradient()   {
        return new double[]{396.0,200.0};
    }
}
