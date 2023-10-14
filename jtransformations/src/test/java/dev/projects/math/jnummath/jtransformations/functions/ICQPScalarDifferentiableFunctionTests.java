package dev.projects.math.jnummath.jtransformations.functions;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.ICQPScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class ICQPScalarDifferentiableFunctionTests
        extends AbstractScalarDifferentiableFunctionTests {
    @Override
    protected IScalarDifferentiableFunction getFunction()   {
        return new ICQPScalarDifferentiableFunction(DenseVector.getInstance(new double[]{-1.0,2.0}));
    }

    @Override
    protected Double getCorrectOutput()   {
        return 9001.0;
    }

    @Override
    protected double[] getCorrectGradient()   {
        return new double[]{-6000.0,6002.0};
    }
}
