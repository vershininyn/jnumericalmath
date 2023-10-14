package dev.projects.math.jnummath.jtransformations.function;

import dev.projects.math.jnummath.jkernel.ICloneable;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;
import lombok.Getter;

/**
 * @author vyn
 */
@Getter
public abstract class AbstractFunction<TInputData extends ICloneable<TInputData>, TOutputData> {
    private boolean isOutputCacheActual = false;

    private long varsCount = 0;
    private long outputComputeCount = 0;

    private TOutputData cachedOutput = null;
    private TInputData inputData = null;

    protected AbstractFunction(long variableCount)   {
        checkVariablesCount(variableCount);

        varsCount = variableCount;
    }

    public void reset()   {
        outputComputeCount = 0;

        deactivateOutputCache();
    }

    public TInputData getVariables()   {
        return getInputData().deepCopy();
    }

    public void setVariables(final TInputData variables)   {
        checkInputData(variables);

        inputData = variables.deepCopy();

        deactivateOutputCache();
    }

    public TOutputData computeOutput(final TInputData variables)   {
        setVariables(variables);

        return computeOutput();
    }

    public TOutputData computeOutput()   {
        TOutputData output = getCachedOutput();

        if (!isOutputCacheActual()) {
            output = actualComputeOutput();

            incOutputComputeCount();

            setOutputCache(output);
            activateOutputCache();
        }

        return output;
    }

    protected abstract TOutputData actualComputeOutput()  ;

    protected boolean isOutputCacheActual() {
        return isOutputCacheActual;
    }

    protected void activateOutputCache() {
        isOutputCacheActual = true;
    }

    protected void deactivateOutputCache() {
        isOutputCacheActual = false;
    }

    protected void setOutputCache(final TOutputData cachedOutput)   {
        checkOutputData(cachedOutput);

        this.cachedOutput = cachedOutput;
    }

    protected void incOutputComputeCount() {
        outputComputeCount = outputComputeCount + 1;
    }

    private void checkVariablesCount(long variableCount)   {
        if (variableCount <= 0) {
            throw new LoggableException("Unacceptable variables count: it's can't be <= 0");
        }
    }

    private void checkInputData(final TInputData variables)   {
        if (variables == null) {
            throw new LoggableException("Unacceptable variables: it's can't be NULL");
        }
    }

    private void checkOutputData(final TOutputData outputData)   {
        if (outputData == null) {
            throw new LoggableException("Unacceptable output data: it's can't be NULL");
        }
    }
}
