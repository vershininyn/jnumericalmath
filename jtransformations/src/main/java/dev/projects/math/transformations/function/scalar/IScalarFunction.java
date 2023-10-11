package dev.projects.math.transformations.function.scalar;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.linalgebra.ICloneable;
import dev.projects.math.transformations.function.IFunction;

/**
 * @author vyn
 * 
 */
public interface IScalarFunction
        extends IFunction<DenseVector, Double>, ICloneable<IFunction<DenseVector, Double>>
{
  
}
