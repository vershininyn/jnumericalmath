package dev.projects.math.jnummath.jtransformations.model.functionals.impl;

import dev.projects.math.jnummath.jdatasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.model.IScalarDifferentiableModel;
import dev.projects.math.jnummath.jtransformations.model.functionals.IFunctional;
import dev.projects.math.jnummath.jtransformations.model.functionals.AbstractScalarDifferentiableFunctional;
import dev.projects.utils.exception.LoggableException;

import java.util.List;
import java.util.Map;

public class LeastSquaresFunctional
        extends AbstractScalarDifferentiableFunctional
        implements IFunctional {

    public LeastSquaresFunctional(OneDimensionalSupervisedDataSet dataSet, IScalarDifferentiableModel model)   {
        super(dataSet, model);
    }

    @Override
    protected Double actualComputeOutput()   {
        IScalarDifferentiableModel model = getModel();

        OneDimensionalSupervisedDataSet data = getDataSet();
        DenseVector parameters = getVariables();

        double out = 0.0;

        for (Map.Entry<List<Double>,Double> dataRow: data.entrySet()) {
            out += Math.pow((dataRow.getValue()
                    - model.computeOutput(DenseVector.getInstance(dataRow.getKey()), parameters)), 2.0);
        }

        return out/((double) data.size() - 1.0);
    }

    @Override
    public String getName() {
        return "(1/(N-1))*sum{(y[k]* - F(x[k],w))^2,k=1,...,N)}";
    }

    @Override
    protected DenseVector actualComputeGradient()   {
        IScalarDifferentiableModel model = getModel();
        OneDimensionalSupervisedDataSet data = getDataSet();

        DenseVector functionalGrad = DenseVector.getInstance(getInputDimensionSize(),0.0),
                parameters = getVariables();

        int paramCount = model.getParametersCount();

        for (Map.Entry<List<Double>, Double> dataRow: data.entrySet()) {
            DenseVector variables = DenseVector.getInstance(dataRow.getKey()),
                    modelGrad = model.computeGradient(variables, parameters);

            for(int paramIndex = 0; paramIndex < paramCount; paramIndex++) {
                double value = 2.0*(model.computeOutput(variables, parameters)
                        - dataRow.getValue())*modelGrad.getValue(paramIndex);

                functionalGrad.setValue(paramIndex,functionalGrad.getValue(paramIndex) + value);
            }
        }

        return functionalGrad;
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
        return new LeastSquaresFunctional(getDataSet(),(IScalarDifferentiableModel) getModel().deepCopy());
    }
}
