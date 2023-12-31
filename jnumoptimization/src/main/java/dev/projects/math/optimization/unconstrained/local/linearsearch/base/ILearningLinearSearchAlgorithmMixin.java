package dev.projects.math.optimization.unconstrained.local.linearsearch.base;

import dev.projects.math.optimization.unconstrained.local.linearsearch.zeroorder.impl.learning.ZeroOrderOptimalVariables;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;

public interface ILearningLinearSearchAlgorithmMixin<TFunction extends IScalarFunction, TOptimalVariables extends ZeroOrderOptimalVariables> {
    TOptimalVariables getOptimalVariables();

    void initializeProcess(TFunction function) throws LoggableException;
}
