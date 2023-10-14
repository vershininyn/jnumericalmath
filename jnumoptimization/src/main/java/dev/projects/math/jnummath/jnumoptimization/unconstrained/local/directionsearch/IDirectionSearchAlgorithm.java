package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch;

import dev.projects.math.jnummath.jkernel.ICloneable;
import dev.projects.math.jnummath.jkernel.IResetable;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;

public interface IDirectionSearchAlgorithm<TFunction extends IScalarFunction>
        extends IResetable, ICloneable<IDirectionSearchAlgorithm<TFunction>>
{
    DenseVector computeDirection(TFunction function, DenseVector currentPoint)  ;

    DenseVector getCurrentDirection()  ;

    void initializeProcess(TFunction function)  ;

    void deinitializeProcess()  ;

    String getAlgorithmName();
}
