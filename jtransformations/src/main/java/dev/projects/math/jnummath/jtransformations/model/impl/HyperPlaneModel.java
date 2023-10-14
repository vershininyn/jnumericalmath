package dev.projects.math.jnummath.jtransformations.model.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.model.AbstractScalarDifferentiableModel;
import dev.projects.math.jnummath.jtransformations.model.IScalarDifferentiableModel;
import dev.projects.utils.exception.LoggableException;

public class HyperPlaneModel
        extends AbstractScalarDifferentiableModel
        implements IScalarDifferentiableModel {
    public HyperPlaneModel(int variablesCount, int parametersCount)   {
        super(variablesCount, parametersCount);
    }

    public HyperPlaneModel(DenseVector variables, DenseVector parameters)   {
        super(variables, parameters);

        if (variables.getSize() != parameters.getSize()) throw new LoggableException("Unacceptable variables and parameters size");
    }

    @Override
    public void reset()   {
        super.reset();

        setVariables(DenseVector.getRandomizedVector(getInputDimensionSize(), 10.0));
        setParameters(DenseVector.getRandomizedVector(getParametersCount(), 10.0));
    }

    @Override
    protected Double actualComputeOutput()   {
        DenseVector vars = getVariables(), params = getParameters();

        return vars.getInnerProduct(params);
    }

    @Override
    public String getName() {
        return "(x,c)";
    }

    @Override
    protected DenseVector actualComputeGradient()   {
        DenseVector vars = getVariables(),
                grad = DenseVector.getInstance(getParametersCount(), 0.0);

        for (int index = 0; index < getParametersCount(); index++) {
            grad.setValue(index, vars.getValue(index));
        }

        return grad;
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
        HyperPlaneModel deepCopy = new HyperPlaneModel(getInputDimensionSize(),getParametersCount());

        deepCopy.setVariables(getVariables().deepCopy());
        deepCopy.setParameters(getParameters().deepCopy());

        return deepCopy;
    }
}