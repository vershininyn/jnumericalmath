package dev.projects.math.linalgebra.entities;

import dev.projects.math.linalgebra.ICloneable;
import dev.projects.math.linalgebra.OrthogonalTransformationResultDto;
import dev.projects.math.linalgebra.blocks.IDataBlock;
import dev.projects.math.linalgebra.storagestrategy.RegionDataBlockKey;
import dev.projects.utils.exception.LoggableException;

public interface IMatrix extends ICloneable<IMatrix>, IDataEntity<IMatrix> {
    OrthogonalTransformationResultDto doOrthogonalTransformationAtPlace(IVector y, IVector sum) throws LoggableException;

    IVector getColumn(int column) throws LoggableException;

    IVector getRow(int row) throws LoggableException;

    double getMaxDiagonal();

    IMatrix setDiagonal(IVector vector) throws LoggableException;

    IMatrix setDiagonal(double[] data);

    IMatrix addDiagonal(double value);

    IMatrix multiplyRowByCoefficient(int row, double coeff) throws LoggableException;

    IMatrix multiplyColumnByCoefficient(int column, double coeff) throws LoggableException;

    IVector refreshableMultiply(double epsilon, double multiplicator, IVector vector) throws LoggableException;

    IVector multiplyByVector(IVector vector) throws LoggableException;

    IMatrix multiplyByMatrix(IMatrix matrix) throws LoggableException;

    IMatrix add(IMatrix matrix) throws LoggableException;

    IMatrix add(double coeff, IMatrix matrix) throws LoggableException;

    IMatrix substract(IMatrix matrix) throws LoggableException;

    IMatrix substract(double coeff, IMatrix matrix) throws LoggableException;

    IMatrix fillByRegion(IDataBlock block, RegionDataBlockKey region) throws LoggableException;
}
