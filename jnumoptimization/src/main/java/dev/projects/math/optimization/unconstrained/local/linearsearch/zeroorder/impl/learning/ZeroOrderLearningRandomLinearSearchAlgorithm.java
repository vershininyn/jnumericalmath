package dev.projects.math.optimization.unconstrained.local.linearsearch.zeroorder.impl.learning;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.linearsearch.base.ILinearSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;

public class ZeroOrderLearningRandomLinearSearchAlgorithm
                implements IZeroOrderLearningLinearSearchAlgorithm {

    private int k1sp = 0, k2sp = 0, n2sp = 0;
    private double hn = 0.0, xxsp = 2.5, psp = 0.4, esp = getEspCoefficient(xxsp, psp);

    private ZeroOrderOptimalVariables optVars;
    private ZeroOrderLearningLinearSearchAlgorithmStateDto state;

    public ZeroOrderLearningRandomLinearSearchAlgorithm(double initialStep) {
        setInitialStep(initialStep);
    }

    @Override
    public void initializeProcess(IScalarFunction function) throws LoggableException {
        setOptimalVariables(Double.MAX_VALUE, Double.MAX_VALUE);
        setState(0.0,0.0,0.0,0.0,0.0,0.0);
    }

    @Override
    public double computeStep(IScalarFunction function,
                              DenseVector currentPoint,
                              DenseVector currentDirection) throws LoggableException {
        double hm = getInitialStep(),

                h1 = 0.0,
                h2 = 0.0,
                h3 = hm,

                f1 = 0.0,
                f2 = function.computeOutput(currentPoint),
                fm = 0.0;

        DenseVector testPoint = currentPoint.deepCopy().add(h3, currentDirection);

        double f3 = function.computeOutput(testPoint);

        if (f3 > f2) {
            f1 = f3;
            h1 = h3;
            hm = (-1.0)*hm;
            h3 = hm;

            testPoint = currentPoint.deepCopy().add(h3, currentDirection);
            f3 = function.computeOutput(testPoint);
        }

        while (f3 <= f2) {
            f1 = f2;
            h1 = h2;

            f2 = f3;
            h2 = h3;

            h3 = h3 + hm;
            hm = 2.0*hm;

            testPoint = currentPoint.deepCopy().add(h3, currentDirection);
            f3 = function.computeOutput(testPoint);
        }

        hm = h2;
        fm = f2;

        if (Math.abs(h2) < 0.1*hn) {
            hn = hn*esp;
        } else {
            k1sp++;
            hn = hn*xxsp;

            if (Math.abs(h2) > 1.5*(hn/xxsp)) {
                k2sp++;
                n2sp++;
            }
        }

        if (n2sp > 7) {
            psp = (k2sp > k1sp) ? (psp/1.3) : (1.3*psp);

            if (psp > 0.4) {
                psp = 0.4;
                esp = getEspCoefficient(xxsp, psp);

                k1sp = 0;
                k2sp = 0;
                n2sp = 0;
            }
        }

        setState(h1,h2,h3,f1,f2,f3);
        setOptimalVariables(hm , fm);

        return hm;
    }

    @Override
    public void setInitialStep(double step) {
        hn = step;
    }

    @Override
    public double getInitialStep() {
        return hn;
    }

    @Override
    public String getAlgorithmName() {
        return "A Learning ZOLSA";
    }

    @Override
    public ILinearSearchAlgorithm<IScalarFunction> deepCopy() throws LoggableException {
        return new ZeroOrderLearningRandomLinearSearchAlgorithm(getInitialStep());
    }

    @Override
    public void reset() throws LoggableException {
        setInitialStep(0.5);
    }

    @Override
    public ZeroOrderOptimalVariables getOptimalVariables() {
        return optVars;
    }

    @Override
    public ZeroOrderLearningLinearSearchAlgorithmStateDto getState() {
        return state;
    }

    private void setOptimalVariables(double hm, double fm) {
        optVars = new ZeroOrderOptimalVariables(hm, fm);
    }

    private void setState(double h1, double h2, double h3, double f1, double f2, double f3) {
        state = new ZeroOrderLearningLinearSearchAlgorithmStateDto(h1,h2,h3,getInitialStep(),f1,f2,f3);
    }

    private double getEspCoefficient(double xxsp, double psp) {
        double coeff = Math.log(xxsp)*((-1.0)*(psp/(1.0 - psp)));
        return Math.exp(coeff);
    }
}
