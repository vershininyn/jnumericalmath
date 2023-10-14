package dev.projects.math.jnummath.jlinalgebra.blocks.mappeddoublebuffer;

import dev.projects.math.jnummath.jlinalgebra.blocks.BlockJobConfiguration;
import dev.projects.math.jnummath.jlinalgebra.blocks.GeneralizedByStrategyBlockOperation;
import dev.projects.math.jnummath.jlinalgebra.blocks.IBlockSpecificOperationStrategy;
import dev.projects.math.jnummath.jlinalgebra.blocks.IDataBlock;
import dev.projects.math.jnummath.jlinalgebra.concurrent.BlockCallableJob;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;
import org.agrona.IoUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class MappedDoubleDataBlock implements IDataBlock {
    private String filePath = System.getProperty("user.dir") + File.separator + UUID.randomUUID() + ".bin";

    private int size = 0;

    private final int DOUBLE_OFFSET = 8;

    private int threadCount = Runtime.getRuntime().availableProcessors();
    private File storageFile = null;
    private RandomAccessFile rndAccessFile = null;

    private MappedByteBuffer doubleBuffer = null;

    private ThreadPoolExecutor executor = (ThreadPoolExecutor) (Executors.newFixedThreadPool(threadCount));

    public MappedDoubleDataBlock(int size)   {
        if (size < 1) throw new LoggableException("Unacceptable size");

        this.size = size;

        storageFile = Path.of(filePath).toFile();

        try {
            if (!storageFile.exists()) {
                storageFile.createNewFile();
            }

            rndAccessFile = new RandomAccessFile(storageFile, "rw");
            FileChannel fileChannel = rndAccessFile.getChannel();
            doubleBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, DOUBLE_OFFSET * size);
        } catch (IOException e) {
            throw new LoggableException(e);
        }
    }

    public MappedDoubleDataBlock(int size, double value)   {
        this(size);

        fillByValue(value);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public IDataBlock fillByValue(double value)   {
        Map<BlockJobConfiguration, GeneralizedByStrategyBlockOperation<Void>> jobsMap = createBlocksJobMap((size, position, block) -> {
            for (int index = 0; index < size; index++) {
                block.putDouble(DOUBLE_OFFSET * index, value);
            }

            return null;
        });

        doBlocksJob(jobsMap);

        return this;
    }

    @Override
    public IDataBlock fillByData(final IDataBlock data)   {
        if ((data == null)
                || (getSize() != data.getSize())) throw new LoggableException("Unacceptable the data block");

        Map<BlockJobConfiguration, GeneralizedByStrategyBlockOperation<Void>> jobsMap = createBlocksJobMap((size, position, block) -> {
            for (int blockIndex = 0; blockIndex < size; blockIndex++) {
                block.putDouble(DOUBLE_OFFSET * blockIndex, data.readValue(position + blockIndex));
            }

            return null;
        });

        doBlocksJob(jobsMap);

        return this;
    }

    @Override
    public double readValue(int index)   {
        if ((index < 0) || (index > (getSize() - 1))) throw new LoggableException("Unacceptable index");

        //TODO: search for some other data structures

        return doubleBuffer.getDouble(DOUBLE_OFFSET * index);
    }

    @Override
    public IDataBlock writeValue(int index, double value)   {
        if ((index < 0) || (index > (getSize() - 1))) throw new LoggableException("Unacceptable index");

        //TODO: search for some other data structures

        doubleBuffer.putDouble(DOUBLE_OFFSET * index, value);

        return this;
    }

    @Override
    public double getMaxElement()   {
        Map<BlockJobConfiguration, GeneralizedByStrategyBlockOperation<Double>> jobsMap = createBlocksJobMap((size, position, block) -> {
            double maxValue = Double.NEGATIVE_INFINITY;

            for (int index = 0; index < size; index++) {
                double value = block.getDouble(DOUBLE_OFFSET * index);

                maxValue = Math.max(maxValue, value);
            }

            return maxValue;
        });

        return doBlocksJob(jobsMap).stream().mapToDouble(Double::doubleValue).max().getAsDouble();
    }

    @Override
    public IDataBlock multiplyByCoefficient(double coeff)   {
        Map<BlockJobConfiguration, GeneralizedByStrategyBlockOperation<Void>> jobsMap = createBlocksJobMap((size, position, block) -> {
            for (int index = 0; index < size; index++) {
                double value = block.getDouble(DOUBLE_OFFSET * index);

                block.putDouble(DOUBLE_OFFSET * index, coeff * value);
            }

            return null;
        });

        doBlocksJob(jobsMap);

        return this;
    }

    @Override
    public IDataBlock multiplyByVector(final IDataBlock data)   {
        int size = getSize();

        if ((data == null)
                || (size != data.getSize())) throw new LoggableException("Unacceptable the data block: size is diff");

        final IDataBlock result = new MappedDoubleDataBlock(size);

        Map<BlockJobConfiguration, GeneralizedByStrategyBlockOperation<Void>> jobsMap = createBlocksJobMap((blockSize, position, block) -> {
            for (int blockIndex = 0; blockIndex < blockSize; blockIndex++) {
                double value = block.getDouble(DOUBLE_OFFSET * blockIndex) * data.readValue(position + blockIndex);

                result.writeValue(position + blockIndex, value);
            }

            return null;
        });

        doBlocksJob(jobsMap);

        return result;
    }

    @Override
    public double getInnerProduct(final IDataBlock block)   {
        if ((block == null) || (block.getSize() != getSize())) throw new LoggableException("Unacceptable block");

        Map<BlockJobConfiguration, GeneralizedByStrategyBlockOperation<Double>> jobsMap = createBlocksJobMap((size, position, currentBlock) -> {
            double product = 0.0;

            for (int index = 0; index < size; index++) {
                product += currentBlock.getDouble(DOUBLE_OFFSET * index) * block.readValue(position + index);
            }

            return product;
        });

        return doBlocksJob(jobsMap).stream().mapToDouble(Double::doubleValue).sum();
    }

    @Override
    public IDataBlock shiftByCoefficient(double coeff)   {
        Map<BlockJobConfiguration, GeneralizedByStrategyBlockOperation<Void>> jobsMap = createBlocksJobMap((size, position, block) -> {
            for (int index = 0; index < size; index++) {
                double value = block.getDouble(DOUBLE_OFFSET * index);

                value = (value < 0.0) ? (value - coeff) : (value + coeff);

                block.putDouble(DOUBLE_OFFSET * index, value);
            }

            return null;
        });

        doBlocksJob(jobsMap);

        return this;
    }

    @Override
    public void close() throws Exception {
        if (doubleBuffer != null) {
            IoUtil.unmap(doubleBuffer);
        }

        if (rndAccessFile != null) {
            rndAccessFile.close();
        }

        if ((storageFile != null) && (storageFile.exists())) {
            storageFile.delete();
        }

        if (executor != null) {
            executor.shutdown();
        }
    }

    private <TCallableResult> Map<BlockJobConfiguration, GeneralizedByStrategyBlockOperation<TCallableResult>> createBlocksJobMap(IBlockSpecificOperationStrategy blockSpecificOperation) {
        Map<BlockJobConfiguration, GeneralizedByStrategyBlockOperation<TCallableResult>> blockJobsMap = new HashMap<>();

        int blockSize = getSize() / threadCount;

        if (blockSize > 0) {
            for (int blockIndex = 0; blockIndex < (threadCount - 1); blockIndex++) {
                blockJobsMap.put(new BlockJobConfiguration(blockIndex * blockSize, blockSize, storageFile),
                        new GeneralizedByStrategyBlockOperation(blockSpecificOperation));
            }
        }

        blockJobsMap.put(new BlockJobConfiguration((threadCount - 1) * blockSize, getSize() - (threadCount - 1) * blockSize, storageFile),
                new GeneralizedByStrategyBlockOperation(blockSpecificOperation));

        return blockJobsMap;
    }

    private <TCallableResult> List<TCallableResult> doBlocksJob(Map<BlockJobConfiguration,
            GeneralizedByStrategyBlockOperation<TCallableResult>> jobsMap)   {
        if ((jobsMap == null) || (jobsMap.isEmpty())) throw new LoggableException("Unacceptable jobs cfg");

        List<TCallableResult> results = new ArrayList<>();

        final CountDownLatch endProcessingSignal = new CountDownLatch(jobsMap.size()),
                startProcessingSignal = new CountDownLatch(1);

        List<Future<TCallableResult>> submitedJobs = new ArrayList<>();

        for (BlockJobConfiguration jobCfg : jobsMap.keySet()) {
            GeneralizedByStrategyBlockOperation<TCallableResult> operation = jobsMap.get(jobCfg);

            BlockCallableJob<TCallableResult, BlockJobConfiguration> callableBlockJob = new BlockCallableJob(jobCfg,
                    operation,
                    startProcessingSignal,
                    endProcessingSignal);

            submitedJobs.add(executor.submit(callableBlockJob));
        }

        startProcessingSignal.countDown();

        try {
            for (Future<TCallableResult> job : submitedJobs) {
                results.add(job.get());
            }

            endProcessingSignal.await();
        } catch (InterruptedException | ExecutionException e) {
            throw new LoggableException(e);
        }

        return results;
    }
}
