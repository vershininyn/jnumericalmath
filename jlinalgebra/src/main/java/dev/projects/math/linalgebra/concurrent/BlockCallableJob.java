package dev.projects.math.linalgebra.concurrent;

import lombok.AllArgsConstructor;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

@AllArgsConstructor
public class BlockCallableJob<TCallableResult, TJobConfiguration> implements Callable<TCallableResult> {
    private TJobConfiguration jobConfiguration = null;
    private IBlockOperation<TCallableResult, TJobConfiguration> operation = null;

    private CountDownLatch signalToStartProcessingCountDownLatch = null,
            signalToEndProcessingCountDownLatch = null;

    @Override
    public TCallableResult call() {
        try {
            signalToStartProcessingCountDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        TCallableResult result = operation.execute(jobConfiguration);

        signalToEndProcessingCountDownLatch.countDown();

        return result;
    }
}
