package dev.projects.math.transformations.function;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.linalgebra.ICountable;
import dev.projects.utils.exception.LoggableException;

/**
 * @author vyn
 *
 */
public interface IDifferentiableFunction<TInputData extends ICountable,TOutputResult>
	extends IFunction<TInputData,TOutputResult>
{
  long getGradientComputeCount();

  DenseVector getCachedGradient();

  DenseVector computeGradient() throws LoggableException;

  DenseVector computeGradient(TInputData pVariables) throws LoggableException;
}
