package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.impl.learning;

import dev.projects.math.transformations.ICloneable;
import dev.projects.utils.exception.LoggableException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FirstOrderLearningLinearSearchOptimalParametersDTO implements ICloneable<FirstOrderLearningLinearSearchOptimalParametersDTO> {
    private double gammaOne = 0.2;
    private double gammaTwo = 0.2;
    private double gammaThree = 0.1;
    private double gammaFour = 0.1;
    private double gammaFive = 0.1;

    @Override
    public FirstOrderLearningLinearSearchOptimalParametersDTO deepCopy()   {
        return new FirstOrderLearningLinearSearchOptimalParametersDTO(getGammaOne(),getGammaTwo(),getGammaThree(),getGammaFour(),getGammaFive());
    }
}
