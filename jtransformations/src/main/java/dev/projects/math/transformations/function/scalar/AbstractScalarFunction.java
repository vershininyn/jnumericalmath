package dev.projects.math.transformations.function.scalar;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.AbstractFunction;
import dev.projects.utils.exception.LoggableException;

/**
 * @author vyn
 *
 */
public abstract class AbstractScalarFunction extends AbstractFunction<DenseVector,Double>
{
    protected AbstractScalarFunction(int variablesCount) throws LoggableException {
      super(variablesCount);
    }

    @Override
    public void reset() throws LoggableException {
      super.reset();
    }

    protected AbstractScalarFunction(DenseVector variables) throws LoggableException {
      this(variables.getSize());

      setVariables(variables);
      setOutputCache(Double.MAX_VALUE);
    }

    @Override
    public void setVariables(DenseVector variables) throws LoggableException {
      if ((variables == null)
              || (variables.getSize() != getVariablesCount())) { throw new LoggableException("Unacceptable variables vector"); }

      super.setVariables(variables);
    }
}
