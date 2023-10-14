package dev.projects.math.jnummath.jtransformations.function;

import dev.projects.math.jnummath.jkernel.IResetable;

/**
 * @author vyn
 */
public interface IFunction<TInputData, TOutputData>
        extends IResetable<IFunction<TInputData, TOutputData>> {
    long getOutputComputeCount();

    int getInputDimensionSize();

    int getOutputDimensionSize();

    String getName();

    TOutputData getCachedOutput();

    TInputData getVariables();

    IFunction<TInputData, TOutputData> setVariables(final TInputData variables);

    TOutputData computeOutput();

    TOutputData computeOutput(final TInputData variables);
}
