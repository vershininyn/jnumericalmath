package dev.projects.jnumericalmath.jlinearalgebra.doubledatablocks;

import dev.projects.math.linalgebra.blocks.IDataBlock;
import dev.projects.math.linalgebra.blocks.mappeddoublebuffer.MappedDoubleDataBlock;
import dev.projects.math.linalgebra.blocks.memorydoublebuffer.MemoryDoubleDataBlock;
import dev.projects.utils.exception.LoggableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

public class DataBlocksTests {

    public static class DataBlocksArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of(MappedDoubleDataBlock.class, 1_000_000, 0.0),
                    Arguments.of(MemoryDoubleDataBlock.class, 1024, 0.0)
            );
        }
    }


    @ParameterizedTest
    @ArgumentsSource(DataBlocksArgumentsProvider.class)
    public void mistakenSizeDataBlockTest(final Class<IDataBlock> clazz, final int dimension, final double fillValue) throws Exception {
        Assertions.assertThrows(InvocationTargetException.class, () -> {
            try(IDataBlock block = clazz.getConstructor(int.class, double.class).newInstance(-1, fillValue)) {

            }
        });
    }

    @ParameterizedTest
    @ArgumentsSource(DataBlocksArgumentsProvider.class)
    public void getSizeDataBlockTest(final Class<IDataBlock> clazz, final int dimension, final double fillValue) throws Exception {
        try (IDataBlock block = clazz.getConstructor(int.class, double.class).newInstance(dimension, fillValue)) {
            Assertions.assertEquals(dimension, block.getSize());
        }
    }

    @ParameterizedTest
    @ArgumentsSource(DataBlocksArgumentsProvider.class)
    public void fillByValueDataBlockTest(final Class<IDataBlock> clazz, final int dimension, final double fillValue) throws Exception {
        try (IDataBlock block = clazz.getConstructor(int.class, double.class).newInstance(dimension, fillValue)) {
            verifyDataBlockByEqualsToSomeValue(block, dimension, fillValue);
        }
    }

    @ParameterizedTest
    @ArgumentsSource(DataBlocksArgumentsProvider.class)
    public void fillByDataBlockTest(final Class<IDataBlock> clazz, final int dimension, final double fillValue) throws Exception {
        try (IDataBlock blockSource = clazz.getConstructor(int.class, double.class).newInstance(dimension, fillValue);
             IDataBlock blockConsumer = clazz.getConstructor(int.class, double.class).newInstance(dimension, 0.0);
             IDataBlock result = blockConsumer.fillByData(blockSource)) {

            verifyDataBlockByEqualsToSomeValue(result, dimension, fillValue);
        }
    }

    @ParameterizedTest
    @ArgumentsSource(DataBlocksArgumentsProvider.class)
    public void readAndWriteValueDoubleBlockTest(final Class<IDataBlock> clazz, final int dimension, final double fillValue) throws Exception {
        try (IDataBlock block = clazz.getConstructor(int.class, double.class).newInstance(dimension, fillValue + 1.0)) {
            block.writeValue(0, fillValue);

            Assertions.assertEquals(fillValue, block.readValue(0));
        }
    }

    @ParameterizedTest
    @ArgumentsSource(DataBlocksArgumentsProvider.class)
    public void multiplyByCoefficientDataBlockTest(final Class<IDataBlock> clazz, final int dimension, final double fillValue) throws Exception {
        try (IDataBlock block = clazz.getConstructor(int.class, double.class).newInstance(dimension, fillValue);
             IDataBlock result = block.multiplyByCoefficient(2.0)) {

            verifyDataBlockByEqualsToSomeValue(result, dimension, 2.0*fillValue);
        }
    }

    @ParameterizedTest
    @ArgumentsSource(DataBlocksArgumentsProvider.class)
    public void multiplyByVectorDataBlockTest(final Class<IDataBlock> clazz, final int dimension, final double fillValue) throws Exception {
        try (IDataBlock block0 = clazz.getConstructor(int.class, double.class).newInstance(dimension, fillValue);
             IDataBlock block1 = clazz.getConstructor(int.class, double.class).newInstance(dimension, fillValue);
             IDataBlock result = block0.multiplyByVector(block1)) {

            //TODO: stream API????

            verifyDataBlockByEqualsToSomeValue(result, dimension, fillValue*fillValue);
        }
    }

    @ParameterizedTest
    @ArgumentsSource(DataBlocksArgumentsProvider.class)
    public void getMaxElementDataBlockTest(final Class<IDataBlock> clazz, final int dimension, final double fillValue) throws Exception {
        try (IDataBlock block = clazz.getConstructor(int.class, double.class).newInstance(dimension, fillValue)) {
            Assertions.assertEquals(fillValue, block.getMaxElement());
        }
    }

    @ParameterizedTest
    @ArgumentsSource(DataBlocksArgumentsProvider.class)
    public void getInnerProductDataBlockTest(final Class<IDataBlock> clazz, final int dimension, final double fillValue) throws Exception {
        try (IDataBlock firstBlock = clazz.getConstructor(int.class, double.class).newInstance(dimension, fillValue);
             IDataBlock secondBlock = clazz.getConstructor(int.class, double.class).newInstance(dimension, fillValue)) {

            Assertions.assertEquals(dimension*fillValue*fillValue, firstBlock.getInnerProduct(secondBlock));
        }
    }

    @ParameterizedTest
    @ArgumentsSource(DataBlocksArgumentsProvider.class)
    public void shiftByCoefficientLessThenZeroDataBlockTest(final Class<IDataBlock> clazz, final int dimension, final double fillValue) throws Exception {
        try (IDataBlock block = clazz.getConstructor(int.class, double.class).newInstance(dimension, -1.0);
             IDataBlock result = block.shiftByCoefficient(0.0)) {

            verifyDataBlockByEqualsToSomeValue(result, dimension, -1.0);
        }
    }

    @ParameterizedTest
    @ArgumentsSource(DataBlocksArgumentsProvider.class)
    public void shiftByCoefficientMoreThenZeroMappedDataBlockTest(final Class<IDataBlock> clazz, final int dimension, final double fillValue) throws Exception {
        try (IDataBlock block = clazz.getConstructor(int.class, double.class).newInstance(dimension, 1.0);
             IDataBlock result = block.shiftByCoefficient(0.0)) {

            verifyDataBlockByEqualsToSomeValue(result, dimension, 1.0);
        }
    }

    private void verifyDataBlockByEqualsToSomeValue(IDataBlock block,
                                                    int dimension,
                                                    double expectedValue) throws LoggableException {
        for (int index = 0; index < dimension; index++) {
            Assertions.assertEquals(expectedValue, block.readValue(index));
        }
    }
}
