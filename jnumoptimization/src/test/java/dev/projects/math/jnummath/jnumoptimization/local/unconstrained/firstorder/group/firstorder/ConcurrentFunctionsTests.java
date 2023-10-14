package dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.group.firstorder;


import dev.projects.math.jnummath.jlinalgebra.dense.DenseVector;
import dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.group.firstorder.learning.LearningFirstOrderFunctionsTestingGroupMap;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.learning.FirstOrderLearningCriticalOptimizationAttributes;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.learning.IFirstOrderLearningUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.local.unconstrained.firstorder.group.firstorder.learning.LearningFirstOrderConcurrentFunctionsGroupTestingMaster;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.IUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.FirstOrderOptimizationResults;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.base.FirstOrderTotalStatisticsOptimizationResults;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.algorithms.firstorder.impl.learning.LearningFirstOrderUnconstrainedOptimizationAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.FirstOrderKachmazhDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.FirstOrderMultistepDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.FirstOrderPairwiseDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.base.IFirstOrderLearningDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.fconditioned.FirstOrderFunctionallyConditionedMultistepDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.fconditioned.IFunctionallyConditionedTransformCoefficientFunction;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.fconditioned.impl.HyperbolicTangentConditionedTransformCoefficientFunction;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.impl.FirstOrderKachmazhSpaceTensionDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.impl.FirstOrderMultistepSpaceTensionDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.impl.FirstOrderNHGSpaceTensionDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.impl.FirstOrderNYVSpaceTensionDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.impl.FirstOrderOrthogonalSpaceTensionDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.impl.FirstOrderPairwiseSpaceTensionDirectionSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionInternalDirectionCorrectionOperator;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.base.ISpaceTensionMatrixCorrectionOperator;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.impl.idc.KachmazhInternalDirectionCorrectionOperator;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.impl.spacetension.AlphaBetaSpaceTensionMatrixCorrectionOperator;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.impl.spacetension.AlphaSpaceTensionMatrixCorrectionOperator;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.directionsearch.firstorder.impl.learning.spacetension.operators.impl.spacetension.OrthogonalSpaceTensionMatrixCorrectionOperator;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.impl.learning.FirstOrderLearningLinearSearchOptimalParametersDTO;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.impl.learning.IFirstOrderLearningLinearSearchAlgorithm;
import dev.projects.math.jnummath.jnumoptimization.unconstrained.local.linearsearch.firstorder.impl.learning.LearningFirstOrderLinearSearchAlgorithm;
import dev.projects.math.jnummath.jtransformations.function.scalar.differentiable.IScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.GeneralizedRosenbrockScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.GofNScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.GrivenkaScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.ICQPScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.MaxLScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.MaxQScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.MxCScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.SchwefelScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.SmDScalarDifferentiableFunction;
import dev.projects.math.jnummath.jtransformations.function.scalar.impl.SquaresOfCoordinateSumScalarDifferentiableFunction;
import dev.projects.utils.exception.LoggableException;
import j2html.tags.specialized.DivTag;
import j2html.tags.specialized.TrTag;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static j2html.TagCreator.attrs;
import static j2html.TagCreator.body;
import static j2html.TagCreator.div;
import static j2html.TagCreator.document;
import static j2html.TagCreator.each;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.table;
import static j2html.TagCreator.tbody;
import static j2html.TagCreator.td;
import static j2html.TagCreator.thead;
import static j2html.TagCreator.tr;

public class ConcurrentFunctionsTests {

    @Test
    public void smoothFunctionsWithoutTensionConcurrentTest()   {
        int varsCount = 50;

        functionsConcurrentTestAndLogResult(getSmoothFunctionsGroup(varsCount, 50.0),
                getLearningOptimizationAlgorithmsWithoutTensionGroup(),
                "not_tension_smooth_N=" + varsCount);
    }

    @Test
    public void notSmoothFunctionsWithoutTensionConcurrentTest()   {
        int varsCount = 50;

        functionsConcurrentTestAndLogResult(getNotSmoothFunctionsGroup(varsCount, 50.0),
                getLearningOptimizationAlgorithmsWithoutTensionGroup(),
                "not_tension_not_smooth_N=" + varsCount);
    }

