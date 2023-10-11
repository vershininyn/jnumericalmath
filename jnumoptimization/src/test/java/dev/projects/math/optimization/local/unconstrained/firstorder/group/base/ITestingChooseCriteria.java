package dev.projects.math.optimization.local.unconstrained.firstorder.group.base;

import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.utils.exception.LoggableException;

public interface ITestingChooseCriteria<TOptimizationResults extends ZeroOrderOptimizationResults> {
    TOptimizationResults compareByBest(TOptimizationResults optResults0,TOptimizationResults optResults1) throws LoggableException;
}
