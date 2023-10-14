package dev.projects.math.jnummath.jtransformations.model;

import dev.projects.math.jnummath.jkernel.ICloneable;
import dev.projects.math.jnummath.jkernel.ICountable;
import dev.projects.math.jnummath.jtransformations.function.IDifferentiableFunction;


public interface IDifferentiableModel<TInputData extends ICountable,
        TOutputResult,
        TParameters extends ICloneable<TParameters>,
        TDifferentiableResult>
        extends IModel<TInputData, TOutputResult, TParameters>, IDifferentiableFunction<TInputData, TOutputResult> {
    TDifferentiableResult computeGradient(final TInputData variables,
                                          final TParameters parameters)  ;
}
