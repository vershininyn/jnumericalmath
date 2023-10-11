package dev.projects.math.transformations.model;

import dev.projects.math.linalgebra.ICloneable;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.utils.exception.LoggableException;

/**
 * @author vyn
 *
 */
public interface IModel<TInputData,TOutputData,TParameters extends ICloneable<TParameters>> extends IFunction<TInputData,TOutputData>
{
    int getParametersCount();

    TParameters getParameters() throws LoggableException;

    void setParameters(final TParameters pParameters) throws LoggableException;

    TOutputData computeOutput(final TInputData pVariables, final TParameters pParameters) throws LoggableException;
}
