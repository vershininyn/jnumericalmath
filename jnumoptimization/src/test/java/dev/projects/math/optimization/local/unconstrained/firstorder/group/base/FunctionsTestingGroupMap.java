package dev.projects.math.optimization.local.unconstrained.firstorder.group.base;

import dev.projects.math.optimization.unconstrained.local.algorithms.IUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderLocalCriticalOptimizationAttributes;
import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.math.optimization.unconstrained.local.directionsearch.IDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.base.ILinearSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class FunctionsTestingGroupMap<TFunction extends IScalarFunction,
                                        TLinearSearchAlgorithm extends ILinearSearchAlgorithm<TFunction>,
                                        TDirectionSearchAlgorithm extends IDirectionSearchAlgorithm<TFunction>,
                                        TOptimizationAttributes extends ZeroOrderLocalCriticalOptimizationAttributes,
                                        TOptimizationResults extends ZeroOrderOptimizationResults>
                    extends LinkedHashMap<IUnconstrainedOptimizationAlgorithm<? extends TFunction,
                                                                            ? extends TLinearSearchAlgorithm,
                                                                            ? extends TDirectionSearchAlgorithm,
                                                                            ? extends TOptimizationAttributes,
                                                                            ? extends TOptimizationResults>,
                                            TOptimizationResults>
                    implements Map<IUnconstrainedOptimizationAlgorithm<? extends TFunction,
                                                                        ? extends TLinearSearchAlgorithm,
                                                                        ? extends TDirectionSearchAlgorithm,
                                                                        ? extends TOptimizationAttributes,
                                                                        ? extends TOptimizationResults>,
                                            TOptimizationResults> {

    @Getter
    @Setter
    private TFunction optimizationFunction = null;

    public List<String> getAlgorithmNames() {
        List<String> result = new ArrayList<>(32);

        for(IUnconstrainedOptimizationAlgorithm<? extends TFunction,
                ? extends TLinearSearchAlgorithm,
                ? extends TDirectionSearchAlgorithm,
                ? extends TOptimizationAttributes,
                ? extends TOptimizationResults> alg: keySet()) {
            result.add(alg.getAlgorithmName());
        }

        return result;
    }
}
