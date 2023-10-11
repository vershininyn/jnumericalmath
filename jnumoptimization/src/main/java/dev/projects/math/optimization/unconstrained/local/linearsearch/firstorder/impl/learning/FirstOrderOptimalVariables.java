package dev.projects.math.optimization.unconstrained.local.linearsearch.firstorder.impl.learning;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.linearsearch.zeroorder.impl.learning.ZeroOrderOptimalVariables;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FirstOrderOptimalVariables extends ZeroOrderOptimalVariables {
    private DenseVector gm;

    public FirstOrderOptimalVariables(double optimalStep, double optimalFunctionOutput, DenseVector optimalGradient) {
        super(optimalStep, optimalFunctionOutput);
        gm = optimalGradient.deepCopy();
    }
}
