package dev.projects.math.transformations.function.scalar.differentiale;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.AbstractScalarFunction;
import dev.projects.utils.exception.LoggableException;

/**
 * @author vyn
 *
 */
public abstract class AbstractScalarDifferentiableFunction extends AbstractScalarFunction {
    private boolean isGradientCacheActual = false;
    private long gradientComputeCount = 0;

    private DenseVector cachedGrad;

    protected AbstractScalarDifferentiableFunction(int variablesCount) throws LoggableException {
        super(variablesCount);

        cachedGrad = DenseVector.getInstance(variablesCount);
    }

    protected AbstractScalarDifferentiableFunction(DenseVector variables) throws LoggableException {
        this(variables.getSize());

        setVariables(variables);
    }

    public long getGradientComputeCount() {
        return gradientComputeCount;
    }

    @Override
    public void reset() throws LoggableException {
        super.reset();

        gradientComputeCount = 0;
        setVariables(DenseVector.getInstance(getVariablesCount(), 0.0));
    }

    public void setVariables(DenseVector variables) throws LoggableException {
        super.setVariables(variables);

        deactivateGradientCache();
    }

    public DenseVector getCachedGradient() {
        return cachedGrad.deepCopy();
    }

    public DenseVector computeGradient(DenseVector variables) throws LoggableException {
        setVariables(variables);

        return computeGradient();
    }

    public DenseVector computeGradient() throws LoggableException {
        DenseVector grad = getCachedGradient();

        if (!isGradientCacheActual) {
            grad = actualComputeGradient();

            incGradComputeCount();

            setGradientCache(grad);
            activateGradCache();
        }

        return grad;
    }

    protected abstract DenseVector actualComputeGradient() throws LoggableException;

    protected void activateGradCache() {
        isGradientCacheActual = true;
    }

    protected void deactivateGradientCache() {
        isGradientCacheActual = false;
    }

    private void incGradComputeCount() {
        gradientComputeCount++;
    }

    private void setGradientCache(DenseVector cachedGrad) {
        this.cachedGrad = cachedGrad.deepCopy();
    }
}
