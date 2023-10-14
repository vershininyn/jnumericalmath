package dev.projects.math.jnummath.jnumoptimization.constrained.barrier.impl.inequalities;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.AbstractScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
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

    public IndexedScalarDifferentiableFunction(int variablesCount, List<Double> parametersList, List<Integer> variablesIndexesList)   {
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
                                               List<Integer> variablesIndexesList)   {
        this(variables.getSize(), parametersList, variablesIndexesList);
    }

    public void setVariableIndexesList(List<Integer> indexesList) {
        variableIndexesList.addAll(indexesList);
    }

    public void setParametersList(List<Double> list) {
        parametersList.addAll(list);
    }

    @Override
    protected Double actualComputeOutput()   {
        DenseVector vars = getVariables();

        List<Double> parameters = getParametersList();
        List<Integer> varsIndexes = getVariableIndexesList();

        double out = 0.0;

        for (int index = 0; index < getInputDimensionSize(); index++) {
            out += parameters.get(index)*vars.getValue(varsIndexes.get(index));
        }

        return out;
    }

    @Override
    protected DenseVector actualComputeGradient()   {
        DenseVector grad = DenseVector.getInstance(getInputDimensionSize(), 0.0);

        List<Double> parameters = getParametersList();
        List<Integer> varsIndexes = getVariableIndexesList();

        for(int index = 0; index < getInputDimensionSize(); index++) {
            grad.setValue(varsIndexes.get(index), parameters.get(index));
        }

        return grad;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy()   {
        return new IndexedScalarDifferentiableFunction(getVariables().deepCopy(), getParametersList(), getVariableIndexesList());
    }
}
