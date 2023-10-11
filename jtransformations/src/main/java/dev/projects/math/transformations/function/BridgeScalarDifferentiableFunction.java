package dev.projects.math.transformations.function;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.differentiale.AbstractScalarDifferentiableFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;

public abstract class BridgeScalarDifferentiableFunction
            extends AbstractScalarDifferentiableFunction {

    @Getter
    private IScalarDifferentiableFunction nestedFunction;

    public BridgeScalarDifferentiableFunction(IScalarDifferentiableFunction nestedFunction) throws LoggableException {
        super(nestedFunction.getVariablesCount());

        setNestedFunction(nestedFunction);
    }

    public void setNestedFunction(IScalarDifferentiableFunction nestedFunction) throws LoggableException {
        if (nestedFunction == null) throw new LoggableException("Unacceptable nested function");

        this.nestedFunction = nestedFunction;
    }

    @Override
    public DenseVector getVariables() throws LoggableException {
        return nestedFunction.getVariables();
    }

    @Override
    public void setVariables(DenseVector variables) throws LoggableException {
        nestedFunction.setVariables(variables);

        deactivateGradientCache();
        deactivateOutputCache();
    }
}
