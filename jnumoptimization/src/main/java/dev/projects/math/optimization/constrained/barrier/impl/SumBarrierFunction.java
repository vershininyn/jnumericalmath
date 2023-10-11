package dev.projects.math.optimization.constrained.barrier.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.constrained.barrier.base.AbstractBarrierBuilder;
import dev.projects.math.optimization.constrained.barrier.base.IBarrierFunction;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

import java.util.ArrayList;
import java.util.List;

public class SumBarrierFunction
        extends AbstractBarrierFunction
        implements IBarrierFunction {

    public SumBarrierFunction(double kInitialCoefficient,
                              double kIncreaseStepCoefficient,
                              IScalarDifferentiableFunction function,
                              List<IScalarDifferentiableFunction> equalityFunctionsSet,
                              List<IScalarDifferentiableFunction> inequalityFunctionsSet) throws LoggableException {
        super(kInitialCoefficient, kIncreaseStepCoefficient, function, equalityFunctionsSet, inequalityFunctionsSet);
    }

    public SumBarrierFunctionBuilder builder(double kInitialCoefficient,
                                                                double kIncreaseStepCoefficient,
                                                                IScalarDifferentiableFunction nestedFunction) throws LoggableException {
        return new SumBarrierFunctionBuilder(kInitialCoefficient, kIncreaseStepCoefficient, nestedFunction);
    }

    public static class SumBarrierFunctionBuilder extends AbstractBarrierBuilder {
        public SumBarrierFunctionBuilder(double kInitialCoefficient,
                                         double kIncreaseStepCoefficient,
                                         IScalarDifferentiableFunction nestedFunction) throws LoggableException {
            super(kInitialCoefficient, kIncreaseStepCoefficient, nestedFunction);
        }

        public IBarrierFunction actualBuild() throws LoggableException {
            return new SumBarrierFunction(getKCoefficient(),
                    getKIncreaseStepCoefficient(),
                    getNestedFunction(),
                    getEqualityFunctionsSet(),
                    getInequalityFunctionsSet());
        }
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        List<IScalarDifferentiableFunction> eqList = new ArrayList<>(64);

        for(IScalarDifferentiableFunction fun: getEqualityConstraintsFunctionSet()) {
            eqList.add((IScalarDifferentiableFunction) fun.deepCopy());
        }

        List<IScalarDifferentiableFunction> ineqList = new ArrayList<>(64);

        for(IScalarDifferentiableFunction fun: getInequalityConstraintsFunctionSet()) {
            ineqList.add((IScalarDifferentiableFunction) fun.deepCopy());
        }

        return new SumBarrierFunction(getKCoefficient(),
                getKIncreaseStepCoefficient(),
                (IScalarDifferentiableFunction) getNestedFunction().deepCopy(),
                eqList,
                ineqList);
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
        double out = getNestedFunction().computeOutput(), sum = 0.0;

        for(IScalarDifferentiableFunction funObj: getEqualityConstraintsFunctionSet()) {
            sum += funObj.computeOutput();
        }

        for(IScalarDifferentiableFunction funObj: getInequalityConstraintsFunctionSet()){
            sum += funObj.computeOutput();
        }

        out += getKCoefficient()*sum;

        setKCoefficient(getKCoefficient()*getKIncreaseStepCoefficient());

        return out;
    }

    @Override
    protected DenseVector actualComputeGradient() throws LoggableException {
        DenseVector sumGradient = DenseVector.getInstance(getNestedFunction().getVariablesCount(), 0.0);

        for(IScalarDifferentiableFunction funObj: getEqualityConstraintsFunctionSet()) {
            sumGradient.add(funObj.computeGradient());
        }

        for(IScalarDifferentiableFunction funObj: getInequalityConstraintsFunctionSet()) {
            sumGradient.add(funObj.computeGradient());
        }

        return getNestedFunction().computeGradient().deepCopy().add(getKCoefficient(), sumGradient);
    }

    @Override
    public String getName() {
        return getNestedFunction().getName() + " with sum{max(0,inequality)} + sum{abs(equality)}";
    }
}
