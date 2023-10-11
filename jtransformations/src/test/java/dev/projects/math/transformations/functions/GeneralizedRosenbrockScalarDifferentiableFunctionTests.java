package dev.projects.math.transformations.functions;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.math.transformations.function.impl.GeneralizedRosenbrockScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class GeneralizedRosenbrockScalarDifferentiableFunctionTests
                extends AbstractScalarDifferentiableFunctionTests {
    @Override
    protected IScalarDifferentiableFunction getFunction() throws LoggableException {
        return new GeneralizedRosenbrockScalarDifferentiableFunction(DenseVector.getInstance(new double[]{-1.0,2.0}));
    }

    @Override
    protected Double getCorrectOutput() throws LoggableException {
        return 104.0;
    }

    @Override
    protected double[] getCorrectGradient() throws LoggableException {
        return new double[]{396.0,200.0};
    }
}
