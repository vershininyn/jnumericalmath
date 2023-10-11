package dev.projects.math.transformations.function.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.differentiale.AbstractScalarDifferentiableFunction;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class ICQPScalarDifferentiableFunction
                extends AbstractScalarDifferentiableFunction
                implements IScalarDifferentiableFunction {
    public ICQPScalarDifferentiableFunction(int variablesCount) throws LoggableException {
        super(variablesCount);
    }

    public ICQPScalarDifferentiableFunction(DenseVector variables) throws LoggableException {
        super(variables);
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
        DenseVector vars = getVariables();
        double out = 0.0;

        for(int index= 0; index < getVariablesCount() - 1; index++){
            out += 1000.0*Math.pow(vars.getValue(index) - vars.getValue(index+1),2.0) + Math.pow(1.0 - vars.getValue(index+1),2.0);
        }

        return out;
    }

    @Override
    protected DenseVector actualComputeGradient() throws LoggableException {
        DenseVector vars= getVariables(), gradient = DenseVector.getInstance(getVariablesCount(), 0.0);

        for(int index= 0; index < getVariablesCount() - 1; index = index + 2){
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
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        return new ICQPScalarDifferentiableFunction(getVariables().deepCopy());
    }
}
