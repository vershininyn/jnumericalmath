package dev.projects.math.transformations.function;

import dev.projects.math.linalgebra.IResetable;
import dev.projects.utils.exception.LoggableException;

/**
 * @author vyn
 */
public interface IFunction<TInputData, TOutputData> extends IResetable {
    long getOutputComputeCount();

    int getVariablesCount();

    String getName();

    TOutputData getCachedOutput();

    TInputData getVariables() throws LoggableException;

    void setVariables(final TInputData pVariables) throws LoggableException;

    TOutputData computeOutput() throws LoggableException;

    TOutputData computeOutput(final TInputData pVariables) throws LoggableException;
}
