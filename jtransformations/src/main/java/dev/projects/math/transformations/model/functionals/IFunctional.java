package dev.projects.math.transformations.model.functionals;

import dev.projects.math.datasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.math.transformations.model.IScalarDifferentiableModel;
import dev.projects.utils.exception.LoggableException;

public interface IFunctional extends IScalarDifferentiableFunction {
    void setModel(IScalarDifferentiableModel model) throws LoggableException;
    IScalarDifferentiableModel getModel();

    OneDimensionalSupervisedDataSet getDataSet();
    void setDataSet(OneDimensionalSupervisedDataSet dataSet);
}
