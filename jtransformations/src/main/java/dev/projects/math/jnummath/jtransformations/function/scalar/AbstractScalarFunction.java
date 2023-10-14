package dev.projects.math.jnummath.jtransformations.function.scalar;

import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.AbstractFunction;

/**
 * @author vyn
 *
 */
public abstract class AbstractScalarFunction extends AbstractFunction<DenseVector,Double>
{
    protected AbstractScalarFunction(int variablesCount)   {
      super(variablesCount);
    }

    @Override
    public void reset()   {
      super.reset();
    }

    protected AbstractScalarFunction(DenseVector variables)   {
      this(variables.getSize());

      setVariables(variables);
      setOutputCache(Double.MAX_VALUE);
    }

    @Override
    public void setVariables(DenseVector variables)   {
      if ((variables == null)
              || (variables.getSize() != getVariablesCount())) { throw new LoggableException("Unacceptable variables vector"); }

      super.setVariables(variables);
    }
}
