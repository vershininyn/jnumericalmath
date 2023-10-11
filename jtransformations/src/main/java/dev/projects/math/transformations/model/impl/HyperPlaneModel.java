package dev.projects.math.transformations.model.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.model.AbstractScalarDifferentiableModel;
import dev.projects.math.transformations.model.IScalarDifferentiableModel;
import dev.projects.utils.exception.LoggableException;

public class HyperPlaneModel
        extends AbstractScalarDifferentiableModel
        implements IScalarDifferentiableModel {
    public HyperPlaneModel(int variablesCount, int parametersCount) throws LoggableException {
        super(variablesCount, parametersCount);
    }

    public HyperPlaneModel(DenseVector variables, DenseVector parameters) throws LoggableException {
        super(variables, parameters);

        if (variables.getSize() != parameters.getSize()) throw new LoggableException("Unacceptable variables and parameters size");
    }

    @Override
    public void reset() throws LoggableException {
        super.reset();

        setVariables(DenseVector.getRandomizedVector(getVariablesCount(), 10.0));
        setParameters(DenseVector.getRandomizedVector(getParametersCount(), 10.0));
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
        DenseVector vars = getVariables(), params = getParameters();

        return vars.getInnerProduct(params);
    }

    @Override
    public String getName() {
        return "(x,c)";
    }

    @Override
    protected DenseVector actualComputeGradient() throws LoggableException {
        DenseVector vars = getVariables(),
                grad = DenseVector.getInstance(getParametersCount(), 0.0);

        for (int index = 0; index < getParametersCount(); index++) {
            grad.setValue(index, vars.getValue(index));
        }

        return grad;
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        HyperPlaneModel deepCopy = new HyperPlaneModel(getVariablesCount(),getParametersCount());

        deepCopy.setVariables(getVariables().deepCopy());
        deepCopy.setParameters(getParameters().deepCopy());

        return deepCopy;
    }
}