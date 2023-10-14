package dev.projects.math.jnummath.jtransformations.function.scalar.impl;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.IFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.AbstractScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;

public class GeneralizedRosenbrockScalarDifferentiableFunction
		extends AbstractScalarDifferentiableFunction
		implements IScalarDifferentiableFunction {

	protected GeneralizedRosenbrockScalarDifferentiableFunction(int variablesCount)   {
		super(variablesCount);

		if (variablesCount % 2 != 0) throw new LoggableException("Unacceptable variables count");
	}

	public GeneralizedRosenbrockScalarDifferentiableFunction(DenseVector variables)   {
		this(variables.getSize());

		setVariables(variables);
	}

	@Override
	public String getName() {
		return "sum{100.0*(-x[k]^2 + x[k+1])^2 + (x[k] - 1.0)^2,k=1,...,N}";
	}

	@Override
	protected DenseVector actualComputeGradient()   {
		DenseVector vars = getVariables(), gradient = DenseVector.getInstance(getInputDimensionSize(), 0.0);

		for(int index = 0; index < getInputDimensionSize() - 1; index = index + 2) {
			double value = 200.0*(vars.getValue(index+1) - Math.pow(vars.getValue(index),2.0));

			gradient.setValue(index, -2.0*vars.getValue(index)*value + 2.0*(vars.getValue(index) - 1.0));
			gradient.setValue(index + 1, value);
		}

		return gradient;
	}

	@Override
	protected Double actualComputeOutput()   {
		DenseVector vars = getVariables();
		double out = 0.0;

		for(int index = 0; index < getInputDimensionSize() - 1; index++) {
			out += 100.0*Math.pow((vars.getValue(index+1) - Math.pow(vars.getValue(index),2.0)), 2.0) + Math.pow(vars.getValue(index) - 1.0, 2.0);
		}

		return out;
	}

	@Override
	public IFunction<DenseVector, Double> deepCopy()   {
		return new GeneralizedRosenbrockScalarDifferentiableFunction(getVariables().deepCopy());
	}
}
