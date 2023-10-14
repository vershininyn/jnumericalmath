package dev.projects.math.jnummath.jtransformations.tabulator;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.vector.IVectorFunction;
import dev.projects.math.jnummath.jtransformations.tabulator.interval.ITemplateInterval;

import java.util.Set;

public interface IFunctionallyConditionedGrid {
    ITemplateInterval getTemplateInterval();

    IFunctionallyConditionedGrid setTemplateInterval(ITemplateInterval templateInterval);

    IFunctionallyConditionedGrid setTransformationFunction(IVectorFunction transformationFunction);

    IVectorFunction getTransformationFunction();

    Set<DenseVector> getGrid();

    IFunctionallyConditionedGrid generateGrid();

    IFunctionallyConditionedGrid generateGrid(final ITemplateInterval templateInterval,
                                              final IVectorFunction transformationFunction);
}
