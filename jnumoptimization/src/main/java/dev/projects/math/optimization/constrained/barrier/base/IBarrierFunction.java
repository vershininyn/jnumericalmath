package dev.projects.math.optimization.constrained.barrier.base;

import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

import java.util.List;

public interface IBarrierFunction extends IScalarDifferentiableFunction {
    double getKCoefficient();
    void setKCoefficient(double kCoefficient) throws LoggableException;

    IScalarDifferentiableFunction getNestedFunction();
    void setNestedFunction(IScalarDifferentiableFunction function) throws LoggableException;

    void setEqualityConstraintsFunctionSet(List<IScalarDifferentiableFunction> equalityFunctionsSet) throws LoggableException;
    List<IScalarDifferentiableFunction> getEqualityConstraintsFunctionSet();

    void setInequalityConstraintsFunctionSet(List<IScalarDifferentiableFunction> inequalityFunctionsSet) throws LoggableException;
    List<IScalarDifferentiableFunction> getInequalityConstraintsFunctionSet();
}
