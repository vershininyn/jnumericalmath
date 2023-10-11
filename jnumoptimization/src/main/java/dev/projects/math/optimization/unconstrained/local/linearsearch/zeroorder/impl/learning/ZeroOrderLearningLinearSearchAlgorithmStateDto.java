package dev.projects.math.optimization.unconstrained.local.linearsearch.zeroorder.impl.learning;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ZeroOrderLearningLinearSearchAlgorithmStateDto {
    private double h1 = 0.0, h2 = 0.0, h3 = 0.0, currentHn = 0.0;
    private double f1 = 0.0, f2 = 0.0, f3 = 0.0;
}
