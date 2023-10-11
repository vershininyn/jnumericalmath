package dev.projects.math.linalgebra.concurrent;

public interface IBlockOperation<TCallableResult, TJobConfiguration> {
    TCallableResult execute(TJobConfiguration configuration);
}
