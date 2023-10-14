package dev.projects.math.jnummath.jnumoptimization.unconstrained.global.base;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class GridExtensionResult {
    private double edgeLength = 0.0;
    private List<DenseVector> centersList;
}
