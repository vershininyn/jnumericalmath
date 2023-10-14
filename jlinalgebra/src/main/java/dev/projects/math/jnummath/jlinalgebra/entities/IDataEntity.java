package dev.projects.math.jnummath.jlinalgebra.entities;

import dev.projects.math.jnummath.jlinalgebra.blocks.IDataBlock;
import dev.projects.math.jnummath.jlinalgebra.storagestrategy.RegionDataBlockKey;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;

import java.util.Map;

public interface IDataEntity<TFluentResult> extends AutoCloseable {
    int getSize();

    int getRowCount();

    int getColumnCount();

    int getSwitchThreshold();

    double getEuclidNorm()  ;

    double getMaxElement()  ;

    TFluentResult multiplyByCoefficient(double coeff)  ;

    TFluentResult setData(Map<RegionDataBlockKey, IDataBlock> data)  ;

    Map<RegionDataBlockKey, IDataBlock> getData()  ;
}
