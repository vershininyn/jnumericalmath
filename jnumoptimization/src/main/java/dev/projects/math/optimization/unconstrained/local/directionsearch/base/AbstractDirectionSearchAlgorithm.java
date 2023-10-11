package dev.projects.math.optimization.unconstrained.local.directionsearch.base;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
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

    public DenseVector computeDirection(TFunction function, DenseVector currentPoint) throws LoggableException {
        DenseVector direction = actualComputeDirection(function, currentPoint);

        setCurrentDirection(direction.deepCopy());

        return direction;
    }

    public void initializeProcess(TFunction function) throws LoggableException {
        currentDirection = DenseVector.getInstance(function.getVariablesCount(), Double.MAX_VALUE);
        internalDirection = DenseVector.getInstance(function.getVariablesCount(), 0.0);
    }

    public void deinitializeProcess() throws LoggableException {

    }

    public abstract DenseVector actualComputeDirection(TFunction function, DenseVector currentPoint) throws LoggableException;

    public void reset() throws LoggableException { }

    @Override
    public int hashCode() {
        return Objects.hash(getPrimeHashCode());
    }
}

