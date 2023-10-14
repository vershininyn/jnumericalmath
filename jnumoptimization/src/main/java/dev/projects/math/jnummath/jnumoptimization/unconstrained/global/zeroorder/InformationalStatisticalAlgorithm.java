package dev.projects.math.jnummath.jnumoptimization.unconstrained.global.zeroorder;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.base.ILinearSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.zeroorder.IZeroOrderLinearSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author vyn
 * @description Информационно-статистический метод решения задач с недифференцируемой целевой функцией
 * и адаптивным оцениванием глобальной константы Липшица.
 * @book [1] --> README
 */

public class InformationalStatisticalAlgorithm implements IZeroOrderLinearSearchAlgorithm {

    private double rConstant = 0.0, epsilon = 0.0, low = 0.0, high = 0.0;
    private final GlobalDoubleInterval globalInterval = new GlobalDoubleInterval();

    public InformationalStatisticalAlgorithm(double RConstant, double Epsilon)   {
        if (RConstant <= 1.0) throw new LoggableException("Unacceptable r constant");
        if (Epsilon <= 0.0) throw new LoggableException("Unacceptable epsilon");

        rConstant = RConstant;
        epsilon = Epsilon;
    }

    public void setLeftAndRigth(double leftA, double rigthB)  {
        if (leftA >= rigthB) throw new LoggableException("Unacceptable leftA and rightB");

        low = leftA;
        high = rigthB;

        globalInterval.clear();
    }

    @Override
    public double computeStep(IScalarFunction function,
                              DenseVector currentPoint,
                              DenseVector currentDirection)   {
        globalInterval.addXPoint(low, function.computeOutput(DenseVector.getInstance(new double[]{low})));
        globalInterval.addXPoint(high, function.computeOutput(DenseVector.getInstance(new double[]{high})));

        int k = 1;

        OptimalInterval optInterval = null;

        while(true) {
            double mu = globalInterval.computeLipschitzConstant(k);

            optInterval = globalInterval.chooseOptimalSubInterval(k, mu);

            if (Math.abs(optInterval.getCoordinateHigh() - optInterval.getCoordinateLow()) <= epsilon) break;

            double xpoint = 0.5*(optInterval.getCoordinateHigh() + optInterval.getCoordinateLow())
                    - (1.0/(2.0*mu))*(optInterval.getFunctionHigh() - optInterval.getFunctionLow());

            globalInterval.addXPoint(xpoint, function.computeOutput(DenseVector.getInstance(new double[]{xpoint})));

            k++;
        }

        return globalInterval.getOptimalStep();
    }

    @Override
    public void setInitialStep(double step) {

    }

    @Override
    public double getInitialStep() {
        return 0;
    }

    @Override
    public String getAlgorithmName() {
        return "Informational statistical algorithm";
    }

    @Override
    public void reset()   {
        globalInterval.clear();
    }

    @Override
    public ILinearSearchAlgorithm<IScalarFunction> deepCopy()   {
        return new InformationalStatisticalAlgorithm(rConstant,epsilon);
    }

    @AllArgsConstructor
    private class OptimalInterval {
        @Getter
        @Setter
        private double coordinateLow = 0.0, coordinateHigh = 0.0, functionLow = 0.0, functionHigh = 0.0;
    }

    private class GlobalDoubleInterval {
        private final SortedMap<Double, Double> experimentFunctionMap = new TreeMap<Double, Double>();

        public double computeLipschitzConstant(int k) {
            Double[] coordinateArray = experimentFunctionMap.keySet().toArray(new Double[0]),
                     alphaFunctionArray = experimentFunctionMap.values().toArray(new Double[0]);

            double hValue = Double.MIN_VALUE;

            for(int index=1; index <= k; index++) {
                double hTestValue = ((Math.abs(alphaFunctionArray[index]
                        - alphaFunctionArray[index-1]))/(coordinateArray[index] - coordinateArray[index-1]));

                hValue = (hTestValue > hValue) ? (hTestValue) : (hValue);
            }

            if (hValue == 0.0) {
                return 1.0;
            } else if (hValue > 0) {
                return rConstant*hValue;
            } else return 0.0;
        }

        public void clear() {
            experimentFunctionMap.clear();
        }

        public double getOptimalStep() {
            Map.Entry<Double, Double> minEntry = experimentFunctionMap
                    .entrySet()
                    .stream()
                    .min((e0,e1) -> {
                        if (e0.getValue() > e1.getValue()) return 1;
                        if (e1.getValue() > e0.getValue()) return -1;
                        return 0;
                    })
                    .get();

            return minEntry.getKey();
        }

        public void addXPoint(double xCoordinate, double alphaFunction) {
            experimentFunctionMap.put(xCoordinate, alphaFunction);
        }

        public OptimalInterval chooseOptimalSubInterval(int k, double mu) {
            Double[] coordinateArray = experimentFunctionMap.keySet().toArray(new Double[0]),
                     alphaFunctionArray = experimentFunctionMap.values().toArray(new Double[0]);

            int searchIndex = 1;

            double rValue = Double.MIN_VALUE;

            for(int index=1; index <= k; index++) {
                double coordDiff = mu*(coordinateArray[index] - coordinateArray[index-1]);

                double rTestValue = coordDiff
                        + (Math.pow(alphaFunctionArray[index] - alphaFunctionArray[index-1],2.0))/coordDiff
                        - 2.0*(alphaFunctionArray[index] + alphaFunctionArray[index-1]);

                if (rTestValue > rValue) {
                    searchIndex = index;
                    rValue = rTestValue;
                }
            }

            return new OptimalInterval(coordinateArray[searchIndex],
                    coordinateArray[searchIndex-1],
                    alphaFunctionArray[searchIndex],
                    alphaFunctionArray[searchIndex-1]);
        }
    }
}
