package dev.projects.math.jnummath.jdatasources.supervised;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dev.projects.math.jnummath.jkernel.exceptions.LoggableException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MultidimensionalSupervisedDataSet
        extends LinkedHashMap<List<Double>, List<Double>>
        implements Map<List<Double>, List<Double>> {

    public void read(InputStream stream, int markerDimension)   {
        if (markerDimension <= 0) throw new LoggableException("Unacceptable a marker size");
        if (stream == null) throw new LoggableException("Unacceptable input stream");

        try (CSVReader reader = new CSVReader(new InputStreamReader(stream, Charset.forName("UTF-8")))) {
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                List<Double> independentVariables = new ArrayList<>(16);

                for (int index = 0; index < nextLine.length - markerDimension; index++) {
                    independentVariables.add(Double.valueOf(nextLine[index]));
                }

                List<Double> dependentVariables = new ArrayList<>(8);

                for (int index = nextLine.length - markerDimension; index < nextLine.length; index++) {
                    dependentVariables.add(Double.valueOf(nextLine[index]));
                }

                put(independentVariables, dependentVariables);
            }
        } catch (CsvValidationException | IOException e) {
            throw new LoggableException(e);
        }
    }

    public OneDimensionalSupervisedDataSet sliceToOneDimension(int markerIndex)   {
        if (markerIndex < 0) throw new LoggableException("Unacceptable a marker index");

        OneDimensionalSupervisedDataSet oneDataSet = new OneDimensionalSupervisedDataSet();

        for (List<Double> key : keySet()) {
            oneDataSet.put(key, get(key).get(markerIndex));
        }

        return oneDataSet;
    }
}
