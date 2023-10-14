package dev.projects.math.jnummath.jnumoptimization.constrained.barrier.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.base.AbstractBarrierBuilder;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.base.IBarrierFunction;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
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
                              List<IScalarDifferentiableFunction> inequalityFunctionsSet)   {
        super(kInitialCoefficient, kIncreaseStepCoefficient, function, equalityFunctionsSet, inequalityFunctionsSet);
    }

    public SumBarrierFunctionBuilder builder(double kInitialCoefficient,
                                                                double kIncreaseStepCoefficient,
                                                                IScalarDifferentiableFunction nestedFunction)   {
        return new SumBarrierFunctionBuilder(kInitialCoefficient, kIncreaseStepCoefficient, nestedFunction);
    }

    public static class SumBarrierFunctionBuilder extends AbstractBarrierBuilder {
        public SumBarrierFunctionBuilder(double kInitialCoefficient,
                                         double kIncreaseStepCoefficient,
                                         IScalarDifferentiableFunction nestedFunction)   {
            super(kInitialCoefficient, kIncreaseStepCoefficient, nestedFunction);
        }

        public IBarrierFunction actualBuild()   {
            return new SumBarrierFunction(getKCoefficient(),
                    getKIncreaseStepCoefficient(),
                    getNestedFunction(),
                    getEqualityFunctionsSet(),
                    getInequalityFunctionsSet());
        }
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
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
    protected Double actualComputeOutput()   {
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
    protected DenseVector actualComputeGradient()   {
        DenseVector sumGradient = DenseVector.getInstance(getNestedFunction().getInputDimensionSize(), 0.0);

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