    @Test
    public void smoothFunctionsWithTensionConcurrentTest()   {
        int varsCount = 100;

        //A Learning FOOA {ls= A Learning FOLSA {qMin= 0.8,qMax= 3.0},ds= A Kachmazh STCDSA {sto=A alpha and beta sto,alpha={30.0},beta={0.2}}}

        functionsConcurrentTestAndLogResult(getSmoothFunctionsGroup(varsCount, 50.0),
                getLearningOptimizationAlgorithmsWithTensionGroup(),
                "tension_smooth_N=" + varsCount);
    }

    @Test
    public void notSmoothFunctionsWithTensionConcurrentTest()   {
        int varsCount = 50;

        functionsConcurrentTestAndLogResult(getNotSmoothFunctionsGroup(varsCount, 50.0),
                getLearningOptimizationAlgorithmsWithTensionGroup(),
                "tension_not_smooth_N=" + varsCount);
    }

    @Test
    public void smoothFunctionsWithNYVConcurrentTest()   {
        int varsCount = 50;

        //A Learning FOOA {ls= A Learning FOLSA {qMin= 0.8,qMax= 6.0},
        // ds= NYV STDSA {sto=A alpha and beta sto,alpha={10.00001},beta={0.5},idc= A Kachmazh IDCO}}

        functionsConcurrentTestAndLogResult(getSmoothFunctionsGroup(varsCount, 50.0), getNYVGridAlgorithms(), "nyv_smooth_N=" + varsCount);
    }

    @Test
    public void notSmoothFunctionsWithNYVConcurrentTest()   {
        int varsCount = 50;

        //winner [min]: A Learning FOOA {ls= A Learning FOLSA {qMin= 0.8,qMax= 2.0,gOne=0.2,gTwo=0.2,gThree=0.1,gFour=0.1,gFive=0.1}
        // ,ds= NYV STDSA {sto=A alpha and beta sto,alpha={30.00001},beta={0.5},idc= A Kachmazh IDCO}}

        functionsConcurrentTestAndLogResult(getNotSmoothFunctionsGroup(varsCount, 50.0),
                getNYVGridAlgorithms(), "nyv_not_smooth_N=" + varsCount);
    }

    @Test
    public void notSmoothFunctionsWithNYVConcurrentTestWithGammaOptimalParameters()   {
        int varsCount = 50;

        functionsConcurrentTestAndLogResult(getNotSmoothFunctionsGroup(varsCount, 50.0),
                getNYVGridAlgorithmsWithGammaOptions(), "nyv_with_gamma_not_smooth_N=" + varsCount);
    }

    @Test
    public void hyperbolicTangentOptimalParametersTest()   {
        int varsCount = 50;

        functionsConcurrentTestAndLogResult(getSmoothFunctionsGroup(varsCount, 50.0),
                getAlgorithmsForHyperbolicTest(), "multistep_hyberbolic_smooth_N="+varsCount);
    }

    private List<IFirstOrderLearningUnconstrainedOptimizationAlgorithm> getAlgorithmsForHyperbolicTest()   {
        List<IFirstOrderLearningUnconstrainedOptimizationAlgorithm> group = new ArrayList<>(1024);

        for (double tanhCoeff = 0.001; tanhCoeff <= 0.5; tanhCoeff += 0.01) {
            FirstOrderLearningCriticalOptimizationAttributes
                    attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0,
                    1.0E-05);

            IFunctionallyConditionedTransformCoefficientFunction
                    fcHyberbolicTangentFunction = new HyperbolicTangentConditionedTransformCoefficientFunction(tanhCoeff);

            IFirstOrderLearningLinearSearchAlgorithm
                    lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(
                    10.0,
                    0.8,
                    3.0);

            IFirstOrderLearningDirectionSearchAlgorithm
                    dsAlgorithm = new FirstOrderFunctionallyConditionedMultistepDirectionSearchAlgorithm(fcHyberbolicTangentFunction);

            IFirstOrderLearningUnconstrainedOptimizationAlgorithm optAlgorithm =
                    new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm);

