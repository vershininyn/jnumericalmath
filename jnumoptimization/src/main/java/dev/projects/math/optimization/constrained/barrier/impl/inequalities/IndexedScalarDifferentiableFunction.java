package dev.projects.math.optimization.constrained.barrier.impl.inequalities;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.differentiale.AbstractScalarDifferentiableFunction;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class IndexedScalarDifferentiableFunction
        extends AbstractScalarDifferentiableFunction
        implements IScalarDifferentiableFunction {
    @Getter
    private final List<Integer> variableIndexesList = new ArrayList<>(32);

    @Getter
    private final List<Double> parametersList = new ArrayList<>(32);

    public IndexedScalarDifferentiableFunction(int variablesCount, List<Double> parametersList, List<Integer> variablesIndexesList) throws LoggableException {
        super(variablesCount);

        if ((variablesIndexesList == null) || (variablesIndexesList.isEmpty()))
            throw new LoggableException("Unacceptable list of indexes");

        boolean isOk = variablesIndexesList.stream()
                .allMatch(value -> value < variablesCount);

        if (!isOk)
            throw new LoggableException("Unacceptable list of indexes: all indexes must be less then {" + variablesCount + "}");
        
        if ((parametersList == null)
                || (parametersList.isEmpty())
                || (parametersList.size() != variablesIndexesList.size()))
            throw new LoggableException("Unacceptable parameters list");

        setParametersList(parametersList);
        setVariableIndexesList(variablesIndexesList);
    }

    public IndexedScalarDifferentiableFunction(DenseVector variables,
                                               List<Double> parametersList,
                                               List<Integer> variablesIndexesList) throws LoggableException {
        this(variables.getSize(), parametersList, variablesIndexesList);
    }

    public void setVariableIndexesList(List<Integer> indexesList) {
        variableIndexesList.addAll(indexesList);
    }

    public void setParametersList(List<Double> list) {
        parametersList.addAll(list);
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
        DenseVector vars = getVariables();

        List<Double> parameters = getParametersList();
        List<Integer> varsIndexes = getVariableIndexesList();

        double out = 0.0;

        for (int index = 0; index < getVariablesCount(); index++) {
            out += parameters.get(index)*vars.getValue(varsIndexes.get(index));
        }

        return out;
    }

    @Override
    protected DenseVector actualComputeGradient() throws LoggableException {
        DenseVector grad = DenseVector.getInstance(getVariablesCount(), 0.0);

        List<Double> parameters = getParametersList();
        List<Integer> varsIndexes = getVariableIndexesList();

        for(int index = 0; index < getVariablesCount(); index++) {
            grad.setValue(varsIndexes.get(index), parameters.get(index));
        }

        return grad;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        return new IndexedScalarDifferentiableFunction(getVariables().deepCopy(), getParametersList(), getVariableIndexesList());
    }
}
