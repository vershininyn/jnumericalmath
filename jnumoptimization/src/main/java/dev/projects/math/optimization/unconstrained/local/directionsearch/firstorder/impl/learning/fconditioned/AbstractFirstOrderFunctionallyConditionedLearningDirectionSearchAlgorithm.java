package dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.fconditioned;

import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.FirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;

import java.util.Objects;

public abstract class AbstractFirstOrderFunctionallyConditionedLearningDirectionSearchAlgorithm
        extends FirstOrderLearningDirectionSearchAlgorithm {
    @Getter
    private IFunctionallyConditionedTransformCoefficientFunction transformFunction;

    public AbstractFirstOrderFunctionallyConditionedLearningDirectionSearchAlgorithm(int primeHash,
                                                                                     IFunctionallyConditionedTransformCoefficientFunction transformFunction) throws LoggableException {
        super(primeHash);

        setFunctionallyConditionedFunction(transformFunction);
    }

    public void setFunctionallyConditionedFunction(IFunctionallyConditionedTransformCoefficientFunction transformFunction) throws LoggableException{
        if (transformFunction == null) throw new LoggableException("Unacceptable transform function");

        this.transformFunction = transformFunction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrimeHashCode(), getTransformFunction());
    }
}
