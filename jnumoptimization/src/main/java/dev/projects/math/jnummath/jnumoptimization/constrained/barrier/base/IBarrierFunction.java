package dev.projects.math.jnummath.jnumoptimization.constrained.barrier.base;

import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

import java.util.List;

public interface IBarrierFunction extends IScalarDifferentiableFunction {
    double getKCoefficient();
    void setKCoefficient(double kCoefficient)  ;

    IScalarDifferentiableFunction getNestedFunction();
    void setNestedFunction(IScalarDifferentiableFunction function)  ;

    void setEqualityConstraintsFunctionSet(List<IScalarDifferentiableFunction> equalityFunctionsSet)  ;
    List<IScalarDifferentiableFunction> getEqualityConstraintsFunctionSet();

    void setInequalityConstraintsFunctionSet(List<IScalarDifferentiableFunction> inequalityFunctionsSet)  ;
    List<IScalarDifferentiableFunction> getInequalityConstraintsFunctionSet();
}
