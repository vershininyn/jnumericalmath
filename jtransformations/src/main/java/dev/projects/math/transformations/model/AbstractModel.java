package dev.projects.math.transformations.model;

import dev.projects.math.linalgebra.ICloneable;
import dev.projects.math.transformations.function.AbstractFunction;
import dev.projects.utils.exception.LoggableException;

/**
 * @author vyn
 */
public abstract class AbstractModel<TInputData extends ICloneable<TInputData>, TOutputData, TParameters extends ICloneable<TParameters>>
        extends AbstractFunction<TInputData, TOutputData> {
    private int parametersCount = 0;

    private TParameters parameters = null;

    protected AbstractModel(int variableCount, int parametersCount) throws LoggableException {
        super(variableCount);

        this.parametersCount = parametersCount;
    }

    public int getParametersCount() {
        return parametersCount;
    }

    public TParameters getParameters() throws LoggableException {
        return parameters.deepCopy();
    }

    public void setParameters(final TParameters parameters) throws LoggableException {
        if (parameters == null) {
            throw new LoggableException("Unacceptable parameters object");
        }

        this.parameters = parameters.deepCopy();
        deactivateOutputCache();
    }

    public TOutputData computeOutput(final TInputData variables, final TParameters parameters) throws LoggableException {
        setParameters(parameters);

        return computeOutput(variables);
    }
}
