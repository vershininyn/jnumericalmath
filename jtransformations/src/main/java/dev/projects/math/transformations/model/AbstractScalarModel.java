package dev.projects.math.transformations.model;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.utils.exception.LoggableException;

/**
 * @author vyn
 */
public abstract class AbstractScalarModel extends AbstractModel<DenseVector, Double, DenseVector> {
    protected AbstractScalarModel(DenseVector variables,
                                  DenseVector parameters) throws LoggableException {
        this(variables.getSize(), parameters.getSize());

        setVariables(variables);
        setParameters(parameters);
    }

    protected AbstractScalarModel(int variablesCount,
                                  int parametersCount) throws LoggableException {
        super(variablesCount, parametersCount);

        setVariables(DenseVector.getInstance(variablesCount, 0.0));
        setParameters(DenseVector.getInstance(parametersCount, 0.0));
    }

    @Override
    public void setParameters(DenseVector parameters) throws LoggableException {
        if ((parameters == null) || (parameters.getSize() != getParametersCount())) {
            throw new LoggableException("Unacceptable model parameters");
        }

        super.setParameters(parameters);
    }

    @Override
    public void reset() throws LoggableException {
        super.reset();

        setParameters(DenseVector.getInstance(getParametersCount(), 0.0));
    }
}
