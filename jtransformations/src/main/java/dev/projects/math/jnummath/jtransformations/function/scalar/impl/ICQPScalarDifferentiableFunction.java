package dev.projects.math.jnummath.jtransformations.function.scalar.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.AbstractScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class ICQPScalarDifferentiableFunction
                extends AbstractScalarDifferentiableFunction
                implements IScalarDifferentiableFunction {
    public ICQPScalarDifferentiableFunction(int variablesCount)   {
        super(variablesCount);
    }

    public ICQPScalarDifferentiableFunction(DenseVector variables)   {
        super(variables);
    }

    @Override
    protected Double actualComputeOutput()   {
        DenseVector vars = getVariables();
        double out = 0.0;

        for(int index = 0; index < getInputDimensionSize() - 1; index++){
            out += 1000.0*Math.pow(vars.getValue(index) - vars.getValue(index+1),2.0) + Math.pow(1.0 - vars.getValue(index+1),2.0);
        }

        return out;
    }

    @Override
    protected DenseVector actualComputeGradient()   {
        DenseVector vars= getVariables(), gradient = DenseVector.getInstance(getInputDimensionSize(), 0.0);

        for(int index = 0; index < getInputDimensionSize() - 1; index = index + 2){
            double derivateK = 2000.0*(vars.getValue(index) - vars.getValue(index+1)),
                    derivateKPlusOne = (-1.0)*derivateK - 2.0*(1.0 - vars.getValue(index+1));

            gradient.setValue(index, derivateK);
            gradient.setValue(index+1, derivateKPlusOne);
        }

        return gradient;
    }

    @Override
    public String getName() {
        return "sum{1000*(x[k]-x[k+1])^2 + (1-x[k+1]),k=1,...,N-1}";
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
        return new ICQPScalarDifferentiableFunction(getVariables().deepCopy());
    }
}
