package dev.projects.math.linalgebra.entities;

import dev.projects.math.linalgebra.ICloneable;
import dev.projects.math.linalgebra.VectorType;
import dev.projects.math.linalgebra.access.DataAccessStrategyType;
import dev.projects.utils.exception.LoggableException;

public interface IVector extends ICloneable<IVector>, IDataEntity<IVector> {
    VectorType getVectorType();

    IVector shiftByCoefficient(double coeff) throws LoggableException;

    IMatrix multiplyByVector(IVector vector) throws LoggableException;

    IVector transposeInPlace();

    IVector add(IVector vector) throws LoggableException;

    IVector add(double coeff, IVector vector) throws LoggableException;

    IVector substract(IVector vector) throws LoggableException;

    IVector substract(double coeff, IVector vector) throws LoggableException;

    double getInnerProduct(IVector vector) throws LoggableException;

    IVector convertToColumnVector() throws LoggableException;

    IVector convertToRowVector() throws LoggableException;

    IVector extendByVector(IVector vector) throws LoggableException;

    IVector extendByDouble(double value) throws LoggableException;

    IVector extendByArray(double[] array) throws LoggableException;

    IVector[] splitMiddleToLeftAtIndex(int index) throws LoggableException;

    IVector[] splitMiddleToRigthAtIndex(int index) throws LoggableException;

    IVector[] splitAtIndex(int index, boolean middleToLeft) throws LoggableException;

    IVector setValue(int index, double value) throws LoggableException;

    double getValue(int index) throws LoggableException;

    DataAccessStrategyType getAccessStrategyType();
}
