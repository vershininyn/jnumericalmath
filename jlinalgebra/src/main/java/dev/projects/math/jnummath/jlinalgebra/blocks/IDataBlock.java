package dev.projects.math.jnummath.jlinalgebra.blocks;


import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;

public interface IDataBlock extends AutoCloseable {
    int getSize();

    IDataBlock fillByValue(double value)  ;

    IDataBlock fillByData(IDataBlock block)  ;

    double readValue(int index)  ;

    IDataBlock writeValue(int index, double value)  ;

    double getMaxElement()  ;

    IDataBlock multiplyByCoefficient(double coeff)  ;

    IDataBlock multiplyByVector(final IDataBlock data)  ;

    double getInnerProduct(final IDataBlock block)  ;

    IDataBlock shiftByCoefficient(double coeff)  ;
}
