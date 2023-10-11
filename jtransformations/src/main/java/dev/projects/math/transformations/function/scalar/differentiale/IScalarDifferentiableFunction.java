package dev.projects.math.transformations.function.scalar.differentiale;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.IDifferentiableFunction;
import dev.projects.math.transformations.function.scalar.IScalarFunction;

public interface IScalarDifferentiableFunction
        extends IScalarFunction,
        IDifferentiableFunction<DenseVector,Double> {

}
