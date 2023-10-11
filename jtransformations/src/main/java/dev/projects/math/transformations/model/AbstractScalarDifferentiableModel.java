package dev.projects.math.transformations.model;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.utils.exception.LoggableException;

/**
 * @author vyn
 */
public abstract class AbstractScalarDifferentiableModel extends AbstractScalarModel {
    private boolean isGradientCacheActual = false;
    private long gradientComputeCount = 0;

    private DenseVector cachedGrad = null;

    public AbstractScalarDifferentiableModel(int variablesCount,
                                             int parametersCount) throws LoggableException {
        super(variablesCount, parametersCount);
    }

    public AbstractScalarDifferentiableModel(DenseVector variables, DenseVector parameters) throws LoggableException {
        super(variables, parameters);
    }

    public long getGradientComputeCount() {
        return gradientComputeCount;
    }

    @Override
    public void reset() throws LoggableException {
        super.reset();
        deactivateGradientCache();

        gradientComputeCount = 0;
    }

    public DenseVector getCachedGradient() {
        return cachedGrad;
    }

    public DenseVector computeGradient(DenseVector variables) throws LoggableException {
        setVariables(variables);
        deactivateGradientCache();

        return computeGradient();
    }

    public DenseVector computeGradient(DenseVector variables,
                                       DenseVector parameters) throws LoggableException {
        setParameters(parameters);
        deactivateGradientCache();

        return computeGradient(variables);
    }

    public DenseVector computeGradient() throws LoggableException {
        DenseVector grad = getCachedGradient();

        if (!isGradientCacheActual()) {
            grad = actualComputeGradient();

            incGradComputeCount();

            setGradientCache(grad);
            activateGradientCache();
        }

        return grad;
    }

    protected abstract DenseVector actualComputeGradient() throws LoggableException;

    protected boolean isGradientCacheActual() {
        return isGradientCacheActual;
    }

    protected void activateGradientCache() {
        isGradientCacheActual = true;
    }

    protected void deactivateGradientCache() {
        isGradientCacheActual = false;
    }

    private void incGradComputeCount() {
        gradientComputeCount += 1;
    }

    private void setGradientCache(DenseVector cachedGrad) {
        this.cachedGrad = cachedGrad.deepCopy();
    }
}
