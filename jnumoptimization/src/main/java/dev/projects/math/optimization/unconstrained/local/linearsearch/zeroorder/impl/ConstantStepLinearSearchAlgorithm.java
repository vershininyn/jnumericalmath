package dev.projects.math.optimization.unconstrained.local.linearsearch.zeroorder.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.linearsearch.base.ILinearSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.zeroorder.IZeroOrderLinearSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
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
    public void reset() throws LoggableException {

    }

    @Override
    public ILinearSearchAlgorithm<IScalarFunction> deepCopy() throws LoggableException {
        return new ConstantStepLinearSearchAlgorithm(getStep());
    }
}
