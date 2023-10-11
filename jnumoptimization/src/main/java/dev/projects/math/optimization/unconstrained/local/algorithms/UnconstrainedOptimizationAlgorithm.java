package dev.projects.math.optimization.unconstrained.local.algorithms;

import dev.projects.math.linalgebra.DenseVector;
import dev.projects.math.optimization.unconstrained.local.algorithms.base.StopCriteria;
import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderLocalCriticalOptimizationAttributes;
import dev.projects.math.optimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.math.optimization.unconstrained.local.base.StopCriteriaEnumeration;
import dev.projects.math.optimization.unconstrained.local.directionsearch.IDirectionSearchAlgorithm;
import dev.projects.math.optimization.unconstrained.local.linearsearch.base.ILinearSearchAlgorithm;
import dev.projects.math.transformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

public abstract class UnconstrainedOptimizationAlgorithm<TFunction extends IScalarFunction,
        TLinearSearchAlgorithm extends ILinearSearchAlgorithm<TFunction>,
        TDirectionSearchAlgorithm extends IDirectionSearchAlgorithm<TFunction>,
        TCriticalOptimizationAttributes extends ZeroOrderLocalCriticalOptimizationAttributes,
        TOptimizationResults extends ZeroOrderOptimizationResults> {

    private volatile boolean timeIsUp = false;

    private int iterationCount = 0;

    @Getter
    private int repeatedFunctionOutputCount = 0;

    private double lastFunctionOutput = Double.MIN_VALUE;
    private final double repeatedFunctionOutputEpsilon = 1.0E-10;

    private final Timer shutdownTimer = new Timer("ShutdownOptimizationProcessTimer", true);

    @Getter
    private TFunction function;

    @Getter
    private TCriticalOptimizationAttributes criticalOptimizationAttributes;

    @Getter
    private TLinearSearchAlgorithm linearSearchAlgorithm;

    @Getter
    private TDirectionSearchAlgorithm directionSearchAlgorithm;

    private DenseVector currentApproximation;

    @Getter
    private StopCriteria stopCriteria;

    public UnconstrainedOptimizationAlgorithm(TFunction function,
                                              TCriticalOptimizationAttributes attributes,
                                              TLinearSearchAlgorithm lsAlgorithm,
                                              TDirectionSearchAlgorithm dsAlgorithm) throws LoggableException {

        this(attributes, lsAlgorithm, dsAlgorithm);
        setFunction(function);
    }

    public UnconstrainedOptimizationAlgorithm(TCriticalOptimizationAttributes attributes,
                                              TLinearSearchAlgorithm lsAlgorithm,
                                              TDirectionSearchAlgorithm dsAlgorithm) throws LoggableException {
        setCriticalOptimizationAttributes(attributes);
        setLinearSearchAlgorithm(lsAlgorithm);
        setDirectionSearchAlgorithm(dsAlgorithm);
    }

    public void setCriticalOptimizationAttributes(TCriticalOptimizationAttributes attributes) throws LoggableException {
        if (attributes == null) throw new LoggableException("Unacceptable attributes");

        criticalOptimizationAttributes = attributes;
    }

    public void setLinearSearchAlgorithm(TLinearSearchAlgorithm lsAlgorithm) throws LoggableException {
        if (lsAlgorithm != null) {
            lsAlgorithm.reset();
        }

        linearSearchAlgorithm = lsAlgorithm;
    }

    public void setDirectionSearchAlgorithm(TDirectionSearchAlgorithm dsAlgorithm) throws LoggableException {
        if (dsAlgorithm != null) {
            dsAlgorithm.reset();
        }

        directionSearchAlgorithm = dsAlgorithm;
    }

    public void setFunction(TFunction function) throws LoggableException {
        if (function == null) throw new LoggableException("Unacceptable function");

        function.reset();

        this.function = function;
    }

    public abstract String getAlgorithmName();

    public TOptimizationResults minimize() throws LoggableException {
        TFunction fun = getFunction();

        if (fun == null) throw new LoggableException("Function obj must not be null");

        TCriticalOptimizationAttributes attrb = getCriticalOptimizationAttributes();

        initializeLocalVariables();

        DenseVector currentApproximation = fun.getVariables().deepCopy();
        setCurrentApproximation(currentApproximation);

        initializeOptimizationProcess(getFunction());
        shutdownTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeIsUp = true;
            }
        }, attrb.getShutdownMillisecondsTime());

        Duration startTime = startTimeMeasurement();
        doOptimizationCycle();
        Duration endTimeMeasurement = endTimeMeasurement(startTime);

        deinitializedOptimizationProcess();

        return getOptimizationResults(getIterationCount(), endTimeMeasurement);
    }

    public Duration startTimeMeasurement() {
        return Duration.ofNanos(System.nanoTime());
    }

    public Duration endTimeMeasurement(Duration startedTime) {
        return Duration.ofNanos(System.nanoTime()).minus(startedTime);
    }

    public void initializeOptimizationProcess(TFunction function) throws LoggableException {

    }

    public void deinitializedOptimizationProcess() throws LoggableException {
    }

    public abstract TOptimizationResults getOptimizationResults(int actualIterationCount, Duration period) throws LoggableException;

    protected void doOptimizationCycle() throws LoggableException {
        DenseVector approximation = getCurrentApproximation().deepCopy();

        while (!checkStopCondition()) {
            approximation = approximation.add(computeDelta(getIterationCount()));

            setCurrentApproximation(approximation);

            setIterationCount(getIterationCount() + 1);
        }

        getFunction().setVariables(approximation);
    }

    protected boolean checkStopCondition() throws LoggableException {
        TCriticalOptimizationAttributes attrb = getCriticalOptimizationAttributes();
        TFunction fun = getFunction();

        long funComputeCount = fun.getOutputComputeCount();

        return (checkCriteria(StopCriteriaEnumeration.STOPPED_BY_DENOMINATED_ITERATION_COUNT,
                "denominatedIterationCount {d= " + getIterationDenominator() + "}",
                (iterationCount / getIterationDenominator()) >= attrb.getMaxIterationCount())
                || checkCriteria(StopCriteriaEnumeration.STOPPED_BY_FUNCTION_COMPUTE_COUNT,
                "funComputeCount >= " + attrb.getMaxFunctionComputeCount(),
                funComputeCount >= attrb.getMaxFunctionComputeCount())
                || checkCriteria(StopCriteriaEnumeration.STOPPED_BY_TIME,
                "opt process time [" + attrb.getShutdownMillisecondsTime() + " ms] is up",
                timeIsUp)
                || checkRepeatedFunctionOutput(fun, attrb)
                || checkFunctionOutput(fun, attrb)
                || checkOtherCondition());
    }

    protected int getIterationDenominator() {
        return 1;
    }

    protected abstract boolean checkOtherCondition() throws LoggableException;

    protected abstract DenseVector computeDelta(int actualIterationCount) throws LoggableException;

    protected DenseVector getCurrentApproximation() {
        return currentApproximation.deepCopy();
    }

    protected void setCurrentApproximation(DenseVector approximation) {
        currentApproximation = approximation.deepCopy();
    }

    protected void setStopCriteria(StopCriteria stopCriteria) {
        this.stopCriteria = stopCriteria;
    }

    protected boolean checkFunctionOutput(TFunction function, TCriticalOptimizationAttributes attributes) throws LoggableException {
        double criteria = Math.abs(function.computeOutput() - attributes.getExpectedExtremum());

        return checkCriteria(StopCriteriaEnumeration.STOPPED_BY_CRITICAL_FUNCTION_VALUE,
                "|F[k] - F*| <= " + attributes.getEpsilonFunctionCriticalOutput(),
                criteria <= attributes.getEpsilonFunctionCriticalOutput());
    }

    protected boolean checkCriteria(StopCriteriaEnumeration stopCriteriaEnum, String criteriaName, boolean condition) {
        if (condition) setStopCriteria(new StopCriteria(criteriaName, stopCriteriaEnum));

        return condition;
    }

    protected void setIterationCount(int iterationCount) {
        this.iterationCount = iterationCount;
    }

    protected int getIterationCount() {
        return iterationCount;
    }

    private void checkRepeatedFunctionComputeCountTimes() throws LoggableException {
        TFunction fun = getFunction();

        double currentFunctionOutput = fun.computeOutput(getCurrentApproximation());

        //TODO: не верная логика repeated count

        if (Math.abs(currentFunctionOutput - lastFunctionOutput) < repeatedFunctionOutputEpsilon) {
            repeatedFunctionOutputCount++;
            lastFunctionOutput = currentFunctionOutput;
        } else {
            repeatedFunctionOutputCount = 0;
            lastFunctionOutput = Double.MIN_VALUE;
        }
    }

    private boolean checkRepeatedFunctionOutput(TFunction fun, TCriticalOptimizationAttributes attrb) throws LoggableException {
        checkRepeatedFunctionComputeCountTimes();

        return checkCriteria(StopCriteriaEnumeration.STOPPED_BY_MAX_REPEATED_FUNCTIONS_OUTPUT,
                "abs(F[k] - F[k-1]) < " + repeatedFunctionOutputEpsilon + " > times[" + attrb.getMaxFunctionOutputRepeatedCount() + "]",
                repeatedFunctionOutputCount >= attrb.getMaxFunctionOutputRepeatedCount());
    }

    private void initializeLocalVariables() throws LoggableException {
        setIterationCount(0);
        repeatedFunctionOutputCount = 0;
        lastFunctionOutput = 0;
        timeIsUp = false;
    }
}
