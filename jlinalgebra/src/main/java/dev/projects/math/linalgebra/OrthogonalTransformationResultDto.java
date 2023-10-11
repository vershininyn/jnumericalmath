package dev.projects.math.linalgebra;

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
