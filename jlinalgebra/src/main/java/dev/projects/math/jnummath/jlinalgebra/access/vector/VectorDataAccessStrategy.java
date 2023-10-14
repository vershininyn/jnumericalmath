package dev.projects.math.jnummath.jlinalgebra.access.vector;

import dev.projects.math.jnummath.jlinalgebra.access.AbstractDataAccessStrategy;
import dev.projects.math.jnummath.jlinalgebra.access.DataAccessStrategyType;
import dev.projects.math.jnummath.jlinalgebra.access.IDataAccessStrategy;
import dev.projects.math.jnummath.jlinalgebra.storagestrategy.RegionDataBlockKey;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class VectorDataAccessStrategy
        extends AbstractDataAccessStrategy
        implements IDataAccessStrategy {

    public VectorDataAccessStrategy(int rowCount,
                                    int columnCount, DataAccessStrategyType type) {
        super(rowCount, columnCount, type);
    }

    @Override
    public int getIndex(int row, int column)   {
        return (getType() == DataAccessStrategyType.useColumnVectorDataAccess) ? (row) : (column);
    }

    @Override
    public List<RegionDataBlockKey> generateKeys(int maxBlockSize)   {
        return (getType() == DataAccessStrategyType.useColumnVectorDataAccess)
                ? (generateKeysByType(getRowCount(), maxBlockSize, this::generateRowTypeKeys))
                : (generateKeysByType(getColumnCount(), maxBlockSize, this::generateColumnTypeKeys));
    }

    @FunctionalInterface
    private interface IGenerateKeysOperator {
        List<RegionDataBlockKey> generate(final Stream<Map.Entry<Integer, Integer>> stream, final int maxBlockSize);
    }

    private List<RegionDataBlockKey> generateKeysByType(int count, int maxBlockSize, IGenerateKeysOperator operator) {
        int blockCount = count / maxBlockSize;

        final Stream<Map.Entry<Integer, Integer>> stream = IntStream.range(0, blockCount)
                .boxed()
                .map(blockIndex -> {
                    int key = blockIndex * maxBlockSize, value = Math.min((blockIndex + 1) * maxBlockSize, maxBlockSize);

                    return Map.entry(key, value);
                });

        return operator.generate(stream, maxBlockSize);
    }

    private List<RegionDataBlockKey> generateRowTypeKeys(final Stream<Map.Entry<Integer, Integer>> stream, final int maxBlockSize) {
        return stream.map(entry -> {
            int blockIndex = entry.getKey() / maxBlockSize;

            return new RegionDataBlockKey(blockIndex, entry.getKey(), entry.getValue(), 0, 1);
        }).collect(Collectors.toList());
    }

    private List<RegionDataBlockKey> generateColumnTypeKeys(final Stream<Map.Entry<Integer, Integer>> stream, final int maxBlockSize) {
        return stream.map(entry -> {
            int blockIndex = entry.getKey() / maxBlockSize;

            return new RegionDataBlockKey(blockIndex, 0, 1, entry.getKey(), entry.getValue());
        }).collect(Collectors.toList());
    }
}
