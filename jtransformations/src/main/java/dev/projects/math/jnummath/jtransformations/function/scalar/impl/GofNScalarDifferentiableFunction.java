package dev.projects.math.jnummath.jtransformations.function.scalar.impl;


import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.AbstractMaxScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;

public class GofNScalarDifferentiableFunction
        extends AbstractMaxScalarDifferentiableFunction
        implements IScalarDifferentiableFunction {
    public GofNScalarDifferentiableFunction(int variablesCount)   {
        super(variablesCount);
    }

    public GofNScalarDifferentiableFunction(DenseVector variables)   {
        super(variables);
    }

    @Override
    protected Double actualComputeOutput()   {
        double[] maxEntry = computeMaxEntry((index,value) -> value);
        double sum = 0.0;
        DenseVector vars = getVariables();

        for(int index = 0; index < getInputDimensionSize(); index++){
            sum += vars.getValue(index);
        }

        return maxEntry[1]* getInputDimensionSize() - sum;
    }

    @Override
    protected DenseVector actualComputeGradient()   {
        double[] maxEntry = computeMaxEntry((index,value) -> value);

        int varsCount = getInputDimensionSize(),
                index = (int)maxEntry[0];

        return DenseVector.getInstance(varsCount, 0.0)
                .setValue(index, varsCount)
                .substract(DenseVector.getInstance(varsCount, 1.0));
    }

    @Override
    public String getName() {
        return "max{x[k],k=1,...,N}*N - sum{x[k],k=1,...,N}";
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
        return new GofNScalarDifferentiableFunction(getVariables().deepCopy());
    }
}
