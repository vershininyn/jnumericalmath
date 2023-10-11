package dev.projects.math.transformations.model;

import dev.projects.math.linalgebra.ICloneable;
import dev.projects.math.linalgebra.ICountable;
import dev.projects.math.transformations.function.IDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public interface IDifferentiableModel<TInputData extends ICountable,
        TOutputResult,
        TParameters extends ICloneable<TParameters>,
        TDifferentiableResult>
        extends IModel<TInputData, TOutputResult, TParameters>, IDifferentiableFunction<TInputData, TOutputResult> {
    TDifferentiableResult computeGradient(final TInputData variables,
                                          final TParameters parameters) throws LoggableException;
}
