package dev.projects.math.jnummath.jtransformations.function.scalar.differentiable;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.AbstractScalarFunction;

/**
 * @author vyn
 */
public abstract class AbstractScalarDifferentiableFunction
        extends AbstractScalarFunction {
    private boolean isGradientCacheActual = false;
    private long gradientComputeCount = 0;

    private DenseVector cachedGrad;

    protected AbstractScalarDifferentiableFunction(int variablesCount)   {
        super(variablesCount);

        cachedGrad = DenseVector.getInstance(variablesCount);
    }

    protected AbstractScalarDifferentiableFunction(DenseVector variables)   {
        this(variables.getSize());

        setVariables(variables);
    }

    public long getGradientComputeCount() {
        return gradientComputeCount;
    }

    @Override
    public void reset()   {
        super.reset();

        gradientComputeCount = 0;
        setVariables(DenseVector.getInstance(getVariablesCount(), 0.0));
    }

    public void setVariables(DenseVector variables)   {
        super.setVariables(variables);

        deactivateGradientCache();
    }

    public DenseVector getCachedGradient() {
        return cachedGrad.deepCopy();
    }

    public DenseVector computeGradient(DenseVector variables)   {
        setVariables(variables);

        return computeGradient();
    }

    public DenseVector computeGradient()   {
        DenseVector grad = getCachedGradient();

        if (!isGradientCacheActual) {
            grad = actualComputeGradient();

            incGradComputeCount();

            setGradientCache(grad);
            activateGradCache();
        }

        return grad;
    }

    protected abstract DenseVector actualComputeGradient()  ;

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
