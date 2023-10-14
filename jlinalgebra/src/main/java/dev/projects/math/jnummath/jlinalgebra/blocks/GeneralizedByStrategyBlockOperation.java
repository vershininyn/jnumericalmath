package dev.projects.math.jnummath.jlinalgebra.blocks;

import dev.projects.math.jnummath.jlinalgebra.concurrent.IBlockOperation;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class GeneralizedByStrategyBlockOperation<TResult> implements IBlockOperation<TResult, BlockJobConfiguration> {
    private final int DOUBLE_OFFSET = 8;

    private IBlockSpecificOperationStrategy<TResult> blockSpecificOperationStrategy = null;

    public GeneralizedByStrategyBlockOperation(IBlockSpecificOperationStrategy<TResult> blockSpecificOperation) {
        blockSpecificOperationStrategy = blockSpecificOperation;
    }

    @Override
    public TResult execute(BlockJobConfiguration blockJobConfiguration) {
        int position = blockJobConfiguration.getPosition(), size = blockJobConfiguration.getSize();
        File workFile = blockJobConfiguration.getFile();

        TResult result = null;

        try (RandomAccessFile raFile = new RandomAccessFile(workFile, "rw");
             FileChannel channel = raFile.getChannel()) {

            MappedByteBuffer mbb = channel.map(FileChannel.MapMode.READ_WRITE, DOUBLE_OFFSET * position, DOUBLE_OFFSET * size);

            FileLock fileLock = channel.lock(DOUBLE_OFFSET * position, DOUBLE_OFFSET * size, true);

            while (fileLock == null || !fileLock.isValid()) {
                fileLock = channel.lock(DOUBLE_OFFSET * position, DOUBLE_OFFSET * size, true);
            }

            result = blockSpecificOperationStrategy.doOperation(size, position, mbb);

            fileLock.release();
        } catch (IOException | LoggableException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
