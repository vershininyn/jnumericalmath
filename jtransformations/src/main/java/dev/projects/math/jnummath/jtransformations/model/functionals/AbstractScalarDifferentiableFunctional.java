package dev.projects.math.jnummath.jtransformations.model.functionals;

import dev.projects.math.jnummath.jdatasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.AbstractScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.model.IScalarDifferentiableModel;
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

    public AbstractScalarDifferentiableFunctional(OneDimensionalSupervisedDataSet dataSet, IScalarDifferentiableModel model)   {
        super(model.getParameters().getSize());

        setModel(model);
        setDataSet(dataSet);
    }

    public void setModel(IScalarDifferentiableModel model)   {
        if (model == null) throw new LoggableException("Unacceptable model");

        this.model = model;
        this.setVariables(model.getParameters());
    }

    @Override
    public DenseVector getVariables()   {
        return model.getParameters().deepCopy();
    }

    @Override
    public void setVariables(DenseVector variables)   {
        super.setVariables(variables.deepCopy());
        model.setParameters(variables.deepCopy());

        deactivateOutputCache();
        deactivateGradientCache();
    }
}
