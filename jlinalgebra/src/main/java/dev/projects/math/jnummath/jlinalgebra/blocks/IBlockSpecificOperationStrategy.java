package dev.projects.math.jnummath.jlinalgebra.blocks;

import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;

import java.nio.MappedByteBuffer;

@FunctionalInterface
public interface IBlockSpecificOperationStrategy<TResult> {
    TResult doOperation(final int size, final int position, final MappedByteBuffer dataBlock)  ;
}
