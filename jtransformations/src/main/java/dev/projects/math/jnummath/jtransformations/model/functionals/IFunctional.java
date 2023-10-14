package dev.projects.math.jnummath.jtransformations.model.functionals;

import dev.projects.math.jnummath.jdatasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.model.IScalarDifferentiableModel;
import dev.projects.utils.exception.LoggableException;

public interface IFunctional extends IScalarDifferentiableFunction {
    void setModel(IScalarDifferentiableModel model)  ;
    IScalarDifferentiableModel getModel();

    OneDimensionalSupervisedDataSet getDataSet();
    void setDataSet(OneDimensionalSupervisedDataSet dataSet);
}
