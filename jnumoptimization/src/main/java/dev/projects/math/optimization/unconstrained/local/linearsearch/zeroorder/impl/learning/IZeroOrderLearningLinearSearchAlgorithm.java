package dev.projects.math.optimization.unconstrained.local.linearsearch.zeroorder.impl.learning;

import dev.projects.math.optimization.unconstrained.local.linearsearch.base.ILearningLinearSearchAlgorithmMixin;
import dev.projects.math.optimization.unconstrained.local.linearsearch.zeroorder.IZeroOrderLinearSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.IScalarFunction;

public interface IZeroOrderLearningLinearSearchAlgorithm
        extends IZeroOrderLinearSearchAlgorithm, ILearningLinearSearchAlgorithmMixin<IScalarFunction, ZeroOrderOptimalVariables> {

    ZeroOrderLearningLinearSearchAlgorithmStateDto getState();

}
