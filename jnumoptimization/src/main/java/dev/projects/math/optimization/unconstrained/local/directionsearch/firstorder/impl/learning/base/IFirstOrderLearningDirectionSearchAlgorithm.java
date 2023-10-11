package dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.base;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.IFirstOrderDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.firstorder.impl.learning.FirstOrderOptimalVariables;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public interface IFirstOrderLearningDirectionSearchAlgorithm extends IFirstOrderDirectionSearchAlgorithm {
    double correctAlgorithm(int iterationCount,
                            double initialStep,
                            FirstOrderOptimalVariables optVariables,
                            DenseVector currentPoint,
                            DenseVector correctionGradient,
                            IScalarDifferentiableFunction function) throws LoggableException;
}
