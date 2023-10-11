package dev.projects.math.transformations.generator;

public class GeneratorTest {
//    private SyntaxTreeGenerator generator = new SyntaxTreeGenerator();

    /*@Test
    public void generatorTest() throws IOException, LoggableException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(GeneratorTest.class.getResourceAsStream("objFunction.csv"),
                Charset.forName("UTF-8")));

        String objFunction = reader.readLine();

        reader.close();

        SyntaxKnote syntaxTree = generator.generate(objFunction);

        IScalarDifferentiableFunction syntaxFunction = new SyntaxTreeScalarDifferentiableFunction(3, syntaxTree);

        Assertions.assertEquals(0.0, syntaxFunction.computeOutput(DenseVector.getInstance(new double[]{0.0,0.0,0.0})));
    }*/
}
