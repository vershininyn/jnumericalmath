package dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.group.base;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderLocalCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.IUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.IDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.base.ILinearSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class ConcurrentFunctionsGroupTestingMaster<TFunction extends IScalarFunction,
                                                    TLinearSearchAlgorithm extends ILinearSearchAlgorithm<TFunction>,
                                                    TDirectionSearchAlgorithm extends IDirectionSearchAlgorithm<TFunction>,
                                                    TOptimizationAttributes extends ZeroOrderLocalCriticalOptimizationAttributes,
                                                    TOptimizationResults extends ZeroOrderOptimizationResults,
                                                    TOptimizationAlgorithm extends IUnconstrainedOptimizationAlgorithm<TFunction,
                                                                                        TLinearSearchAlgorithm,
                                                                                        TDirectionSearchAlgorithm,
                                                                                        TOptimizationAttributes,
                                                                                        TOptimizationResults>,
                                                    TFunctionsTestingGroupMap extends FunctionsTestingGroupMap<TFunction,
                                                                                        TLinearSearchAlgorithm,
                                                                                        TDirectionSearchAlgorithm,
                                                                                        TOptimizationAttributes,
                                                                                        TOptimizationResults>,
                                                    TConcurrentFunctionsGroupTestingSlave extends ConcurrentFunctionsGroupTestingSlave<TFunction,
                                                                                                                                        TLinearSearchAlgorithm,
                                                                                                                                        TDirectionSearchAlgorithm,
                                                                                                                                        TOptimizationAttributes,
                                                                                                                                        TOptimizationResults,
                                                                                                                                        TFunctionsTestingGroupMap,
                                                                                                                                        TOptimizationAlgorithm>>
{

    private final ExecutorService threadPool;
    private final List<TFunction> functionsGroup = new ArrayList<>(32);
    private final List<TOptimizationAlgorithm> algorithmsGroup = new ArrayList<>(32);

    public ConcurrentFunctionsGroupTestingMaster(List<TFunction> funGroup, List<TOptimizationAlgorithm> algGroup)   {
        threadPool = Executors.newFixedThreadPool(funGroup.size());

        functionsGroup.addAll(funGroup);
        algorithmsGroup.addAll(algGroup);
    }

    public List<TFunctionsTestingGroupMap> performTestingAtAlgorithmsGroup()   {
        List<TFunctionsTestingGroupMap> results = new ArrayList<>(32);

        List<Future<TFunctionsTestingGroupMap>> futureList = new ArrayList<>(32);

        for(TFunction fun: functionsGroup) {
            List<TOptimizationAlgorithm> optGroup = cloneAlgorithmsGroup(algorithmsGroup);
            TConcurrentFunctionsGroupTestingSlave slave = getInstanceOfSlave(optGroup,(TFunction)fun.deepCopy());
            futureList.add(threadPool.submit(slave));
        }

        try {
            for(Future<TFunctionsTestingGroupMap> future: futureList) {
                results.add(future.get());
            }
        } catch (InterruptedException | ExecutionException ex) {
            throw new LoggableException(ex);
        }

        threadPool.shutdown();

        return results;
    }

    protected abstract TConcurrentFunctionsGroupTestingSlave getInstanceOfSlave(List<TOptimizationAlgorithm> optAlgorithmsGroup, TFunction function)  ;

    private List<TOptimizationAlgorithm> cloneAlgorithmsGroup(List<TOptimizationAlgorithm> group)   {
        List<TOptimizationAlgorithm> newGroup = new ArrayList<>(32);

        for(TOptimizationAlgorithm optAlg: group) {
            newGroup.add((TOptimizationAlgorithm) optAlg.deepCopy());
        }

        return newGroup;
    }
}
