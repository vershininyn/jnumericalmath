package dev.projects.math.optimization.constrained.barrier.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.BridgeScalarDifferentiableFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBarrierFunction
        extends BridgeScalarDifferentiableFunction {
    @Getter
    @Setter
    private double kCoefficient, kIncreaseStepCoefficient;

    @Getter
    private final List<IScalarDifferentiableFunction> equalityConstraintsFunctionSet = new ArrayList<>();

    @Getter
    private final List<IScalarDifferentiableFunction> inequalityConstraintsFunctionSet = new ArrayList<>();

    public AbstractBarrierFunction(double kInitialCoefficient,
                                   double kIncreaseStepCoefficient,
                                   IScalarDifferentiableFunction function,
                                   List<IScalarDifferentiableFunction> equalityFunctionsSet,
                                   List<IScalarDifferentiableFunction> inequalityFunctionsSet) throws LoggableException {
        super(function);

        if (kIncreaseStepCoefficient <= 1.0) throw new LoggableException("Unacceptable a increase step coefficient");

        if ((equalityFunctionsSet.isEmpty()) && (inequalityFunctionsSet.isEmpty()))
            throw new LoggableException("A equality and inequality functions set can\'t be empty at the same time");

        setEqualityConstraintsFunctionSet(equalityFunctionsSet);
        setInequalityConstraintsFunctionSet(inequalityFunctionsSet);

        setKIncreaseStepCoefficient(kIncreaseStepCoefficient);
        setKCoefficient(kInitialCoefficient);

        setNestedFunction(function);
    }

    @Override
    public void setVariables(DenseVector variables) throws LoggableException {
        IScalarDifferentiableFunction fun = getNestedFunction();

        fun.setVariables(variables);

        for(IScalarDifferentiableFunction funObj: getEqualityConstraintsFunctionSet()) {
            funObj.setVariables(variables);
        }

        for(IScalarDifferentiableFunction funObj: getInequalityConstraintsFunctionSet()) {
            funObj.setVariables(variables);
        }

        deactivateGradientCache();
        deactivateOutputCache();
    }

    @Override
    public DenseVector getVariables() throws LoggableException {
        return getNestedFunction().getVariables().deepCopy();
    }

    public void setEqualityConstraintsFunctionSet(List<IScalarDifferentiableFunction> equalityFunctionsSet) throws LoggableException {
        if (equalityFunctionsSet == null) throw new LoggableException("Unacceptable equality functions set");

        equalityConstraintsFunctionSet.addAll(equalityFunctionsSet);
    }

    public void setInequalityConstraintsFunctionSet(List<IScalarDifferentiableFunction> inequalityFunctionsSet) throws LoggableException {
        if (inequalityFunctionsSet == null) throw new LoggableException("Unacceptable inequality functions set");

        inequalityConstraintsFunctionSet.addAll(inequalityFunctionsSet);
    }
}
