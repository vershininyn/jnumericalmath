package dev.projects.math.linalgebra.entities;

import dev.projects.math.linalgebra.blocks.IDataBlock;
import dev.projects.math.linalgebra.storagestrategy.RegionDataBlockKey;
import dev.projects.utils.exception.LoggableException;

import java.util.Map;

public interface IDataEntity<TFluentResult> extends AutoCloseable {
    int getSize();

    int getRowCount();

    int getColumnCount();

    int getSwitchThreshold();

    double getEuclidNorm() throws LoggableException;

    double getMaxElement() throws LoggableException;

    TFluentResult multiplyByCoefficient(double coeff) throws LoggableException;

    TFluentResult setData(Map<RegionDataBlockKey, IDataBlock> data) throws LoggableException;

    Map<RegionDataBlockKey, IDataBlock> getData() throws LoggableException;
}
