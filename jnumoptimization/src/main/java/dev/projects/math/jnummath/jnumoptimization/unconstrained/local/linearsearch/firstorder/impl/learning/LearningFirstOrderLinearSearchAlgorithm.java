package dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.impl.learning;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.interpolation.ICubicInterpolationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.interpolation.impl.LearningCubicStepInterpolationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.base.CriticalGradientNormValueIsAchievedException;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.base.ILinearSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;

import java.util.Objects;

public class LearningFirstOrderLinearSearchAlgorithm implements IFirstOrderLearningLinearSearchAlgorithm {

    private double qmin, qmax;

    private final double epsilonCriticalGradientLinearSearchValue = 1.0E-50;

    @Getter
    private double hmax, hn = 0.0;

    private ICubicInterpolationAlgorithm cubicStepInterpolationAlg;
    private FirstOrderOptimalVariables optVars = null;
    private DenseVector correctionGrad = null;

    private final int primeHashCode = 29;

    private FirstOrderLearningLinearSearchOptimalParametersDTO gammaDTO = null;

    public LearningFirstOrderLinearSearchAlgorithm(ICubicInterpolationAlgorithm interpolationAlg,
                                                   double hnStep,
                                                   double hmaxStep,
                                                   double qMax,
                                                   double qMin)   {

        if (qMin > qMax) throw new LoggableException("Unacceptable qMin or qMax parameters");
        if (interpolationAlg == null) throw new LoggableException("Unacceptable interpolation alg object");

        setInitialStep(hnStep);
        hmax = hmaxStep;
        setQMax(qMax);
        setQMin(qMin);

        cubicStepInterpolationAlg = interpolationAlg;
    }

    public LearningFirstOrderLinearSearchAlgorithm(ICubicInterpolationAlgorithm interpolationAlg,
                                                   FirstOrderLearningLinearSearchOptimalParametersDTO gammaDTO,
                                                   double hnStep,
                                                   double hmaxStep,
                                                   double qMax,
                                                   double qMin)   {
        this(interpolationAlg, hnStep, hmaxStep, qMax, qMin);

        setGammaOptimalParameters(gammaDTO);
    }

    public static IFirstOrderLearningLinearSearchAlgorithm getStandartSet(double hMax,
                                                                          double qMin,
                                                                          double qMax)   {
        FirstOrderLearningLinearSearchOptimalParametersDTO gammaDTO = new FirstOrderLearningLinearSearchOptimalParametersDTO();

        return getStandartSet(gammaDTO, hMax, qMin, qMax);
    }

    public static IFirstOrderLearningLinearSearchAlgorithm getStandartSet(FirstOrderLearningLinearSearchOptimalParametersDTO gammaDTO,
                                                                          double hMax,
                                                                          double qMin,
                                                                          double qMax)   {
        if (gammaDTO == null) throw new LoggableException("Unacceptable gamma dto");

        return new LearningFirstOrderLinearSearchAlgorithm(new LearningCubicStepInterpolationAlgorithm(), gammaDTO, 0.5, hMax, qMax, qMin);
    }

