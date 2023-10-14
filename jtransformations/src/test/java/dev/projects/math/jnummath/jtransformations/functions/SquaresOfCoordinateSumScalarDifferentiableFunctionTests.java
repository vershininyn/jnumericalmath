package dev.projects.math.jnummath.jtransformations.functions;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.SquaresOfCoordinateSumScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class SquaresOfCoordinateSumScalarDifferentiableFunctionTests
                extends AbstractScalarDifferentiableFunctionTests {
    @Override
    protected IScalarDifferentiableFunction getFunction()   {
        return new SquaresOfCoordinateSumScalarDifferentiableFunction(DenseVector.getInstance(new double[]{0.0}));
    }

    @Override
    protected Double getCorrectOutput()   {
        return 0.0;
    }

    @Override
    protected double[] getCorrectGradient()   {
        return new double[]{0.0};
    }
}
