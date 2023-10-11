package dev.projects.math.transformations.model.functionals.impl;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.differentiale.AbstractScalarDifferentiableFunction;
import dev.projects.math.transformations.function.IFunction;
import dev.projects.math.transformations.function.scalar.differentiale.IScalarDifferentiableFunction;
import dev.projects.math.transformations.model.functionals.IFunctional;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TihonovRegularazedFunctionalDecorator
        extends AbstractScalarDifferentiableFunction
        implements IScalarDifferentiableFunction {

    private double alpha;
    private IFunctional functional;

    public TihonovRegularazedFunctionalDecorator(IFunctional nestedFunctional, double alphaCoefficient) throws LoggableException {
        super(nestedFunctional.getVariables());

        if (alphaCoefficient < 0.0) throw new LoggableException("Unacceptable alpha coefficient");

        setAlpha(alphaCoefficient);
        setFunctional(nestedFunctional);
    }

    @Override
    public DenseVector getVariables() throws LoggableException {
        if (functional == null) throw new LoggableException("Functional is not setted");

        return functional.getVariables();
    }

    @Override
    public void setVariables(DenseVector variables) throws LoggableException {
        if (functional == null) return;

        functional.setVariables(variables);

        deactivateOutputCache();
        deactivateGradientCache();
    }

    @Override
    protected Double actualComputeOutput() throws LoggableException {
        IFunctional fun = getFunctional();
        DenseVector parameters = fun.getVariables();

        return fun.computeOutput() + alpha*parameters.getInnerProduct(parameters);
    }

    @Override
    protected DenseVector actualComputeGradient() throws LoggableException {
        IFunctional fun = getFunctional();

        DenseVector functionalGrad = fun.computeGradient(),
                parameters = fun.getVariables();

        int paramCount = fun.getVariablesCount();

        double alphaCoeff = 2.0*alpha;

        for(int paramIndex = 0; paramIndex < paramCount; paramIndex++) {
            functionalGrad.setValue(paramIndex,functionalGrad.getValue(paramIndex) + alphaCoeff*parameters.getValue(paramIndex));
        }

        return functionalGrad;
    }

    @Override
    public String getName() {
        return functional.getName() + "+("+getAlpha()+")*||w||)";
    }

    @Override
    public IFunction<DenseVector, Double> deepCopy() throws LoggableException {
        return new TihonovRegularazedFunctionalDecorator((IFunctional) getFunctional().deepCopy(),getAlpha());
    }
}
