package dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder;

import dev.projects.math.jnummath.jdatasources.supervised.MultidimensionalSupervisedDataSet;
import dev.projects.math.jnummath.jdatasources.supervised.OneDimensionalSupervisedDataSet;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;
import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.FirstOrderOptimizationResults;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.learning.FirstOrderLearningCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.learning.IFirstOrderLearningUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.impl.learning.LearningFirstOrderUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.base.StopCriteriaQuality;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.FirstOrderKachmazhDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.FirstOrderMultistepDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.FirstOrderPairwiseDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.fconditioned.FirstOrderFunctionallyConditionedMultistepDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.fconditioned.IFunctionallyConditionedTransformCoefficientFunction;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.fconditioned.impl.HyperbolicTangentConditionedTransformCoefficientFunction;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.fconditioned.impl.SignumConditionedTransformCoefficientFunction;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.impl.*;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionInternalDirectionCorrectionOperator;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionMatrixCorrectionOperator;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.impl.idc.KachmazhInternalDirectionCorrectionOperator;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.impl.spacetension.AlphaBetaSpaceTensionMatrixCorrectionOperator;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.impl.spacetension.AlphaSpaceTensionMatrixCorrectionOperator;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.impl.spacetension.OrthogonalSpaceTensionMatrixCorrectionOperator;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.impl.learning.IFirstOrderLearningLinearSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.impl.learning.LearningFirstOrderLinearSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.IScalarFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.ICQPScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.vector.impl.ZeroTransformVectorFunction;
import dev.projects.math.jnummath.jtransformations.model.IScalarDifferentiableModel;
import dev.projects.math.jnummath.jtransformations.model.impl.HyperPlaneModel;
import dev.projects.math.jnummath.jtransformations.tabulator.IFunctionallyConditionedGrid;
import dev.projects.math.jnummath.jtransformations.tabulator.IUniversalFunctionsTabulator;
import dev.projects.math.jnummath.jtransformations.tabulator.grids.FunctionallyConditionedHyperGrid;
import dev.projects.math.jnummath.jtransformations.tabulator.impl.UniversalFunctionsTabulator;
import dev.projects.math.jnummath.jtransformations.tabulator.interval.ITemplateInterval;
import dev.projects.math.jnummath.jtransformations.tabulator.interval.impl.SimpleTemplateInterval;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public abstract class AbstractFunctionalTests {
    private IScalarDifferentiableFunction fun;

    private FirstOrderLearningCriticalOptimizationAttributes attrib;

    private IFirstOrderLearningLinearSearchAlgorithm lsAlgorithm;

    private List<IFirstOrderLearningDirectionSearchAlgorithm> dsAlgsList = new LinkedList();

    @BeforeEach
    public void setUp() {
        ITemplateInterval templateInterval = SimpleTemplateInterval.getInstance(-1.0D, 1.0D, 1024L);

        IFunctionallyConditionedGrid
                grid = FunctionallyConditionedHyperGrid.getInstance();

        Set<DenseVector> list = grid.setTemplateInterval(templateInterval)
                .setTransformationFunction(ZeroTransformVectorFunction.getInstance())
                .generateGrid();

        IScalarFunction testedFunction = new ICQPScalarDifferentiableFunction(DenseVector.getInstance(4, 0.0));

        IUniversalFunctionsTabulator tabulator = UniversalFunctionsTabulator.getInstance(grid, testedFunction);

        String temporalFile = System.getProperty("user.dir")
                + File.separator
                + UUID.randomUUID();

        Path temporalPath = Path.of(temporalFile);

        try (OutputStream temporalFileDataStream = Files.newOutputStream(temporalPath,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE);

             OutputStream outputStream = new BufferedOutputStream(temporalFileDataStream);) {

            tabulator.performTabulate(outputStream, ',');
        } catch (Exception ex) {
            throw new LoggableException(ex);
        }

        //TODO: Check logic of the test What is INPUT AND OUTPUT???

        MultidimensionalSupervisedDataSet multiDataSet = new MultidimensionalSupervisedDataSet();

        try (InputStream dataStream = Files.newInputStream(temporalPath)) {
            multiDataSet.read(dataStream, 1);
        } catch (Exception e) {
            throw new LoggableException(e);
        }

//        MultidimensionalSupervisedDataSet multiDataSet = new MultidimensionalSupervisedDataSet();

//        try (InputStream dataStream = getClass().getClassLoader().getResourceAsStream("data.csv");) {
//            multiDataSet.read(dataStream, 1);
//        } catch (Exception e) {
//            throw new LoggableException(e);
//        }

        fun = getFunctional(multiDataSet.sliceToOneDimension(0),
                new HyperPlaneModel(DenseVector.getInstance(4, 0.5D), DenseVector.getInstance(4, 0.5D)));

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, getTaskQMaxStep());

        ISpaceTensionMatrixCorrectionOperator
                alphaBetaOperator = new AlphaBetaSpaceTensionMatrixCorrectionOperator(30.0, 0.2),
                alphaOperator = new AlphaSpaceTensionMatrixCorrectionOperator(1.5),
                orthogonalOperator = new OrthogonalSpaceTensionMatrixCorrectionOperator(5.0);

        ISpaceTensionInternalDirectionCorrectionOperator
                kachmazhIDCO = new KachmazhInternalDirectionCorrectionOperator();

        dsAlgsList.add(new FirstOrderNYVSpaceTensionDirectionSearchAlgorithm(alphaOperator, kachmazhIDCO));
        dsAlgsList.add(new FirstOrderNYVSpaceTensionDirectionSearchAlgorithm(alphaBetaOperator, kachmazhIDCO));
        dsAlgsList.add(new FirstOrderNYVSpaceTensionDirectionSearchAlgorithm(orthogonalOperator, kachmazhIDCO));

        dsAlgsList.add(new FirstOrderKachmazhDirectionSearchAlgorithm(0.8));
        dsAlgsList.add(new FirstOrderMultistepDirectionSearchAlgorithm());
        dsAlgsList.add(new FirstOrderPairwiseDirectionSearchAlgorithm());

        dsAlgsList.add(new FirstOrderNHGSpaceTensionDirectionSearchAlgorithm(alphaOperator, kachmazhIDCO));
        dsAlgsList.add(new FirstOrderNHGSpaceTensionDirectionSearchAlgorithm(alphaBetaOperator, kachmazhIDCO));
        dsAlgsList.add(new FirstOrderNHGSpaceTensionDirectionSearchAlgorithm(orthogonalOperator, kachmazhIDCO));

        dsAlgsList.add(new FirstOrderKachmazhSpaceTensionDirectionSearchAlgorithm(alphaOperator));
        dsAlgsList.add(new FirstOrderMultistepSpaceTensionDirectionSearchAlgorithm(alphaOperator));
        dsAlgsList.add(new FirstOrderPairwiseSpaceTensionDirectionSearchAlgorithm(alphaOperator));

        dsAlgsList.add(new FirstOrderKachmazhSpaceTensionDirectionSearchAlgorithm(alphaBetaOperator));
        dsAlgsList.add(new FirstOrderMultistepSpaceTensionDirectionSearchAlgorithm(alphaBetaOperator));
        dsAlgsList.add(new FirstOrderPairwiseSpaceTensionDirectionSearchAlgorithm(alphaBetaOperator));

        dsAlgsList.add(new FirstOrderKachmazhSpaceTensionDirectionSearchAlgorithm(orthogonalOperator));
        dsAlgsList.add(new FirstOrderMultistepSpaceTensionDirectionSearchAlgorithm(orthogonalOperator));
        dsAlgsList.add(new FirstOrderPairwiseSpaceTensionDirectionSearchAlgorithm(orthogonalOperator));

        dsAlgsList.add(new FirstOrderOrthogonalSpaceTensionDirectionSearchAlgorithm(orthogonalOperator));
        dsAlgsList.add(new FirstOrderOrthogonalSpaceTensionDirectionSearchAlgorithm(alphaOperator));
        dsAlgsList.add(new FirstOrderOrthogonalSpaceTensionDirectionSearchAlgorithm(alphaBetaOperator));

        IFunctionallyConditionedTransformCoefficientFunction fcSignumFunction = new SignumConditionedTransformCoefficientFunction(),
                fcSigmoidFunction = new HyperbolicTangentConditionedTransformCoefficientFunction(1.0);

        dsAlgsList.add(new FirstOrderFunctionallyConditionedMultistepDirectionSearchAlgorithm(fcSignumFunction));
        dsAlgsList.add(new FirstOrderFunctionallyConditionedMultistepDirectionSearchAlgorithm(fcSigmoidFunction));
    }

    @Test
    public void directionSearchAlgorithmsTest() {
        final Map<StopCriteriaQuality, Double> stopQuality = new HashMap<>(2) {{
            put(StopCriteriaQuality.RANK_ONE, Double.valueOf(1.0E-250));
            put(StopCriteriaQuality.RANK_TWO, Double.valueOf(1.0));
        }};

        for (IFirstOrderLearningDirectionSearchAlgorithm dsAlgorithm : dsAlgsList) {
            IFirstOrderLearningUnconstrainedOptimizationAlgorithm optAlgorithm =
                    new LearningFirstOrderUnconstrainedOptimizationAlgorithm(fun, attrib, lsAlgorithm, dsAlgorithm);

            FirstOrderOptimizationResults optResult = optAlgorithm.minimize();

            System.out.println(optResult);

            if (optResult.isSuccessfullyStopped()) {
                double quality = stopQuality.get(optResult.getStopCriteriaQuality());

                DenseVector optimum = optResult.getOptimalApproximation();

                Assertions.assertTrue(Math.abs(Math.round(optimum.getValue(0)) - 4.0) <= quality);
                Assertions.assertTrue(Math.abs(Math.round(optimum.getValue(1)) - 7.0) <= quality);
                Assertions.assertTrue(Math.abs(Math.round(optimum.getValue(2)) - 8.0) <= quality);
            }
        }
    }

    protected abstract IScalarDifferentiableFunction getFunctional(OneDimensionalSupervisedDataSet dataSet,
                                                                   IScalarDifferentiableModel model);

    protected double getTaskQMaxStep() {
        return 1.0 + 1.0E-05;
    }
}
