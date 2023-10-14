package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.base;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.IFirstOrderDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.impl.learning.FirstOrderOptimalVariables;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public interface IFirstOrderLearningDirectionSearchAlgorithm extends IFirstOrderDirectionSearchAlgorithm {
    double correctAlgorithm(int iterationCount,
                            double initialStep,
                            FirstOrderOptimalVariables optVariables,
                            DenseVector currentPoint,
                            DenseVector correctionGradient,
                            IScalarDifferentiableFunction function)  ;
}
