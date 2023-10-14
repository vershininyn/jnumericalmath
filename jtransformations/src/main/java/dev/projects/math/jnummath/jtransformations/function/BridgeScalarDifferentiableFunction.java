package dev.projects.math.jnummath.jtransformations.function;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.AbstractScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;

public abstract class BridgeScalarDifferentiableFunction
            extends AbstractScalarDifferentiableFunction {

    @Getter
    private IScalarDifferentiableFunction nestedFunction;

    public BridgeScalarDifferentiableFunction(IScalarDifferentiableFunction nestedFunction)   {
        super(nestedFunction.getInputDimensionSize());

        setNestedFunction(nestedFunction);
    }

    public void setNestedFunction(IScalarDifferentiableFunction nestedFunction)   {
        if (nestedFunction == null) throw new LoggableException("Unacceptable nested function");

        this.nestedFunction = nestedFunction;
    }

    @Override
    public DenseVector getVariables()   {
        return nestedFunction.getVariables();
    }

    @Override
    public void setVariables(DenseVector variables)   {
        nestedFunction.setVariables(variables);

        deactivateGradientCache();
        deactivateOutputCache();
    }
}
