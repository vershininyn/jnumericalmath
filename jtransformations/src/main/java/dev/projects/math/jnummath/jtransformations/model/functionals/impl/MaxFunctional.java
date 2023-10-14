package dev.projects.math.jnummath.jtransformations.model.functionals.impl;

import dev.projects.math.jnummath.jdatasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.model.IScalarDifferentiableModel;
import dev.projects.math.jnummath.jtransformations.model.functionals.IFunctional;
import dev.projects.math.jnummath.jtransformations.model.functionals.AbstractScalarDifferentiableFunctional;
import dev.projects.utils.exception.LoggableException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MaxFunctional
        extends AbstractScalarDifferentiableFunctional
        implements IFunctional {

    private final Map.Entry<List<Double>, Double> zeroEntry;

    public MaxFunctional(OneDimensionalSupervisedDataSet dataSet, IScalarDifferentiableModel model)   {
        super(dataSet, model);

        zeroEntry = new ZeroEntry(getInputDimensionSize());
    }

    @Override
    protected Double actualComputeOutput()   {
        Map.Entry<List<Double>, Double> maxEntry = getMaxEntry(getDataSet());

        return Math.abs(maxEntry.getValue() - getModel().computeOutput(DenseVector.getInstance(maxEntry.getKey()), getVariables()));
    }

    @Override
    protected DenseVector actualComputeGradient()   {
        IScalarDifferentiableModel model = getModel();
        Map.Entry<List<Double>, Double> maxEntry = getMaxEntry(getDataSet());

        int parametersCount = model.getParametersCount();

        DenseVector gradient = DenseVector.getInstance(parametersCount, 0.0),
                variables = DenseVector.getInstance(maxEntry.getKey()),
                modelGradient = model.computeGradient(variables, getVariables());

        double delta = maxEntry.getValue() - model.computeOutput(variables, getVariables());

        for(int parameterIndex = 0; parameterIndex < parametersCount; parameterIndex++) {
            gradient.setValue(parameterIndex, (-1.0)*Math.signum(delta)*modelGradient.getValue(parameterIndex));
        }

        return gradient;
    }

    @Override
    public String getName() {
        return "max{abs(y[k] - F(x[k],w),k=1,...,N)}";
    }

    private Map.Entry<List<Double>, Double> getMaxEntry(OneDimensionalSupervisedDataSet dataSet)   {
        IScalarDifferentiableModel model = getModel();

        double max = Double.NEGATIVE_INFINITY;

        Map.Entry<List<Double>, Double> maxEntry = zeroEntry;

        for(Map.Entry<List<Double>, Double> dataRow: dataSet.entrySet()) {
            double value = Math.abs(dataRow.getValue()
                    - model.computeOutput(DenseVector.getInstance(dataRow.getKey()), getVariables()));

            if (value > max) {
                maxEntry = dataRow;
                max = value;
            }
        }

        return maxEntry;
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
        return new MaxFunctional(getDataSet(),(IScalarDifferentiableModel) getModel().deepCopy());
    }

    private class ZeroEntry implements Map.Entry<List<Double>, Double> {

        private List<Double> key = new ArrayList<>(1);
        private Double value = 0.0;

        public ZeroEntry(int varsCount) {
            for (int index = 0; index < varsCount; index++) {
                key.add(0.0);
            }
        }

        @Override
        public List<Double> getKey() {
            return key;
        }

        @Override
        public Double getValue() {
            return value;
        }

        @Override
        public Double setValue(Double value) {
            return value;
        }
    }
}
