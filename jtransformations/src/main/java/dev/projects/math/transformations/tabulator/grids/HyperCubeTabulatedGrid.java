package dev.projects.math.transformations.tabulator.grids;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
import dev.projects.math.transformations.tabulator.TabulatedGrid;
import dev.projects.utils.exception.LoggableException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
@Getter
public class HyperCubeTabulatedGrid implements TabulatedGrid {
    private double minBoundary = 0.0D;
    private double maxBoundary = 0.0D;
    private long betweenMinAndMaxPointsCount = 0L;
    private long dimension = 0L;

    private final Set<DenseVector> hyperCubeGrid = new HashSet<>(1024);

//  TODO:  private final IScalarFunction hyperCubeGridMappingFunction = IT IS VECTOR FUNCTION

    public static TabulatedGrid getInstance(double minBoundary, double maxBoundary, long pointsCount, long dimension) throws LoggableException {
        checkBoundaries(minBoundary, maxBoundary);
        checkPointsCount(pointsCount);
        checkDimension(dimension);

        return new HyperCubeTabulatedGrid(minBoundary, maxBoundary, pointsCount, dimension);
    }

    public static TabulatedGrid getInstance(double minBoundary, double maxBoundary, long dimension) throws LoggableException {
        checkBoundaries(minBoundary, maxBoundary);
        checkDimension(dimension);

        return new HyperCubeTabulatedGrid(minBoundary, maxBoundary, dimension);
    }

    private static void checkBoundaries(double minBoundary, double maxBoundary) throws LoggableException {
        if ((minBoundary == maxBoundary)
                || (minBoundary > maxBoundary)
                || (maxBoundary < minBoundary)) {

            throw new LoggableException("Unacceptable boundaries: (min != max) && (min < max) && (max > min)");
        }
    }

    private static void checkPointsCount(long pointsCount) throws LoggableException {
        if (pointsCount <= 0L) {
            throw new LoggableException("Unacceptable points count: it's can't be <= 0L");
        }
    }

    private static void checkDimension(long dimension) throws LoggableException {
        if (dimension <= 0L) {
            throw new LoggableException("Unacceptable dimension: it's can't be <= 0L");
        }
    }

    private HyperCubeTabulatedGrid(double minBoundary, double maxBoundary, long dimension) throws LoggableException {
        setBoundaries(minBoundary, maxBoundary);
        setDimension(dimension);
    }

    private HyperCubeTabulatedGrid(double minBoundary, double maxBoundary, long betweenMinAndMaxPointsCount, long dimension) throws LoggableException {
        setBoundaries(minBoundary, maxBoundary);
        setBetweenMinAndMaxPointsCount(betweenMinAndMaxPointsCount);
        setDimension(dimension);
    }

    @Override
    public TabulatedGrid setBoundaries(double minBoundary, double maxBoundary) throws LoggableException {
        checkBoundaries(minBoundary, maxBoundary);

        this.minBoundary = minBoundary;
        this.maxBoundary = maxBoundary;

        return this;
    }

    public TabulatedGrid setBetweenMinAndMaxPointsCount(long pointsCount) throws LoggableException {
        checkPointsCount(pointsCount);

        this.betweenMinAndMaxPointsCount = pointsCount;

        return this;
    }

    public TabulatedGrid setDimension(long dimension) throws LoggableException {
        checkDimension(dimension);

        this.dimension = dimension;

        return this;
    }

    @Override
    public TabulatedGrid update() throws LoggableException {
        return update(getMinBoundary(), getMaxBoundary(), getBetweenMinAndMaxPointsCount(), getDimension());
    }

    @Override
    public TabulatedGrid update(double minBoundary, double maxBoundary, long pointsCount, long dimension) throws LoggableException {
        checkBoundaries(minBoundary, maxBoundary);
        checkPointsCount(pointsCount);
        checkDimension(dimension);

        double step = (maxBoundary - minBoundary) / (pointsCount);

//        Set<DenseVector> hyperCubeGrid = generateAllPermutations()
//                .stream()

        //TODO: REALIZE IT!

        return this;
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
