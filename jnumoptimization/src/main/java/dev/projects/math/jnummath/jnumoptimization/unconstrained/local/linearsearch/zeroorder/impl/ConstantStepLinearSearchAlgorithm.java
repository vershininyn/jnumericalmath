package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.zeroorder.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.zeroorder.IZeroOrderLinearSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.base.ILinearSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ConstantStepLinearSearchAlgorithm implements IZeroOrderLinearSearchAlgorithm {
    private double step = 0.0;

    @Override
    public double computeStep(IScalarFunction function, DenseVector currentPoint, DenseVector currentDirection) {
        return step;
    }

    @Override
    public void setInitialStep(double step) {

    }

    @Override
    public double getInitialStep() {
        return 0;
    }

    @Override
    public String getAlgorithmName() {
        return "Constant step linear search alg";
    }

    @Override
    public void reset()   {

    }

    @Override
    public ILinearSearchAlgorithm<IScalarFunction> deepCopy()   {
        return new ConstantStepLinearSearchAlgorithm(getStep());
    }
}
