package dev.projects.math.jnummath.jtransformations.functions;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.MxCScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class MaxCScalarDifferentiableFunctionTests extends AbstractScalarDifferentiableFunctionTests {

    @Override
    protected IScalarDifferentiableFunction getFunction()   {
        return new MxCScalarDifferentiableFunction(DenseVector.getInstance(new double[]{-1.0,2.0,3.0}));
    }

    @Override
    protected Double getCorrectOutput()   {
        return 81.0;
    }

    @Override
    protected double[] getCorrectGradient()   {
        return new double[]{0.0,0.0,27.0};
    }
}
