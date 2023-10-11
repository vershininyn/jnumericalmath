package dev.projects.math.optimization.local.unconstrained.firstorder.group.base;

import dev.projects.math.optimization.unconstrained.local.algorithms.IUnconstrainedOptimizationAlgorithm;
import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderLocalCriticalOptimizationAttributes;
import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.math.optimization.unconstrained.local.directionsearch.IDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.base.ILinearSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class ConcurrentFunctionsGroupTestingSlave<TFunction extends IScalarFunction,
                                                            TLinearSearchAlgorithm extends ILinearSearchAlgorithm<TFunction>,
                                                            TDirectionSearchAlgorithm extends IDirectionSearchAlgorithm<TFunction>,
                                                            TOptimizationAttributes extends ZeroOrderLocalCriticalOptimizationAttributes,
                                                            TOptimizationResults extends ZeroOrderOptimizationResults,
                                                            TFunctionsTestingGroupMap extends FunctionsTestingGroupMap<TFunction,
                                                                                                                TLinearSearchAlgorithm,
                                                                                                                TDirectionSearchAlgorithm,
                                                                                                                TOptimizationAttributes,
                                                                                                                TOptimizationResults>,
                                                            TOptimizationAlgorithm extends IUnconstrainedOptimizationAlgorithm<TFunction,
                                                                                                                                        TLinearSearchAlgorithm,
                                                                                                                                        TDirectionSearchAlgorithm,
                                                                                                                                        TOptimizationAttributes,
                                                                                                                                        TOptimizationResults>>
                        implements Callable<TFunctionsTestingGroupMap>
{

    @Getter
    @Setter
    private TFunction optimizationFunction;

    private final List<TOptimizationAlgorithm> optAlgorithmsGroup = new ArrayList<>(32);

    public ConcurrentFunctionsGroupTestingSlave(List<TOptimizationAlgorithm>  optAlgList, TFunction function) throws LoggableException {
        if ((optAlgList == null) || (optAlgList.isEmpty())) throw new LoggableException("Unacceptable alg''s list");
        if (function == null) throw new LoggableException("Unacceptable function");


        setOptimizationFunction(function);
        optAlgorithmsGroup.addAll(optAlgList);
    }

    @Override
    public TFunctionsTestingGroupMap call() throws Exception {
        TFunctionsTestingGroupMap resultMap = getFunctionsTestingGroupMapInstance();

        resultMap.setOptimizationFunction((TFunction)getOptimizationFunction().deepCopy());

        for(TOptimizationAlgorithm optAlgorithm: optAlgorithmsGroup) {

            optAlgorithm.setFunction((TFunction)getOptimizationFunction().deepCopy());
            TOptimizationResults optResult = optAlgorithm.minimize();
            resultMap.putIfAbsent(optAlgorithm, optResult);
        }

        return resultMap;
    }

    protected abstract TFunctionsTestingGroupMap getFunctionsTestingGroupMapInstance() throws LoggableException;
}