            group.add(optAlgorithm);
        }

        return group;
    }

    private List<IFirstOrderLearningUnconstrainedOptimizationAlgorithm> getNYVGridAlgorithmsWithGammaOptions()   {
        List<IFirstOrderLearningUnconstrainedOptimizationAlgorithm> group = new ArrayList<>(1024);

        for (double gammaOne = 0.1; gammaOne <= 0.5; gammaOne += 0.1) {
            for (double gammaTwo = 0.1; gammaTwo <= 0.5; gammaTwo += 0.1) {
                for (double gammaThree = 0.1; gammaThree <= 0.5; gammaThree += 0.1) {
                    for (double gammaFour = 0.1; gammaFour <= 0.5; gammaFour += 0.1) {
                        for (double gammaFive = 0.1; gammaFive <= 0.5; gammaFive += 0.1) {
                            ISpaceTensionInternalDirectionCorrectionOperator
                                    KachmazhIDCO = new KachmazhInternalDirectionCorrectionOperator();

                            ISpaceTensionMatrixCorrectionOperator
                                    alphaBetaOperator = new AlphaBetaSpaceTensionMatrixCorrectionOperator(30.00001,
                                    0.5);

                            FirstOrderLearningLinearSearchOptimalParametersDTO
                                    gammaDTO = new FirstOrderLearningLinearSearchOptimalParametersDTO(gammaOne,
                                    gammaTwo,
                                    gammaThree,
                                    gammaFour,
                                    gammaFive);

                            FirstOrderLearningCriticalOptimizationAttributes
                                    attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0,
                                    1.0E-05);

                            IFirstOrderLearningLinearSearchAlgorithm
                                    lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(gammaDTO.deepCopy(),
                                    10.0,
                                    0.8,
                                    2.0);

                            IFirstOrderLearningDirectionSearchAlgorithm
                                    dsAlgorithm = new FirstOrderNYVSpaceTensionDirectionSearchAlgorithm(alphaBetaOperator.deepCopy(),
                                    KachmazhIDCO.deepCopy());

                            group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));
                        }
                    }
                }
            }
        }

        return group;
    }

    private List<IFirstOrderLearningUnconstrainedOptimizationAlgorithm> getNYVGridAlgorithms()   {
        List<IFirstOrderLearningUnconstrainedOptimizationAlgorithm> group = new ArrayList<>(256);

        for (double alpha = 10.00001; alpha < 50.0; alpha += 10.0) {
            for (double beta = 0.1; beta < 1.0; beta += 0.1) {
                for (double qmin = 0.8; qmin < 1.0; qmin += 0.05) {
                    for (double qmax = 2.0; qmax < 7.0; qmax += 1.0) {
                        ISpaceTensionInternalDirectionCorrectionOperator
                                KachmazhIDCO = new KachmazhInternalDirectionCorrectionOperator();

                        ISpaceTensionMatrixCorrectionOperator
                                alphaBetaOperator = new AlphaBetaSpaceTensionMatrixCorrectionOperator(alpha, beta);

                        FirstOrderLearningLinearSearchOptimalParametersDTO
                                gammaDto = new FirstOrderLearningLinearSearchOptimalParametersDTO();

                        FirstOrderLearningCriticalOptimizationAttributes
                                attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
                        IFirstOrderLearningLinearSearchAlgorithm
                                lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(gammaDto, 10.0, qmin, qmax);

                        IFirstOrderLearningDirectionSearchAlgorithm
                                dsAlgorithm = new FirstOrderNYVSpaceTensionDirectionSearchAlgorithm(alphaBetaOperator.deepCopy(), KachmazhIDCO.deepCopy());

                        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));
                    }
                }
            }
        }

        return group;
    }

    private void functionsConcurrentTestAndLogResult(List<IScalarDifferentiableFunction> funGroup,
                                                     List<IFirstOrderLearningUnconstrainedOptimizationAlgorithm> algGroup,
                                                     String suffix)   {
        LearningFirstOrderConcurrentFunctionsGroupTestingMaster master = new LearningFirstOrderConcurrentFunctionsGroupTestingMaster(funGroup, algGroup);
        List<LearningFirstOrderFunctionsTestingGroupMap> listOfMaps = master.performTestingAtAlgorithmsGroup();

        logResultsToFile(listOfMaps, suffix);
    }

    private List<IScalarDifferentiableFunction> getNotSmoothFunctionsGroup(int variablesCount, double scalFactor)   {
        List<IScalarDifferentiableFunction> group = new ArrayList<>(16);

        group.add(new GofNScalarDifferentiableFunction(DenseVector.getRandomizedVector(variablesCount, scalFactor)));
        group.add(new MaxLScalarDifferentiableFunction(DenseVector.getRandomizedVector(variablesCount, scalFactor)));
        group.add(new MaxQScalarDifferentiableFunction(DenseVector.getRandomizedVector(variablesCount, scalFactor)));
        group.add(new MxCScalarDifferentiableFunction(DenseVector.getRandomizedVector(variablesCount, scalFactor)));
        group.add(new SmDScalarDifferentiableFunction(DenseVector.getRandomizedVector(variablesCount, scalFactor)));

        return group;
    }

    private List<IScalarDifferentiableFunction> getSmoothFunctionsGroup(int variablesCount, double scalFactor)   {
        List<IScalarDifferentiableFunction> group = new ArrayList<>(16);

        group.add(new ICQPScalarDifferentiableFunction(DenseVector.getRandomizedVector(variablesCount, scalFactor)));
        group.add(new SquaresOfCoordinateSumScalarDifferentiableFunction(DenseVector.getInstance(variablesCount, 1.0)));
        group.add(new GeneralizedRosenbrockScalarDifferentiableFunction(DenseVector.getRandomizedVector(variablesCount, scalFactor)));
        group.add(new SchwefelScalarDifferentiableFunction(DenseVector.getRandomizedVector(variablesCount, scalFactor)));
        group.add(new GrivenkaScalarDifferentiableFunction(DenseVector.getRandomizedVector(variablesCount, scalFactor)));

        return group;
    }

    private List<IFirstOrderLearningUnconstrainedOptimizationAlgorithm> getLearningOptimizationAlgorithmsWithoutTensionGroup()   {
        List<IFirstOrderLearningUnconstrainedOptimizationAlgorithm> group = new ArrayList<>(16);

        FirstOrderLearningCriticalOptimizationAttributes
                attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        IFirstOrderLearningLinearSearchAlgorithm
                lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(3.0, 0.8, 3.0);
        IFirstOrderLearningDirectionSearchAlgorithm
                dsAlgorithm = new FirstOrderKachmazhDirectionSearchAlgorithm(0.8);

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderMultistepDirectionSearchAlgorithm();

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderPairwiseDirectionSearchAlgorithm();

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        return group;
    }

    private List<IFirstOrderLearningUnconstrainedOptimizationAlgorithm> getLearningOptimizationAlgorithmsWithTensionGroup()   {
        List<IFirstOrderLearningUnconstrainedOptimizationAlgorithm> group = new ArrayList<>(16);

        ISpaceTensionInternalDirectionCorrectionOperator
                KachmazhIDCO = new KachmazhInternalDirectionCorrectionOperator();

        ISpaceTensionMatrixCorrectionOperator
                alphaOperator = new AlphaSpaceTensionMatrixCorrectionOperator(1.5);

        FirstOrderLearningCriticalOptimizationAttributes
                attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        IFirstOrderLearningLinearSearchAlgorithm
                lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        IFirstOrderLearningDirectionSearchAlgorithm
                dsAlgorithm = new FirstOrderNYVSpaceTensionDirectionSearchAlgorithm(alphaOperator.deepCopy(), KachmazhIDCO.deepCopy());

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderNHGSpaceTensionDirectionSearchAlgorithm(alphaOperator.deepCopy(), KachmazhIDCO.deepCopy());

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderKachmazhSpaceTensionDirectionSearchAlgorithm(alphaOperator.deepCopy());

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderMultistepSpaceTensionDirectionSearchAlgorithm(alphaOperator.deepCopy());

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderPairwiseSpaceTensionDirectionSearchAlgorithm(alphaOperator.deepCopy());

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderOrthogonalSpaceTensionDirectionSearchAlgorithm(alphaOperator.deepCopy());

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        ISpaceTensionMatrixCorrectionOperator
                alphaBetaOperator = new AlphaBetaSpaceTensionMatrixCorrectionOperator(30.0, 1.0 / 5.0);

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderNYVSpaceTensionDirectionSearchAlgorithm(alphaBetaOperator.deepCopy(), KachmazhIDCO.deepCopy());

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderNHGSpaceTensionDirectionSearchAlgorithm(alphaBetaOperator.deepCopy(), KachmazhIDCO.deepCopy());

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderKachmazhSpaceTensionDirectionSearchAlgorithm(alphaBetaOperator.deepCopy());

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderMultistepSpaceTensionDirectionSearchAlgorithm(alphaBetaOperator.deepCopy());

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderPairwiseSpaceTensionDirectionSearchAlgorithm(alphaBetaOperator.deepCopy());

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderOrthogonalSpaceTensionDirectionSearchAlgorithm(alphaBetaOperator.deepCopy());

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        ISpaceTensionMatrixCorrectionOperator
                orthogonalOperator = new OrthogonalSpaceTensionMatrixCorrectionOperator(5.0);

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderNYVSpaceTensionDirectionSearchAlgorithm(orthogonalOperator.deepCopy(), KachmazhIDCO.deepCopy());

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderNHGSpaceTensionDirectionSearchAlgorithm(orthogonalOperator.deepCopy(), KachmazhIDCO.deepCopy());

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderKachmazhSpaceTensionDirectionSearchAlgorithm(orthogonalOperator.deepCopy());

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderMultistepSpaceTensionDirectionSearchAlgorithm(orthogonalOperator.deepCopy());

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderPairwiseSpaceTensionDirectionSearchAlgorithm(orthogonalOperator.deepCopy());

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        attrib = FirstOrderLearningCriticalOptimizationAttributes.getStandartSet(0.0, 1.0E-05);
        lsAlgorithm = LearningFirstOrderLinearSearchAlgorithm.getStandartSet(10.0, 0.8, 3.0);
        dsAlgorithm = new FirstOrderOrthogonalSpaceTensionDirectionSearchAlgorithm(orthogonalOperator.deepCopy());

        group.add(new LearningFirstOrderUnconstrainedOptimizationAlgorithm(attrib, lsAlgorithm, dsAlgorithm));

        return group;
    }

    private void logResultsToFile(List<LearningFirstOrderFunctionsTestingGroupMap> list, String suffix)   {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

        String logFilename = System.getProperty("user.dir")
                + File.separator
                + dateFormatter.format(new Date())
                + "_"
                + suffix
                + ".html";

        List<Integer> funIndexes = IntStream.rangeClosed(1, list.size()).boxed().collect(Collectors.toList());

        DivTag functionsDiv = div(attrs("#functions-group"), table(
                thead("Group functions"),
                tbody(each(funIndexes, i -> tr(td(String.valueOf(i)), td(list.get(i - 1).getOptimizationFunction().getName()))))
        ));

        final List<String> algNames = list.get(0).getAlgorithmNames();

        List<Integer> optAlgIndexes = IntStream.rangeClosed(1, algNames.size()).boxed().collect(Collectors.toList());

        DivTag optAlgorithmsDiv = div(attrs("#opt-algorithms"), table(
                thead("Optimization algorithms"),
                tbody(each(optAlgIndexes, i -> tr(td(String.valueOf(i)), td(algNames.get(i - 1)))))
        ));

        List<DivTag> resultDivs = list.stream()
                .map(groupMap -> {
                    final Integer[] index = new Integer[]{1};

                    return div(table(thead(
                                    tr(groupMap.getOptimizationFunction().getName()),
                                    tr(td("Index"),
                                            td("IterationCount"),
                                            td("FunComputeCount"),
                                            td("GradComputeCount"),
                                            td("FunOutput"),
                                            td("FunRepeatedCount"),
                                            td("TimePeriod [ms]"),
                                            td("StopCriteria"))),
                            tbody(
                                    each(groupMap.values(), result -> {
                                                TrTag tag = tr(td(String.valueOf(index[0])),
                                                        td(String.valueOf(result.getActualIterationCount())),
                                                        td(String.valueOf(result.getActualFunctionComputeCount())),
                                                        td(String.valueOf(result.getActualGradientComputeCount())),
                                                        td(String.valueOf(result.getActualFunctionOutput())),
                                                        td(String.valueOf(result.getActualFunctionOutputRepeatedCount())),
                                                        td(String.valueOf(result.getActualTimePeriod().toMillis())),
                                                        td(String.valueOf(result.getStopCriteria())));

                                                index[0] += 1;

                                                return tag;
                                            }
                                    )
                            )));
                })
                .collect(Collectors.toList());

        Map<IUnconstrainedOptimizationAlgorithm<? extends IScalarDifferentiableFunction,
                ? extends IFirstOrderLearningLinearSearchAlgorithm,
                ? extends IFirstOrderLearningDirectionSearchAlgorithm,
                ? extends FirstOrderLearningCriticalOptimizationAttributes,
                ? extends FirstOrderOptimizationResults>, FirstOrderTotalStatisticsOptimizationResults> statisticsMap = computeStatisticsMap(list);

        final Integer[] index = new Integer[]{1};

        DivTag statDiv = div(attrs("#stat-opt-alg"), table(
                thead(tr(td("Index"),
                        td("tIterationCount"),
                        td("tFunComputeCount"),
                        td("tGradComputeCount"),
                        td("tFunRepeatedCount"),
                        td("tTimePeriod [ms]"))),
                tbody(each(statisticsMap.values(), totalMap -> {
                            TrTag tag = tr(td(String.valueOf(index[0])),
                                    td(String.valueOf(totalMap.getTotalActualIterationCount())),
                                    td(String.valueOf(totalMap.getTotalActualFunctionComputeCount())),
                                    td(String.valueOf(totalMap.getTotalActualGradientComputeCount())),
                                    td(String.valueOf(totalMap.getTotalMaxFunctionOutputRepeatedCount())),
                                    td(String.valueOf(totalMap.getTotalActualTimePeriod().toMillis()))
                            );

                            index[0] += 1;

                            return tag;
                        })
                )
        ));

        Map.Entry<IUnconstrainedOptimizationAlgorithm<? extends IScalarDifferentiableFunction,
                ? extends IFirstOrderLearningLinearSearchAlgorithm,
                ? extends IFirstOrderLearningDirectionSearchAlgorithm,
                ? extends FirstOrderLearningCriticalOptimizationAttributes,
                ? extends FirstOrderOptimizationResults>, FirstOrderTotalStatisticsOptimizationResults>
                winner = statisticsMap.entrySet()
                .stream()
                .min((m0, m1) -> {
                    FirstOrderTotalStatisticsOptimizationResults r0 = m0.getValue(), r1 = m1.getValue();

                    long f0 = r0.getTotalActualIterationCount()
                            + r0.getTotalActualFunctionComputeCount()
                            + r0.getTotalActualGradientComputeCount()
                            + r0.getTotalActualTimePeriod().toMillis();

                    long f1 = r1.getTotalActualIterationCount()
                            + r1.getTotalActualFunctionComputeCount()
                            + r1.getTotalActualGradientComputeCount()
                            + r1.getTotalActualTimePeriod().toMillis();

                    if (f0 > f1) return 1;
                    if (f0 < f1) return -1;
                    return 0;
                }).get();

        DivTag winnerDiv = div("winner [min]: " + winner.getKey().getAlgorithmName()),
                emptyDiv = div("------------");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilename, StandardCharsets.UTF_8))) {
            writer.write(document(html(head(), body(functionsDiv,
                    emptyDiv,
                    optAlgorithmsDiv,
                    emptyDiv,
                    each(resultDivs, div -> div),
                    emptyDiv,
                    statDiv,
                    emptyDiv,
                    winnerDiv))));
        } catch (IOException ex) {
            throw new LoggableException(ex);
        }
    }

    private Map<IUnconstrainedOptimizationAlgorithm<? extends IScalarDifferentiableFunction,
            ? extends IFirstOrderLearningLinearSearchAlgorithm,
            ? extends IFirstOrderLearningDirectionSearchAlgorithm,
            ? extends FirstOrderLearningCriticalOptimizationAttributes,
            ? extends FirstOrderOptimizationResults>, FirstOrderTotalStatisticsOptimizationResults> computeStatisticsMap(List<LearningFirstOrderFunctionsTestingGroupMap> list) {

        Map<IUnconstrainedOptimizationAlgorithm<? extends IScalarDifferentiableFunction,
                ? extends IFirstOrderLearningLinearSearchAlgorithm,
                ? extends IFirstOrderLearningDirectionSearchAlgorithm,
                ? extends FirstOrderLearningCriticalOptimizationAttributes,
                ? extends FirstOrderOptimizationResults>, FirstOrderTotalStatisticsOptimizationResults> algMap = new LinkedHashMap<>(32);

        for (LearningFirstOrderFunctionsTestingGroupMap testingMap : list) {
            for (Map.Entry<IUnconstrainedOptimizationAlgorithm<? extends IScalarDifferentiableFunction,
                    ? extends IFirstOrderLearningLinearSearchAlgorithm,
                    ? extends IFirstOrderLearningDirectionSearchAlgorithm,
                    ? extends FirstOrderLearningCriticalOptimizationAttributes,
                    ? extends FirstOrderOptimizationResults>, FirstOrderOptimizationResults> map : testingMap.entrySet()) {
                algMap.putIfAbsent(map.getKey(), new FirstOrderTotalStatisticsOptimizationResults());

                algMap.get(map.getKey()).plusOneResult(map.getValue());
            }
        }

        return algMap;
    }
}
