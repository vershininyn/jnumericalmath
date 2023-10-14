package dev.projects.math.jnummath.jnumoptimization.unconstrained.global.zeroorder;

import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.base.StopCriteriaEnumeration;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.global.base.SimpleHyperCube;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.global.zeroorder.base.IZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.global.zeroorder.base.ZeroOrderGlobalCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.IUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.UnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.zeroorder.IZeroOrderDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.zeroorder.IZeroOrderLinearSearchAlgorithm;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Многомерный диагональный Информационно-статистический метод с Недифференцируемой
 * целевой функцией и адаптивной оценкой Глобальной константы Липшица, основанный
 * на Безызбыточной Стратегии разбиения гиперинтервалов
 */
public class ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm
        extends UnconstrainedOptimizationAlgorithm<IScalarFunction,
                IZeroOrderLinearSearchAlgorithm,
                IZeroOrderDirectionSearchAlgorithm,
                ZeroOrderGlobalCriticalOptimizationAttributes,
        ZeroOrderOptimizationResults>
        implements IZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm
{

    @Getter
    @Setter
    private double rConstant = 0.0, cConstant = 0.0;

    private double initialFunctionOutput = 0.0;

    @Getter
    private SimpleHyperCube originalCube;

    private SimpleHyperCube currentOptimalCube;

    private final Map<DenseVector, Double> testingMap = new HashMap<>(128);

    private final List<SimpleHyperCube> searchArea = new ArrayList<>(1024);

    public ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm(double RConstant,
                                                                                     double CConstant,
                                                                                     IScalarFunction function,
                                                                                     ZeroOrderGlobalCriticalOptimizationAttributes attributes,
                                                                                     SimpleHyperCube cube)   {
        this(RConstant, CConstant, attributes, cube);

        setFunction(function);
    }

    public ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm(double RConstant,
                                                                                     double CConstant,
                                                                                     ZeroOrderGlobalCriticalOptimizationAttributes attributes,
                                                                                     SimpleHyperCube cube)   {
        super(attributes, null, null);

        if (RConstant <= 1.0) throw new LoggableException("Unacceptable R constant");
        if (CConstant <= 0.0) throw new LoggableException("Unacceptable C constant");

        setRConstant(RConstant);
        setCConstant(CConstant);
        setOriginalCube(cube);
    }

    @Override
    public void setOriginalCube(SimpleHyperCube cube)   {
        if (cube == null) throw new LoggableException("Unacceptable cube");

        originalCube = cube;
    }

    @Override
    public ZeroOrderOptimizationResults optimize(IScalarFunction function,
                                                 ZeroOrderGlobalCriticalOptimizationAttributes attributes,
                                                 SimpleHyperCube originalHyperCube)   {
        setFunction(function);
        setOriginalCube(originalHyperCube);
        setCriticalOptimizationAttributes(attributes);

        return minimize();
    }

    @Override
    public String getAlgorithmName() {
        return "A ISGZOOA";
    }

    @Override
    public ZeroOrderOptimizationResults getOptimizationResults(int actualIterationCount, Duration period)   {
        return new ZeroOrderOptimizationResults(actualIterationCount,
                getFunction().getOutputComputeCount(),
                getRepeatedFunctionOutputCount(),
                getFunction().computeOutput(),
                getAlgorithmName(),
                period,
                getCurrentApproximation().deepCopy(),
                getStopCriteria());
    }

    @Override
    public IUnconstrainedOptimizationAlgorithm<IScalarFunction,
                IZeroOrderLinearSearchAlgorithm,
                IZeroOrderDirectionSearchAlgorithm,
                ZeroOrderGlobalCriticalOptimizationAttributes,
                ZeroOrderOptimizationResults> deepCopy()   {
        return new ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm(getRConstant(),
                getCConstant(),
                (IScalarFunction) getFunction().deepCopy(),
                (ZeroOrderGlobalCriticalOptimizationAttributes)getCriticalOptimizationAttributes().deepCopy(),
                getOriginalCube().deepCopy());
    }

    @Override
    protected boolean checkOtherCondition()   {
        ZeroOrderGlobalCriticalOptimizationAttributes attrb = getCriticalOptimizationAttributes();

        DenseVector optAlphaPoint = currentOptimalCube.getAlphaPoint(),
                optBetaPoint = currentOptimalCube.getBetaPoint(),
                origAlphaPoint = getOriginalCube().getAlphaPoint(),
                origBetaPoint = getOriginalCube().getBetaPoint();

        double optNorm = optAlphaPoint.deepCopy().substract(optBetaPoint).getEuclidNorm(),
                origNorm = origAlphaPoint.deepCopy().substract(origBetaPoint).getEuclidNorm(),
                currentOutput = getFunction().computeOutput(getCurrentApproximation());

        return checkCriteria(StopCriteriaEnumeration.STOPPED_BY_CRITICAL_GLOBAL_EPSILON,
                "||a[t] - b[t]|| <= "+attrb.getEpsilonGlobalByNormValue()+"*||origA - origB||",
                optNorm <= attrb.getEpsilonGlobalByNormValue()*origNorm)
                || checkCriteria(StopCriteriaEnumeration.STOPPED_BY_GLOBAL_MAX_MULTIPLICITY_FACTOR,
                                "abs(FInitial/FCurrent) >= "+attrb.getMaxMultiplicityFactor(),
                                Math.abs(initialFunctionOutput/currentOutput) >= attrb.getMaxMultiplicityFactor());
    }

    @Override
    protected DenseVector computeDelta(int actualIterationCount)   {
        throw new LoggableException("Unsupported operation");
    }

    @Override
    protected void doOptimizationCycle()   {
        IScalarFunction function = getFunction();
        SimpleHyperCube originalCube = getOriginalCube();

        DenseVector alphaPoint = originalCube.getAlphaPoint().deepCopy(),
                betaPoint = originalCube.getBetaPoint().deepCopy();

        double alphaFunction = function.computeOutput(alphaPoint),
                betaFunction = function.computeOutput(betaPoint);

        initialFunctionOutput = Math.min(alphaFunction, betaFunction);

        testingMap.putIfAbsent(alphaPoint, alphaFunction);
        testingMap.putIfAbsent(betaPoint, betaFunction);

        searchArea.add(originalCube);

        double globalLipschitzConstant = Math.abs(alphaFunction - betaFunction)/(alphaPoint.deepCopy().substract(betaPoint)
                .getEuclidNorm());

        while(true) {
            currentOptimalCube = computeRParameterAtSearchAreaAndReturnOptCube(globalLipschitzConstant);

            if (checkStopCondition()) break;

            DenseVector[] uvPoints = generateUAndVPoints(currentOptimalCube);

            if (!testingMap.containsKey(uvPoints[0])) {
                testingMap.put(uvPoints[0], function.computeOutput(uvPoints[0]));
            }

            if (!testingMap.containsKey(uvPoints[1])) {
                testingMap.put(uvPoints[1], function.computeOutput(uvPoints[1]));
            }

            globalLipschitzConstant = computeGlobalLipschitzConstant(globalLipschitzConstant,
                    currentOptimalCube,
                    uvPoints[0],
                    uvPoints[1]);

            divideOptimalHyperCubeIntoThreeSubHyperCubes(currentOptimalCube, uvPoints[0], uvPoints[1]);

            setCurrentApproximation(getOptimalApproximation());

            setIterationCount(getIterationCount() + 1);
        }

        function.setVariables(getCurrentApproximation());
    }

    private void divideOptimalHyperCubeIntoThreeSubHyperCubes(SimpleHyperCube optHyperCube,
                                                              DenseVector uPoint,
                                                              DenseVector vPoint)   {
        searchArea.remove(optHyperCube);

        searchArea.add(new SimpleHyperCube(uPoint.deepCopy(), vPoint.deepCopy()));
        searchArea.add(new SimpleHyperCube(optHyperCube.getAlphaPoint().deepCopy(), vPoint.deepCopy()));
        searchArea.add(new SimpleHyperCube(uPoint.deepCopy(), optHyperCube.getBetaPoint().deepCopy()));
    }

    private double computeGlobalLipschitzConstant(double currentGlobalLipschitzConstant,
                                                  SimpleHyperCube optHyperCube,
                                                  DenseVector uPoint,
                                                  DenseVector vPoint)   {
        DenseVector alphaPoint = optHyperCube.getAlphaPoint(),
                betaPoint = optHyperCube.getBetaPoint();

        double alphaBetaValue = computeLipschitzConstant(alphaPoint, betaPoint),
                alphaUValue = computeLipschitzConstant(alphaPoint, uPoint),
                alphaVValue = computeLipschitzConstant(alphaPoint, vPoint),
                betaUValue = computeLipschitzConstant(betaPoint, uPoint),
                betaVValue = computeLipschitzConstant(betaPoint, vPoint),
                uvValue = computeLipschitzConstant(uPoint, vPoint);

        return Arrays
                .stream(new double[]{currentGlobalLipschitzConstant, alphaBetaValue, alphaUValue,
                        alphaVValue, betaUValue, betaVValue, uvValue})
                .max()
                .getAsDouble();
    }

    private double computeFunctionOutput(DenseVector point)   {
        return (testingMap.get(point) == null) ? (getFunction().computeOutput(point)) : (testingMap.get(point));
    }

    private double computeLipschitzConstant(DenseVector alphaPoint, DenseVector betaPoint)   {
        double alphaValue = computeFunctionOutput(alphaPoint),
                betaValue = computeFunctionOutput(betaPoint);

        return (Math.abs(alphaValue - betaValue))/(alphaPoint.deepCopy().substract(betaPoint).getEuclidNorm());
    }

    private DenseVector[] generateUAndVPoints(SimpleHyperCube currentOptimalCube)   {
        IScalarFunction fun = getFunction();

        DenseVector alphaPoint = currentOptimalCube.getAlphaPoint(),
                betaPoint = currentOptimalCube.getBetaPoint();

        int uvIndex= 0;
        double maxValue = Double.NEGATIVE_INFINITY;

        for(int index = 0; index < fun.getInputDimensionSize(); index++) {
            double value = Math.abs(betaPoint.getValue(index) - alphaPoint.getValue(index));

            if (value > maxValue) {
                uvIndex = index;
                maxValue = value;
            }
        }

        double uValue = alphaPoint.getValue(uvIndex) + (2.0/3.0)*(betaPoint.getValue(uvIndex) - alphaPoint.getValue(uvIndex));
        DenseVector uPoint = alphaPoint
                .deepCopy()
                .setValue(uvIndex,  uValue);

        double vValue = betaPoint.getValue(uvIndex) + (2.0/3.0)*(alphaPoint.getValue(uvIndex) - betaPoint.getValue(uvIndex));
        DenseVector vPoint = betaPoint
                .deepCopy()
                .setValue(uvIndex,  vValue);

        return new DenseVector[]{uPoint, vPoint};
    }

    private DenseVector getOptimalApproximation() {
        return testingMap.entrySet()
                .stream()
                .min((m0,m1) -> {
                    if (m0.getValue() > m1.getValue()) return 1;
                    if (m1.getValue() > m0.getValue()) return -1;
                    return 0;
                })
                .get()
                .getKey();
    }

    private SimpleHyperCube computeRParameterAtSearchAreaAndReturnOptCube(double globalLipschitzConstant)   {
        double mu = (rConstant + cConstant/(getIterationCount() + 1))*Math.max(globalLipschitzConstant, 1.0E-06),
                currentRConstant = Double.NEGATIVE_INFINITY;

        SimpleHyperCube optimalCube = null;

        for(SimpleHyperCube cube: searchArea) {
            double rValue = computeRParameter(mu, cube);

            if (rValue > currentRConstant) {
                optimalCube = cube;
                currentRConstant = rValue;
            }
        }

        return optimalCube;
    }

    private double computeRParameter(double mu, SimpleHyperCube cube)   {
        DenseVector alphaPoint = cube.getAlphaPoint(), betaPoint = cube.getBetaPoint();

        double alphaValue = computeFunctionOutput(alphaPoint),
                betaValue = computeFunctionOutput(betaPoint),
                muABNorm = mu*alphaPoint.deepCopy().substract(betaPoint).getEuclidNorm();

        return muABNorm + (Math.pow(alphaValue - betaValue,2.0))/(muABNorm) - 2.0*(alphaValue + betaValue);
    }
}
