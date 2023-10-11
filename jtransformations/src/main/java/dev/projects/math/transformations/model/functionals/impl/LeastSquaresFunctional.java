package dev.projects.math.transformations.model.functionals.impl;

import dev.projects.math.datasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.model.IScalarDifferentiableModel;
import dev.projects.math.transformations.model.functionals.AbstractScalarDifferentiableFunctional;
import dev.projects.math.transformations.model.functionals.IFunctional;
import dev.projects.utils.exception.LoggableException;

import java.util.List;
import java.util.Map;

public class LeastSquaresFunctional
        extends AbstractScalarDifferentiableFunctional
        implements IFunctional {

    public LeastSquaresFunctional(OneDimensionalSupervisedDataSet dataSet, IScalarDifferentiableModel model) throws LoggableException {
        super(dataSet, model);
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
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
    protected DenseVector actualComputeGradient() throws LoggableException {
        IScalarDifferentiableModel model = getModel();
        OneDimensionalSupervisedDataSet data = getDataSet();

        DenseVector functionalGrad = DenseVector.getInstance(getVariablesCount(),0.0),
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
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        return new LeastSquaresFunctional(getDataSet(),(IScalarDifferentiableModel) getModel().deepCopy());
    }
}
