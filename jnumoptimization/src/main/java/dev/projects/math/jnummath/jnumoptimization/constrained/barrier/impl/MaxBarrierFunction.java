package dev.projects.math.jnummath.jnumoptimization.constrained.barrier.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.base.AbstractBarrierBuilder;
import dev.projects.math.jnummath.jnumoptimization.constrained.barrier.base.IBarrierFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.AbstractScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxBarrierFunction
        extends AbstractBarrierFunction
        implements IBarrierFunction {

    public MaxBarrierFunction(double kInitialCoefficient,
                              double kIncreaseStepCoefficient,
                              IScalarDifferentiableFunction function,
                              List<IScalarDifferentiableFunction> equalityFunctionsSet,
                              List<IScalarDifferentiableFunction> inequalityFunctionsSet)   {
        super(kInitialCoefficient, kIncreaseStepCoefficient, function, equalityFunctionsSet, inequalityFunctionsSet);
    }

    public MaxBarrierFunctionBuilder builder(double kInitialCoefficient,
                                             double kIncreaseStepCoefficient,
                                             IScalarDifferentiableFunction nestedFunction)   {
        return new MaxBarrierFunctionBuilder(kInitialCoefficient, kIncreaseStepCoefficient, nestedFunction);
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
        List<IScalarDifferentiableFunction> eqList = new ArrayList<>(64);

        for(IScalarDifferentiableFunction fun: getEqualityConstraintsFunctionSet()) {
            eqList.add((IScalarDifferentiableFunction)fun.deepCopy());
        }

        List<IScalarDifferentiableFunction> ineqList = new ArrayList<>(64);

        for(IScalarDifferentiableFunction fun: getInequalityConstraintsFunctionSet()) {
            ineqList.add((IScalarDifferentiableFunction) fun.deepCopy());
        }

        return new MaxBarrierFunction(getKCoefficient(), getKIncreaseStepCoefficient(),
                (IScalarDifferentiableFunction)getNestedFunction().deepCopy(),
                eqList,
                ineqList);
    }

    public static class MaxBarrierFunctionBuilder extends AbstractBarrierBuilder {
        public MaxBarrierFunctionBuilder(double kInitialCoefficient,
                                         double kIncreaseStepCoefficient,
                                         IScalarDifferentiableFunction nestedFunction)   {
            super(kInitialCoefficient, kIncreaseStepCoefficient, nestedFunction);
        }

        public IBarrierFunction actualBuild()   {
            return new MaxBarrierFunction(getKCoefficient(),
                    getKIncreaseStepCoefficient(),
                    getNestedFunction(),
                    getEqualityFunctionsSet(),
                    getInequalityFunctionsSet());
        }
    }

    @Override
    protected Double actualComputeOutput()   {
        List<IScalarDifferentiableFunction> equalitySet = getEqualityConstraintsFunctionSet(),
                inequalitySet = getInequalityConstraintsFunctionSet();

        double equalityMaxFunctionOutput = Double.NEGATIVE_INFINITY;

        if (!equalitySet.isEmpty()) {
            equalityMaxFunctionOutput = getMaxFunction(getNestedFunction().getInputDimensionSize(), equalitySet).computeOutput();
        }

        double inequalityMaxFunctionOutput = Double.NEGATIVE_INFINITY;

        if (!inequalitySet.isEmpty()) {
            inequalityMaxFunctionOutput = getMaxFunction(getNestedFunction().getInputDimensionSize(), inequalitySet).computeOutput();
        }

        double out = getNestedFunction().computeOutput() + getKCoefficient() * Math.max(equalityMaxFunctionOutput, inequalityMaxFunctionOutput);

        setKCoefficient(getKCoefficient()*getKIncreaseStepCoefficient());

        return out;
    }

    @Override
    protected DenseVector actualComputeGradient()   {
        IScalarDifferentiableFunction nestedFun = getNestedFunction();

        DenseVector nestedFunctionGrad = nestedFun.computeGradient().deepCopy();

        int variablesCount = nestedFun.getInputDimensionSize();

        IScalarDifferentiableFunction equalityMaxFunction = getMaxFunction(variablesCount,
                                            getEqualityConstraintsFunctionSet()),
                inequalityMaxFunction = getMaxFunction(variablesCount,
                                            getInequalityConstraintsFunctionSet()),
                maxFunction = getMaxFunction(variablesCount,
                                            Arrays.asList(equalityMaxFunction,inequalityMaxFunction));

        return nestedFunctionGrad.add(maxFunction.computeGradient().multiplyByCoefficient(getKCoefficient()));
    }

    @Override
    public String getName() {
        return getNestedFunction().getName() + " with max{max[inequalities+] + max[abs(equalities)]}";
    }

    private IScalarDifferentiableFunction getMaxFunction(int variablesCount,
                                                         List<IScalarDifferentiableFunction> list)   {
        if (list.isEmpty()) return new NullScalarDifferentiableFunction(variablesCount);

        double minOutput = Double.NEGATIVE_INFINITY;

        IScalarDifferentiableFunction maxFunction = list.get(0);

        for(IScalarDifferentiableFunction fun: list) {
            double output = fun.computeOutput();

            if (output > minOutput) {
                maxFunction = fun;
                minOutput = output;
            }
        }

        return maxFunction;
    }

    private class NullScalarDifferentiableFunction
                    extends AbstractScalarDifferentiableFunction
                    implements IScalarDifferentiableFunction {

        protected NullScalarDifferentiableFunction(int variablesCount)   {
            super(variablesCount);
        }

        protected NullScalarDifferentiableFunction(DenseVector variables)   {
            super(variables);
        }

        @Override
        protected Double actualComputeOutput()   {
            return Double.NEGATIVE_INFINITY;
        }

        @Override
        protected DenseVector actualComputeGradient()   {
            return DenseVector.getInstance(getInputDimensionSize(), Double.NEGATIVE_INFINITY);
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public IFunction<DenseVector, Double> deepCopy()   {
            return new NullScalarDifferentiableFunction(getVariables().deepCopy());
        }
    }
}
