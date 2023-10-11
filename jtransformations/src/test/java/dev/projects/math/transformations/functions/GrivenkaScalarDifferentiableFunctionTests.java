package dev.projects.math.transformations.functions;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.math.transformations.function.impl.GrivenkaScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class GrivenkaScalarDifferentiableFunctionTests
                extends AbstractScalarDifferentiableFunctionTests{
    @Override
    protected IScalarDifferentiableFunction getFunction() throws LoggableException {
        return new GrivenkaScalarDifferentiableFunction(DenseVector.getInstance(new double[]{0.0}));
    }

    @Override
    protected Double getCorrectOutput() throws LoggableException {
        return 1.0 - Math.cos(0.0);
    }

    @Override
    protected double[] getCorrectGradient() throws LoggableException {
        return new double[]{Math.sin(0.0)};
    }
}
