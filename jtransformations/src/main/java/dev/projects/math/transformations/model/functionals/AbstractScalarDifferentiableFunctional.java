package dev.projects.math.transformations.model.functionals;

import dev.projects.math.datasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.differentiale.AbstractScalarDifferentiableFunction;
import dev.projects.math.transformations.model.IScalarDifferentiableModel;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractScalarDifferentiableFunctional
        extends AbstractScalarDifferentiableFunction {
    @Getter
    @Setter
    private OneDimensionalSupervisedDataSet dataSet;

    @Getter
    private IScalarDifferentiableModel model;

    public AbstractScalarDifferentiableFunctional(OneDimensionalSupervisedDataSet dataSet, IScalarDifferentiableModel model) throws LoggableException {
        super(model.getParameters().getSize());

        setModel(model);
        setDataSet(dataSet);
    }

    public void setModel(IScalarDifferentiableModel model) throws LoggableException {
        if (model == null) throw new LoggableException("Unacceptable model");

        this.model = model;
        this.setVariables(model.getParameters());
    }

    @Override
    public DenseVector getVariables() throws LoggableException {
        return model.getParameters().deepCopy();
    }

    @Override
    public void setVariables(DenseVector variables) throws LoggableException {
        super.setVariables(variables.deepCopy());
        model.setParameters(variables.deepCopy());

        deactivateOutputCache();
        deactivateGradientCache();
    }
}
