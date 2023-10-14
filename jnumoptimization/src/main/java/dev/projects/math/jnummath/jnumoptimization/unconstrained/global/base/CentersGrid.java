package dev.projects.math.jnummath.jnumoptimization.unconstrained.global.base;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.utils.exception.LoggableException;

import java.util.LinkedList;
import java.util.List;

public class CentersGrid {

    public GridExtensionResult extendGridToAnotherDimensions(int maxDimension,
                                                             int centersCount,
                                                             double alpha,
                                                             double beta)   {
        if (maxDimension <= 0) throw new LoggableException("Unacceptable max dimension");
        if (centersCount <= 0) throw new LoggableException("Unacceptable centers count");
        if (alpha >= beta) throw new LoggableException("Unacceptable alpha and beta egdes");

        List<DenseVector> oneDimentionalGrid = generateOneDimentionalGrid(centersCount, alpha, beta),
                dimentionalGrid = oneDimentionalGrid,
                tempGrid;

        double N = Math.abs(oneDimentionalGrid.stream().mapToDouble((v) -> v.getValue(0)).max().getAsDouble()),
                step = (2.0 * N) / centersCount;

        for (int dim = 0; dim < maxDimension - 1; dim++) {
            tempGrid = new LinkedList<>();

            for (double value = (-1.0) * N; value <= N; value += 2.0 * step) {
                List<DenseVector> grid = copyOfGrid(dimentionalGrid);

                for (DenseVector vector : grid) {
                    tempGrid.add(vector.extendByDouble(value));
                }
            }

            dimentionalGrid = tempGrid;
        }

        return new GridExtensionResult(N, dimentionalGrid);
    }

    private List<DenseVector> copyOfGrid(List<DenseVector> grid) {
        List<DenseVector> copy = new LinkedList<>();
        copy.addAll(grid);
        return copy;
    }

    private List<DenseVector> generateOneDimentionalGrid(int centersCount, double alpha, double beta)   {
        List<DenseVector> centersGrid = new LinkedList<>();

        double step = (beta - alpha) / centersCount,
                centerK = alpha;

        int k = 1;

        while ((centerK + step) <= beta) {
            centerK = 0.5 * (alpha + k * step - step / 2.0);
            centersGrid.add(DenseVector.getInstance(new double[]{centerK}));
            k++;
        }

        return centersGrid;
    }

}
