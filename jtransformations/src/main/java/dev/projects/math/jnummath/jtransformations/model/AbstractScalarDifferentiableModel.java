package dev.projects.math.jnummath.jtransformations.model;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.utils.exception.LoggableException;

/**
 * @author vyn
 */
public abstract class AbstractScalarDifferentiableModel extends AbstractScalarModel {
    private boolean isGradientCacheActual = false;
    private long gradientComputeCount = 0;

    private DenseVector cachedGrad = null;

    public AbstractScalarDifferentiableModel(int variablesCount,
                                             int parametersCount)   {
        super(variablesCount, parametersCount);
    }

    public AbstractScalarDifferentiableModel(DenseVector variables, DenseVector parameters)   {
        super(variables, parameters);
    }

    public long getGradientComputeCount() {
        return gradientComputeCount;
    }

    @Override
    public void reset()   {
        super.reset();
        deactivateGradientCache();

        gradientComputeCount = 0;
    }

    public DenseVector getCachedGradient() {
        return cachedGrad;
    }

    public DenseVector computeGradient(DenseVector variables)   {
        setVariables(variables);
        deactivateGradientCache();

        return computeGradient();
    }

    public DenseVector computeGradient(DenseVector variables,
                                       DenseVector parameters)   {
        setParameters(parameters);
        deactivateGradientCache();

        return computeGradient(variables);
    }

    public DenseVector computeGradient()   {
        DenseVector grad = getCachedGradient();

        if (!isGradientCacheActual()) {
            grad = actualComputeGradient();

            incGradComputeCount();

            setGradientCache(grad);
            activateGradientCache();
        }

        return grad;
    }

    protected abstract DenseVector actualComputeGradient()  ;

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
