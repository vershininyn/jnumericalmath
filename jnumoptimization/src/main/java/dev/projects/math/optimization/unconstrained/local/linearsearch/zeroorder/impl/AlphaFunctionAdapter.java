package dev.projects.math.optimization.unconstrained.local.linearsearch.zeroorder.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.AbstractScalarFunction;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;

public class AlphaFunctionAdapter
        extends AbstractScalarFunction
        implements IScalarFunction {

    private final DenseVector currentApproximation, direction;
    private IScalarFunction adoptedFunction;

    public AlphaFunctionAdapter(DenseVector pCurrentApproximation,
                                DenseVector pDirection,
                                IScalarFunction pAdoptedFunction) throws LoggableException {
        super(DenseVector.getInstance(1));

        currentApproximation = DenseVector.getInstance(pCurrentApproximation.getData());
        direction = DenseVector.getInstance(pDirection.getData());
        adoptedFunction = pAdoptedFunction;
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
        DenseVector vars = getVariables();

        return adoptedFunction.computeOutput(currentApproximation.deepCopy().add(direction.deepCopy().multiplyByCoefficient(vars.get(0))));
    }

    @Override
    public String getName() {
        return "adapter of fun = "+adoptedFunction.getName();
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        return new AlphaFunctionAdapter(currentApproximation.deepCopy(),direction.deepCopy(),(IScalarFunction) adoptedFunction.deepCopy());
    }
}
