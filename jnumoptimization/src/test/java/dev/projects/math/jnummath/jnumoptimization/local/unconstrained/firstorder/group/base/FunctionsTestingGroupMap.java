package dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.group.base;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderLocalCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.IUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.IDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.base.ILinearSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;
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
