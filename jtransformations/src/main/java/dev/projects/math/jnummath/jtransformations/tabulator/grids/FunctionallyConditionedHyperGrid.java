package dev.projects.math.jnummath.jtransformations.tabulator.grids;

import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jtransformations.function.vector.IVectorFunction;
import dev.projects.math.jnummath.jtransformations.function.vector.impl.ZeroTransformVectorFunction;
import dev.projects.math.jnummath.jtransformations.tabulator.IFunctionallyConditionedGrid;
import dev.projects.math.jnummath.jtransformations.tabulator.interval.ITemplateInterval;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
@Getter
public class FunctionallyConditionedHyperGrid implements IFunctionallyConditionedGrid {
    private ITemplateInterval templateInterval = null;
    private IVectorFunction transformationFunction = null;

    private final Set<DenseVector> grid = new HashSet<>(2048);

    public static IFunctionallyConditionedGrid getInstance() {
        return new FunctionallyConditionedHyperGrid();
    }

    public static IFunctionallyConditionedGrid getInstance(final ITemplateInterval templateInterval) {
        checkTemplateInterval(templateInterval);

        IVectorFunction defaultZeroTransformationFunction = new ZeroTransformVectorFunction(templateInterval.getDimension());

        return new FunctionallyConditionedHyperGrid(templateInterval, defaultZeroTransformationFunction);
    }

    public static IFunctionallyConditionedGrid getInstance(final ITemplateInterval templateInterval,
                                                           final IVectorFunction transformationFunction) {
        checkTemplateInterval(templateInterval);
        checkTransformationFunction(transformationFunction);
        checkTransformationFunctionAndTemplateIntervalJointly(templateInterval, transformationFunction);

        return new FunctionallyConditionedHyperGrid(templateInterval, transformationFunction);
    }

    private static void checkTemplateInterval(final ITemplateInterval templateInterval) {
        if (templateInterval == null) {
            throw new LoggableException("Unacceptable template interval: it's can't be NULL");
        }
    }

    private static void checkTransformationFunction(final IVectorFunction transformationFunction) {
        if (transformationFunction == null) {
            throw new LoggableException("Unacceptable transformation function: it's can't be NULL");
        }

        if (transformationFunction.getInputDimensionSize() <= 0) {
            throw new LoggableException("Unacceptable transformation function: it's input dimension size can't be <= 0");
        }
    }

    private static void checkTransformationFunctionAndTemplateIntervalJointly(final ITemplateInterval templateInterval,
                                                                              final IVectorFunction transformationFunction) {
        checkTemplateInterval(templateInterval);
        checkTransformationFunction(transformationFunction);

        if (transformationFunction.getInputDimensionSize() != templateInterval.getDimension()) {
            throw new LoggableException("Unacceptable transformation function OR template interval: it's size's MUST BE EQUAL");
        }
    }

    private FunctionallyConditionedHyperGrid() {
    }

    private FunctionallyConditionedHyperGrid(final ITemplateInterval templateInterval) {
        setTemplateInterval(templateInterval);
    }

    private FunctionallyConditionedHyperGrid(final ITemplateInterval templateInterval,
                                             final IVectorFunction transformationFunction) {
        this(templateInterval);

        setTransformationFunction(transformationFunction);
    }

    @Override
    public IFunctionallyConditionedGrid setTemplateInterval(ITemplateInterval templateInterval) {
        checkTemplateInterval(templateInterval);

        this.templateInterval = templateInterval;

        return this;
    }

    public IFunctionallyConditionedGrid setTransformationFunction(final IVectorFunction transformationFunction) {
        checkTransformationFunction(transformationFunction);

        this.transformationFunction = transformationFunction;

        return this;
    }

    @Override
    public IFunctionallyConditionedGrid generateGrid() {
        return generateGrid(getTemplateInterval(), getTransformationFunction());
    }

    public IFunctionallyConditionedGrid generateGrid(final ITemplateInterval templateInterval,
                                                     final IVectorFunction transformationFunction) {
        checkTemplateInterval(templateInterval);
        checkTransformationFunction(transformationFunction);

//        checkTransformationFunctionAndDimension(transformationFunction, dimension);

//        double step = (maxBoundary - minBoundary) / (pointsCount);

//        Set<DenseVector> hyperCubeGrid = generateAllPermutations()
//                .stream()

        return this;
    }

    private Set<DenseVector> generateAllPermutations() {
        return null;
    }

//    private Set<DenseVector> generateAllPermutations() {
//        // NOTICE:  Copyright 2008, Phillip Paul Fuchs
//
//#define N    12   // number of elements to permute.  Let N > 2
//
//        void QuickPerm(void) {
//                unsigned int a[N], p[N];
//        register unsigned int i, j, tmp; // Upper Index i; Lower Index j
//
//        for(i = 0; i < N; i++) {  // initialize arrays; a[N] can be any type
//            a[i] = i + 1;   // a[i] value is not revealed and can be arbitrary
//            p[i] = 0;       // p[i] == i controls iteration and index boundaries for i
//        }
//        //display(a, 0, 0);   // remove comment to display array a[]
//        i = 1;   // setup first swap points to be 1 and 0 respectively (i & j)
//        while(i < N) {
//            if (p[i] < i) {
//                j = i % 2 * p[i];   // IF i is odd then j = p[i] otherwise j = 0
//                tmp = a[j];         // swap(a[j], a[i])
//                a[j] = a[i];
//                a[i] = tmp;
//                //display(a, j, i); // remove comment to display target array a[]
//                p[i]++;             // increase index "weight" for i by one
//                i = 1;              // reset index i to 1 (assumed)
//            } else {               // otherwise p[i] == i
//                p[i] = 0;           // reset p[i] to zero
//                i++;                // set new index value for i (increase by one)
//            } // if (p[i] < i)
//        } // while(i < N)
//} // QuickPerm()
//    }

}
