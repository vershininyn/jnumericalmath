package dev.projects.math.optimization.unconstrained.global.base;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.linalgebra.ICloneable;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SimpleHyperCube implements ICloneable<SimpleHyperCube> {

    @Getter @Setter
    private DenseVector alphaPoint, betaPoint;

    private final CentersGrid grid = new CentersGrid();

    public static SimpleHyperCube getInstance(double edgeLength, DenseVector center) throws LoggableException {
        if (edgeLength <= 0.0) throw new LoggableException("Unacceptable edge length");
        if (center == null) throw new LoggableException("Unacceptable center vector");

        DenseVector shift = DenseVector.getInstance(center.getSize(), edgeLength/2.0);

        return new SimpleHyperCube(center.deepCopy().substract(shift),center.deepCopy().add(shift));
    }

    public SimpleHyperCube(DenseVector alpha, DenseVector beta) throws LoggableException {
        if ((alpha == null)
                || (beta == null)
                || (alpha.getSize() != beta.getSize())) throw new LoggableException("Unacceptable alpha and/or beta vector");

        setAlphaPoint(alpha.deepCopy());
        setBetaPoint(beta.deepCopy());
    }

    public List<SimpleHyperCube> divideIntoSubHyperCubes(int variablesCount,
                                                         int oneDimensionalCentersCount,
                                                         double alpha,
                                                         double beta) throws LoggableException {
        if (variablesCount <= 0) throw new LoggableException("Unacceptable variables count");
        if (oneDimensionalCentersCount <= 0) throw new LoggableException("Unacceptable one dimensional center count");
        if (alpha >= beta) throw new LoggableException("Unacceptable alpha and/or beta");

        GridExtensionResult gridCenters = grid.extendGridToAnotherDimensions(variablesCount,oneDimensionalCentersCount,alpha,beta);

        double edgeLength = gridCenters.getEdgeLength();

        List<SimpleHyperCube> subCubes = new ArrayList<>(512);

        for(DenseVector center: gridCenters.getCentersList()) {
            subCubes.add(SimpleHyperCube.getInstance(edgeLength, center));
        }

        return subCubes;
    }

    @Override
    public String toString() {
        return "SHC alpha= "+getAlphaPoint().toString()+",beta= "+getBetaPoint().toString();
    }

    @Override
    public SimpleHyperCube deepCopy() throws LoggableException {
        return new SimpleHyperCube(getAlphaPoint().deepCopy(),getBetaPoint().deepCopy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAlphaPoint(),getBetaPoint());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        return hashCode() == obj.hashCode();
    }
}
