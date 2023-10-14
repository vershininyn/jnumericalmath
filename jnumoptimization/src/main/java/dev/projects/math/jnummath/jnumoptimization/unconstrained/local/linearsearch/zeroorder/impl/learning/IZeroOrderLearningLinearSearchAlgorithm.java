package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.zeroorder.impl.learning;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.base.ILearningLinearSearchAlgorithmMixin;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.zeroorder.IZeroOrderLinearSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;

public interface IZeroOrderLearningLinearSearchAlgorithm
        extends IZeroOrderLinearSearchAlgorithm, ILearningLinearSearchAlgorithmMixin<IScalarFunction, ZeroOrderOptimalVariables> {

    ZeroOrderLearningLinearSearchAlgorithmStateDto getState();

}
