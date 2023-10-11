package dev.projects.math.linalgebra.blocks;


import dev.projects.utils.exception.LoggableException;

public interface IDataBlock extends AutoCloseable {
    int getSize();

    IDataBlock fillByValue(double value) throws LoggableException;

    IDataBlock fillByData(IDataBlock block) throws LoggableException;

    double readValue(int index) throws LoggableException;

    IDataBlock writeValue(int index, double value) throws LoggableException;

    double getMaxElement() throws LoggableException;

    IDataBlock multiplyByCoefficient(double coeff) throws LoggableException;

    IDataBlock multiplyByVector(final IDataBlock data) throws LoggableException;

    double getInnerProduct(final IDataBlock block) throws LoggableException;

    IDataBlock shiftByCoefficient(double coeff) throws LoggableException;
}
