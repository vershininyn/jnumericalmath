package dev.projects.math.transformations.function;

import dev.projects.math.linalgebra.ICloneable;
import dev.projects.utils.exception.LoggableException;

/**
 * @author vyn
 */
public abstract class AbstractFunction<TInputData extends ICloneable<TInputData>, TOutputData> {
    private boolean isOutputCacheActual = false;
    private int varCount = 0;
    private long outputComputeCount = 0;

    private TOutputData cachedOutput = null;
    private TInputData inputData = null;

    protected AbstractFunction(int pVariableCount) throws LoggableException {
        if (pVariableCount <= 0) throw new LoggableException("Unacceptable vars count");

        varCount = pVariableCount;
    }

    public long getOutputComputeCount() {
        return outputComputeCount;
    }

    public void reset() throws LoggableException {
        outputComputeCount = 0;

        deactivateOutputCache();
    }

    public int getVariablesCount() {
        return varCount;
    }

    public TInputData getVariables() throws LoggableException {
        return inputData.deepCopy();
    }

    public void setVariables(final TInputData pVariables) throws LoggableException {
        inputData = pVariables.deepCopy();

        deactivateOutputCache();
    }

    public TOutputData getCachedOutput() {
        return cachedOutput;
    }

    public TOutputData computeOutput(final TInputData pVariables) throws LoggableException {
        setVariables(pVariables);
        return computeOutput();
    }

    public TOutputData computeOutput() throws LoggableException {
        TOutputData output = getCachedOutput();

        if (!isOutputCacheActual()) {
            output = actualComputeOutput();

            incOutputComputeCount();

            setOutputCache(output);
            activateOutputCache();
        }

        return output;
    }

    protected abstract TOutputData actualComputeOutput() throws LoggableException;

    protected boolean isOutputCacheActual() {
        return isOutputCacheActual;
    }

    protected void activateOutputCache() {
        isOutputCacheActual = true;
    }

    protected void deactivateOutputCache() {
        isOutputCacheActual = false;
    }

    protected void setOutputCache(final TOutputData pCachedOutput) {
        cachedOutput = pCachedOutput;
    }

    private void incOutputComputeCount() {
        outputComputeCount += 1;
    }
}
