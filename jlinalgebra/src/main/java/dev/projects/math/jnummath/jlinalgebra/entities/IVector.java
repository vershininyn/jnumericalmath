package dev.projects.math.jnummath.jlinalgebra.entities;

import dev.projects.math.jnummath.jkernel.ICloneable;
import dev.projects.math.jnummath.jlinalgebra.VectorType;
import dev.projects.math.jnummath.jlinalgebra.access.DataAccessStrategyType;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;

public interface IVector extends ICloneable<IVector>, IDataEntity<IVector> {
    VectorType getVectorType();

    IVector shiftByCoefficient(double coeff)  ;

    IMatrix multiplyByVector(IVector vector)  ;

    IVector transposeInPlace();

    IVector add(IVector vector)  ;

    IVector add(double coeff, IVector vector)  ;

    IVector substract(IVector vector)  ;

    IVector substract(double coeff, IVector vector)  ;

    double getInnerProduct(IVector vector)  ;

    IVector convertToColumnVector()  ;

    IVector convertToRowVector()  ;

    IVector extendByVector(IVector vector)  ;

    IVector extendByDouble(double value)  ;

    IVector extendByArray(double[] array)  ;

    IVector[] splitMiddleToLeftAtIndex(int index)  ;

    IVector[] splitMiddleToRigthAtIndex(int index)  ;

    IVector[] splitAtIndex(int index, boolean middleToLeft)  ;

    IVector setValue(int index, double value)  ;

    double getValue(int index)  ;

    DataAccessStrategyType getAccessStrategyType();
}
