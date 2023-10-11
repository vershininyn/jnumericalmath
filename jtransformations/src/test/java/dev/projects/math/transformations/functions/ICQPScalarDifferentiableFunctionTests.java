package dev.projects.math.transformations.functions;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.math.transformations.function.impl.ICQPScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class ICQPScalarDifferentiableFunctionTests
        extends AbstractScalarDifferentiableFunctionTests {
    @Override
    protected IScalarDifferentiableFunction getFunction() throws LoggableException {
        return new ICQPScalarDifferentiableFunction(DenseVector.getInstance(new double[]{-1.0,2.0}));
    }

    @Override
    protected Double getCorrectOutput() throws LoggableException {
        return 9001.0;
    }

    @Override
    protected double[] getCorrectGradient() throws LoggableException {
        return new double[]{-6000.0,6002.0};
    }
}
