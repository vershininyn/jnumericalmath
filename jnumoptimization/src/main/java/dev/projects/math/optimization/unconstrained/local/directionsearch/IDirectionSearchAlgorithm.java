package dev.projects.math.optimization.unconstrained.local.directionsearch;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.linalgebra.ICloneable;
import dev.projects.math.linalgebra.IResetable;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;

public interface IDirectionSearchAlgorithm<TFunction extends IScalarFunction>
        extends IResetable, ICloneable<IDirectionSearchAlgorithm<TFunction>>
{
    DenseVector computeDirection(TFunction function, DenseVector currentPoint) throws LoggableException;

    DenseVector getCurrentDirection() throws LoggableException;

    void initializeProcess(TFunction function) throws LoggableException;
    void deinitializeProcess() throws LoggableException;

    String getAlgorithmName();
}