    @Override
    public double computeStep(IScalarDifferentiableFunction function,
                              DenseVector currentPoint,
                              DenseVector currentDirection)  , CriticalGradientNormValueIsAchievedException {
        if (getGammaOptimalParameters() == null) {
            throw new LoggableException("A gamma optimal parameters must be set before");
        }

        double euclidNorm = Math.sqrt(currentDirection.getInnerProduct(currentDirection));

        if (hn * euclidNorm > hmax) {
            hn = hmax / euclidNorm;
        }

        double h0 = 0.0,
                h = hn,
                h1 = hn,
                f0 = function.computeOutput(currentPoint),
                f1,
                fm;

        DenseVector gx = function.computeGradient(currentPoint),
                g0 = gx.deepCopy(),
                g1,
                gm;

        double sg0 = g0.getInnerProduct(currentDirection), sg1;

        while (true) {
            DenseVector testPoint = currentPoint.deepCopy().add(h1, currentDirection);

            f1 = function.computeOutput(testPoint);
            g1 = function.computeGradient(testPoint);

            if (g1.getEuclidNorm() <= epsilonCriticalGradientLinearSearchValue) {
                throw new CriticalGradientNormValueIsAchievedException();
            }

            sg1 = g1.getInnerProduct(currentDirection);

            if (sg1 >= 0.0) break;

            h = h * qmax;
            h0 = h1;
            g0 = g1.deepCopy();
            f0 = f1;
            sg0 = sg1;
            h1 = h1 + h;
        }

        hn = hn * qmin * Math.sqrt(h1 / hn);

        double hm = cubicStepInterpolationAlg.computeInterpolation(h0, h1, sg0, sg1, f0, f1);

        FirstOrderLearningLinearSearchOptimalParametersDTO gammaDTO = getGammaOptimalParameters();

        if ((h1 - hm) < gammaDTO.getGammaOne() * (h1 - h0)) {
            hm = h1;
            gm = g1.deepCopy();
            fm = f1;
        } else if (((hm - h0) < gammaDTO.getGammaTwo() * (h1 - h0)) && (h0 > gammaDTO.getGammaThree() * h1)) {
            hm = h0;
            gm = g0.deepCopy();
            fm = f0;
        } else {
            if ((hm - h0) < gammaDTO.getGammaFour() * (h1 - h0)) hm = h0 + gammaDTO.getGammaFive() * (h1 - h0);

            DenseVector testPoint = currentPoint.deepCopy().add(hm, currentDirection);

            fm = function.computeOutput(testPoint);
            gm = function.computeGradient(testPoint).deepCopy();
        }

        correctionGrad = g1.deepCopy();

        setOptimalVariables(hm, fm, gm);

        return hm;
    }

    @Override
    public FirstOrderLearningLinearSearchOptimalParametersDTO getGammaOptimalParameters() {
        return gammaDTO;
    }

    @Override
    public IFirstOrderLearningLinearSearchAlgorithm setGammaOptimalParameters(FirstOrderLearningLinearSearchOptimalParametersDTO gammaDTOObj)   {
        if (gammaDTOObj == null) throw new LoggableException("Unacceptable gamma object");

        gammaDTO = gammaDTOObj;
        return this;
    }

    @Override
    public String getAlgorithmName() {
        FirstOrderLearningLinearSearchOptimalParametersDTO gammaDTO = getGammaOptimalParameters();

        return "A Learning FOLSA {" +
                "qMin= " + qmin
                + ",qMax= " + qmax
                + ",gOne=" + gammaDTO.getGammaOne()
                + ",gTwo=" + gammaDTO.getGammaTwo()
                + ",gThree=" + gammaDTO.getGammaThree()
                + ",gFour=" + gammaDTO.getGammaFour()
                + ",gFive=" + gammaDTO.getGammaFive()
                + "}";
    }

    @Override
    public void initializeProcess(IScalarDifferentiableFunction function)   {
        setOptimalVariables(Double.MAX_VALUE, Double.MAX_VALUE, DenseVector.getInstance(function.getInputDimensionSize(), Double.MAX_VALUE));
        correctionGrad = DenseVector.getInstance(function.getInputDimensionSize(), 0.0);
    }

    @Override
    public DenseVector getCorrectionGradient() {
        return correctionGrad.deepCopy();
    }

    @Override
    public IFirstOrderLearningLinearSearchAlgorithm setQMin(double qMin) {
        qmin = qMin;

        return this;
    }

    @Override
    public double getQMin() {
        return qmin;
    }

    @Override
    public IFirstOrderLearningLinearSearchAlgorithm setQMax(double qMax) {
        qmax = qMax;

        return this;
    }

    @Override
    public double getQMax() {
        return qmax;
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
    public FirstOrderOptimalVariables getOptimalVariables() {
        return optVars;
    }

    private void setOptimalVariables(double hm, double fm, DenseVector gm) {
        optVars = new FirstOrderOptimalVariables(hm, fm, gm);
    }

    @Override
    public void reset()   {
        hn = 0.5;
        hmax = 5.0;
    }

    @Override
    public ILinearSearchAlgorithm<IScalarDifferentiableFunction> deepCopy()   {
        return new LearningFirstOrderLinearSearchAlgorithm(cubicStepInterpolationAlg.deepCopy(),
                getGammaOptimalParameters().deepCopy(),
                hn,
                hmax,
                qmax,
                qmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primeHashCode, hmax, qmin, qmax, gammaDTO);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        LearningFirstOrderLinearSearchAlgorithm otherLsAlg = (LearningFirstOrderLinearSearchAlgorithm) obj;

        return hashCode() == otherLsAlg.hashCode();
    }
}
