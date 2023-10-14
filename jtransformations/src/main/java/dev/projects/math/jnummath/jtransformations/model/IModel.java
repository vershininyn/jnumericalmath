package dev.projects.math.jnummath.jtransformations.model;

import dev.projects.math.jnummath.jkernel.ICloneable;
import dev.projects.math.jnummath.jtransformations.function.IFunction;

/**
 * @author vyn
 *
 */
public interface IModel<TInputData,TOutputData,TParameters extends ICloneable<TParameters>> extends IFunction<TInputData,TOutputData>
{
    int getParametersCount();

    TParameters getParameters()  ;

    void setParameters(final TParameters pParameters)  ;

    TOutputData computeOutput(final TInputData pVariables, final TParameters pParameters)  ;
}
