package dev.projects.math.jnummath.jlinalgebra;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseMatrix;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OrthogonalTransformationResultDto {
    private DenseMatrix matrix;
    private DenseVector y, sum;
}
