package dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.group.base;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.utils.exception.LoggableException;

public interface ITestingChooseCriteria<TOptimizationResults extends ZeroOrderOptimizationResults> {
    TOptimizationResults compareByBest(TOptimizationResults optResults0,TOptimizationResults optResults1)  ;
}
