package dev.projects.math.transformations.functions;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.math.transformations.function.impl.MxCScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class MaxCScalarDifferentiableFunctionTests extends AbstractScalarDifferentiableFunctionTests {

    @Override
    protected IScalarDifferentiableFunction getFunction() throws LoggableException {
        return new MxCScalarDifferentiableFunction(DenseVector.getInstance(new double[]{-1.0,2.0,3.0}));
    }

    @Override
    protected Double getCorrectOutput() throws LoggableException {
        return 81.0;
    }

    @Override
    protected double[] getCorrectGradient() throws LoggableException {
        return new double[]{0.0,0.0,27.0};
    }
}
