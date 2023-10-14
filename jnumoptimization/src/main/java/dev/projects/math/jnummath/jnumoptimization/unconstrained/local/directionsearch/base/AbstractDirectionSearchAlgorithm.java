package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.base;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public abstract class AbstractDirectionSearchAlgorithm<TFunction extends IScalarFunction> {
    private DenseVector currentDirection, internalDirection;

    private int primeHashCode;

    public AbstractDirectionSearchAlgorithm(int primeHash) {
        setPrimeHashCode(primeHash);
    }

    public DenseVector computeDirection(TFunction function, DenseVector currentPoint)   {
        DenseVector direction = actualComputeDirection(function, currentPoint);

        setCurrentDirection(direction.deepCopy());

        return direction;
    }

    public void initializeProcess(TFunction function)   {
        currentDirection = DenseVector.getInstance(function.getInputDimensionSize(), Double.MAX_VALUE);
        internalDirection = DenseVector.getInstance(function.getInputDimensionSize(), 0.0);
    }

    public void deinitializeProcess()   {

    }

    public abstract DenseVector actualComputeDirection(TFunction function, DenseVector currentPoint)  ;

    public void reset()   { }

    @Override
    public int hashCode() {
        return Objects.hash(getPrimeHashCode());
    }
}

