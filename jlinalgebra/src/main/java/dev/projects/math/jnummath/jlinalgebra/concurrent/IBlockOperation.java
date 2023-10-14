package dev.projects.math.jnummath.jlinalgebra.concurrent;

public interface IBlockOperation<TCallableResult, TJobConfiguration> {
    TCallableResult execute(TJobConfiguration configuration);
}
