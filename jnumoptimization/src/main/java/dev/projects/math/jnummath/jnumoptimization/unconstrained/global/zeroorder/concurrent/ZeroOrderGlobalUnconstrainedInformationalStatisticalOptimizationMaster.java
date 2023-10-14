package dev.projects.math.jnummath.jnumoptimization.unconstrained.global.zeroorder.concurrent;

import dev.projects.math.jnummath.jnumoptimization.unconstrained.global.zeroorder.ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.zeroorder.base.ZeroOrderOptimizationResults;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.global.base.SimpleHyperCube;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.global.zeroorder.base.IZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.global.zeroorder.base.ZeroOrderGlobalCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;
import dev.projects.utils.exception.LoggableException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

public class ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationMaster {

    private ExecutorService threadPool;

    @Getter
    @Setter
    private SimpleHyperCube originalHyperCube;

    @Getter @Setter
    private IScalarFunction function;

    public ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationMaster(IScalarFunction function,
                                                                                  SimpleHyperCube hyperCube)   {
        if (function == null) throw new LoggableException("Unacceptable function");
        if (hyperCube == null) throw new LoggableException("Unacceptable hyper cube");

        setFunction(function);
        setOriginalHyperCube(hyperCube);
    }

    public ZeroOrderOptimizationResults doConcurrentGlobalMinimization(ZeroOrderGlobalCriticalOptimizationAttributes attributes,
                                                                       int centersCount,
                                                                       double cubeEdgeLength,
                                                                       double rConstant,
                                                                       double cConstant)   {
        if (attributes == null) throw new LoggableException("Unacceptable attributes");
        if (cubeEdgeLength <= 0.0) throw new LoggableException("Unacceptable cube edge length");
        if (centersCount <= 0) throw new LoggableException("Unacceptable centers count");
        if (rConstant <= 1.0) throw new LoggableException("Unacceptable R constant");
        if (cConstant <= 0.0) throw new LoggableException("Unacceptable C constant");

        IScalarFunction fun = getFunction();
        fun.reset();

        List<SimpleHyperCube> cubesList = getOriginalHyperCube().divideIntoSubHyperCubes(fun.getInputDimensionSize(),
                centersCount, (-1.0)*(cubeEdgeLength/2.0), cubeEdgeLength/2.0);

        threadPool = Executors.newFixedThreadPool(cubesList.size(), new CubeThreadFactory());

        List<Future<ZeroOrderOptimizationResults>> futureList = new ArrayList<>(128);

        for(SimpleHyperCube subCube: cubesList) {
            IZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm
                    optAlg = new ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationAlgorithm(rConstant,
                    cConstant,
                    (IScalarFunction) fun.deepCopy(),
                    (ZeroOrderGlobalCriticalOptimizationAttributes)attributes.deepCopy(),
                    subCube);

            ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationSlave
                    slave = new ZeroOrderGlobalUnconstrainedInformationalStatisticalOptimizationSlave(optAlg);

            futureList.add(threadPool.submit(slave));
        }

        List<ZeroOrderOptimizationResults> results = new ArrayList<>(128);

        try {
            for(Future<ZeroOrderOptimizationResults> future: futureList) {
                results.add(future.get());
            }
        } catch (InterruptedException | ExecutionException ex) {
            throw new LoggableException(ex);
        }

        threadPool.shutdown();

        return getOptimalResult(results);
    }

    private ZeroOrderOptimizationResults getOptimalResult(List<ZeroOrderOptimizationResults> resultList)   {
        ZeroOrderOptimizationResults optimalResult = null;

        double currentFunctionOutput = Double.POSITIVE_INFINITY;

        for(ZeroOrderOptimizationResults currentResult: resultList) {
            double value = currentResult.getActualFunctionOutput();

            if ((currentResult.isSuccessfullyStopped()) && (value < currentFunctionOutput)) {
                optimalResult = currentResult;
                currentFunctionOutput = value;
            }
        }

        return optimalResult;
    }

    private class CubeThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread cubeThread = new Thread(r);

            cubeThread.setDaemon(true);
            cubeThread.setPriority(Thread.MAX_PRIORITY);

            return cubeThread;
        }
    }
}
