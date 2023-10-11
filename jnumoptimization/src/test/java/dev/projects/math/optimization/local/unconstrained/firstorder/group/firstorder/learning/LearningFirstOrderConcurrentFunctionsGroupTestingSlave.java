package dev.projects.math.optimization.local.unconstrained.firstorder.group.firstorder.learning;

import dev.projects.math.optimization.local.unconstrained.firstorder.group.firstorder.FirstOrderConcurrentFunctionsGroupTestingSlave;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.learning.FirstOrderLearningCriticalOptimizationAttributes;
import dev.projects.math.optimization.unconstrained.local.algorithms.firstorder.base.learning.IFirstOrderLearningUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.firstorder.impl.learning.IFirstOrderLearningLinearSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

import java.util.List;
import java.util.concurrent.Callable;

public class LearningFirstOrderConcurrentFunctionsGroupTestingSlave
                extends FirstOrderConcurrentFunctionsGroupTestingSlave<IFirstOrderLearningLinearSearchAlgorithm,
                        IFirstOrderLearningDirectionSearchAlgorithm,
                        FirstOrderLearningCriticalOptimizationAttributes,
                        IFirstOrderLearningUnconstrainedOptimizationAlgorithm,
                        dev.projects.math.optimization.local.unconstrained.firstorder.group.firstorder.Learning.LearningFirstOrderFunctionsTestingGroupMap>
                implements Callable<dev.projects.math.optimization.local.unconstrained.firstorder.group.firstorder.Learning.LearningFirstOrderFunctionsTestingGroupMap> {

    public LearningFirstOrderConcurrentFunctionsGroupTestingSlave(List<IFirstOrderLearningUnconstrainedOptimizationAlgorithm> optAlgorithmsGroup, IScalarDifferentiableFunction function) throws LoggableException {
        super(optAlgorithmsGroup, function);
    }

    @Override
    protected dev.projects.math.optimization.local.unconstrained.firstorder.group.firstorder.Learning.LearningFirstOrderFunctionsTestingGroupMap getFunctionsTestingGroupMapInstance() throws LoggableException {
        return new dev.projects.math.optimization.local.unconstrained.firstorder.group.firstorder.Learning.LearningFirstOrderFunctionsTestingGroupMap();
    }
}
