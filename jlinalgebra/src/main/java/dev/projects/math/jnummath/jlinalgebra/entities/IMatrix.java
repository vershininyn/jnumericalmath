package dev.projects.math.jnummath.jlinalgebra.entities;

import dev.projects.math.jnummath.jkernel.ICloneable;
import dev.projects.math.jnummath.jlinalgebra.blocks.IDataBlock;
import dev.projects.math.jnummath.jlinalgebra.OrthogonalTransformationResultDto;
import dev.projects.math.jnummath.jlinalgebra.storagestrategy.RegionDataBlockKey;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;

public interface IMatrix extends ICloneable<IMatrix>, IDataEntity<IMatrix> {
    OrthogonalTransformationResultDto doOrthogonalTransformationAtPlace(IVector y, IVector sum)  ;

    IVector getColumn(int column)  ;

    IVector getRow(int row)  ;

    double getMaxDiagonal();

    IMatrix setDiagonal(IVector vector)  ;

    IMatrix setDiagonal(double[] data);

    IMatrix addDiagonal(double value);

    IMatrix multiplyRowByCoefficient(int row, double coeff)  ;

    IMatrix multiplyColumnByCoefficient(int column, double coeff)  ;

    IVector refreshableMultiply(double epsilon, double multiplicator, IVector vector)  ;

    IVector multiplyByVector(IVector vector)  ;

    IMatrix multiplyByMatrix(IMatrix matrix)  ;

    IMatrix add(IMatrix matrix)  ;

    IMatrix add(double coeff, IMatrix matrix)  ;

    IMatrix substract(IMatrix matrix)  ;

    IMatrix substract(double coeff, IMatrix matrix)  ;

    IMatrix fillByRegion(IDataBlock block, RegionDataBlockKey region)  ;
}
