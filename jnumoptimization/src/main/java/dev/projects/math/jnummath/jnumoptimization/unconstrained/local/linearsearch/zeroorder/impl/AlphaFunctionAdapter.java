package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.zeroorder.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.AbstractScalarFunction;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;

public class AlphaFunctionAdapter
        extends AbstractScalarFunction
        implements IScalarFunction {

    private final DenseVector currentApproximation, direction;
    private IScalarFunction adoptedFunction;

    public AlphaFunctionAdapter(DenseVector pCurrentApproximation,
                                DenseVector pDirection,
                                IScalarFunction pAdoptedFunction)   {
        super(DenseVector.getInstance(1));

        currentApproximation = DenseVector.getInstance(pCurrentApproximation.getData());
        direction = DenseVector.getInstance(pDirection.getData());
        adoptedFunction = pAdoptedFunction;
    }

    @Override
    protected Double actualComputeOutput()   {
        DenseVector vars = getVariables();

        return adoptedFunction.computeOutput(currentApproximation.deepCopy().add(direction.deepCopy().multiplyByCoefficient(vars.get(0))));
    }

    @Override
    public String getName() {
        return "adapter of fun = "+adoptedFunction.getName();
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
        return new AlphaFunctionAdapter(currentApproximation.deepCopy(),direction.deepCopy(),(IScalarFunction) adoptedFunction.deepCopy());
    }
}
