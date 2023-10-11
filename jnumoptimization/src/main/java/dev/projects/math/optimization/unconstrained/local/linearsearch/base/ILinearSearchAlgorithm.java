package dev.projects.math.optimization.unconstrained.local.linearsearch.base;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.linalgebra.ICloneable;
import dev.projects.math.linalgebra.IResetable;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;

public interface ILinearSearchAlgorithm<TFunction extends IScalarFunction>
        extends IResetable, ICloneable<ILinearSearchAlgorithm<TFunction>>
{
    double computeStep(TFunction function,
                       DenseVector currentPoint,
                       DenseVector currentDirection) throws LoggableException, CriticalGradientNormValueIsAchievedException;

    void setInitialStep(double step);

    double getInitialStep();

    String getAlgorithmName();
}
