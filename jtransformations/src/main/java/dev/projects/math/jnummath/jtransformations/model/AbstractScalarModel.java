package dev.projects.math.jnummath.jtransformations.model;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.utils.exception.LoggableException;

/**
 * @author vyn
 */
public abstract class AbstractScalarModel extends AbstractModel<DenseVector, Double, DenseVector> {
    protected AbstractScalarModel(DenseVector variables,
                                  DenseVector parameters)   {
        this(variables.getSize(), parameters.getSize());

        setVariables(variables);
        setParameters(parameters);
    }

    protected AbstractScalarModel(int variablesCount,
                                  int parametersCount)   {
        super(variablesCount, parametersCount);

        setVariables(DenseVector.getInstance(variablesCount, 0.0));
        setParameters(DenseVector.getInstance(parametersCount, 0.0));
    }

    @Override
    public void setParameters(DenseVector parameters)   {
        if ((parameters == null) || (parameters.getSize() != getParametersCount())) {
            throw new LoggableException("Unacceptable model parameters");
        }

        super.setParameters(parameters);
    }

    @Override
    public void reset()   {
        super.reset();

        setParameters(DenseVector.getInstance(getParametersCount(), 0.0));
    }
}
