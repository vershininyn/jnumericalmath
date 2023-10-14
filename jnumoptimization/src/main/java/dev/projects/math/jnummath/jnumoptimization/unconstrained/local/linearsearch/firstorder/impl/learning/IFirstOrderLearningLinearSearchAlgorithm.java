package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.impl.learning;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.IFirstOrderLinearSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.base.ILearningLinearSearchAlgorithmMixin;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public interface IFirstOrderLearningLinearSearchAlgorithm
        extends IFirstOrderLinearSearchAlgorithm,
        ILearningLinearSearchAlgorithmMixin<IScalarDifferentiableFunction, FirstOrderOptimalVariables> {
    DenseVector getCorrectionGradient();

    IFirstOrderLearningLinearSearchAlgorithm setQMin(double qMin);
    double getQMin();

    IFirstOrderLearningLinearSearchAlgorithm setQMax(double qMax);
    double getQMax();

    FirstOrderLearningLinearSearchOptimalParametersDTO getGammaOptimalParameters();
    IFirstOrderLearningLinearSearchAlgorithm setGammaOptimalParameters(FirstOrderLearningLinearSearchOptimalParametersDTO gammaDTO)  ;
}
