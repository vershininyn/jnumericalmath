package dev.projects.math.linalgebra.blocks;

import dev.projects.utils.exception.LoggableException;

import java.nio.MappedByteBuffer;

@FunctionalInterface
public interface IBlockSpecificOperationStrategy<TResult> {
    TResult doOperation(final int size, final int position, final MappedByteBuffer dataBlock) throws LoggableException;
}
