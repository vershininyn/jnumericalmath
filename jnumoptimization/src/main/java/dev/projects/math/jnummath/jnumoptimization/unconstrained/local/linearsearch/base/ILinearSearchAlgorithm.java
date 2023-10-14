package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.base;

import dev.projects.math.jnummath.jkernel.ICloneable;
import dev.projects.math.jnummath.jkernel.IResetable;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;

public interface ILinearSearchAlgorithm<TFunction extends IScalarFunction>
        extends IResetable, ICloneable<ILinearSearchAlgorithm<TFunction>>
{
    double computeStep(TFunction function,
                       DenseVector currentPoint,
                       DenseVector currentDirection)  , CriticalGradientNormValueIsAchievedException;

    void setInitialStep(double step);

    double getInitialStep();

    String getAlgorithmName();
}
