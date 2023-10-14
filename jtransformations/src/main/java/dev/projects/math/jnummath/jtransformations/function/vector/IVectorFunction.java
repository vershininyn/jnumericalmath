package dev.projects.math.jnummath.jtransformations.function.vector;

import dev.projects.math.jnummath.jkernel.ICloneable;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.IFunction;

public interface IVectorFunction
        extends IFunction<DenseVector, DenseVector>, ICloneable<IVectorFunction>
{

}
