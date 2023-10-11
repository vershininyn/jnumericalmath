package dev.projects.math.datasources.unsupervised;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dev.projects.utils.exception.LoggableException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MultidimensionalUnsupervisedDataSet
        extends LinkedList<List<Double>>
        implements List<List<Double>> {

    public void read(InputStream stream) throws LoggableException {
        if (stream == null) throw new LoggableException("Unacceptable input stream");

        try (CSVReader reader = new CSVReader(new InputStreamReader(stream, Charset.forName("UTF-8")))) {
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                List<Double> variables = new ArrayList<>(16);

                for (int index = 0; index < nextLine.length; index++) {
                    variables.add(Double.valueOf(nextLine[index]));
                }

                add(variables);
            }
        } catch (CsvValidationException | IOException e) {
            throw new LoggableException(e);
        }
    }

    /**
     * Divides the sample into two parts according to the coefficient
     *
     * @param sampleCoeff
     * @return index 0 - first sample, 1 - second sample
     * @throws LoggableException
     */
    public MultidimensionalUnsupervisedDataSet[] splitByRows(float sampleCoeff) throws LoggableException {
        if ((sampleCoeff <= 0.0) || (sampleCoeff >= 1.0)) throw new LoggableException("Unacceptable coeff");

        //TODO: недостаточно данных
        int divideIndex = (int) Math.round(Math.ceil(sampleCoeff * size()));

        MultidimensionalUnsupervisedDataSet first = new MultidimensionalUnsupervisedDataSet();

        for (int index = 0; index < divideIndex; index++) {
            first.add(get(index));
        }

        MultidimensionalUnsupervisedDataSet second = new MultidimensionalUnsupervisedDataSet();

        for (int index = divideIndex; index < size(); index++) {
            second.add(get(index));
        }

        return new MultidimensionalUnsupervisedDataSet[]{first, second};
    }

    /**
     * Convert this multidimensional supervised sample to one dimensional unsupervised sample by means of
     * slicing index
     * @param index This index is used for switching between sample types. It's can be a zero.
     * @return OneDimensionalUnsupervisedDataSet with a one marker.
     * @throws LoggableException
     */
    public OneDimensionalUnsupervisedDataSet sliceToOneDimension(int index) throws LoggableException {
        if (index < 0) throw new LoggableException("Unacceptable a marker index");

        OneDimensionalUnsupervisedDataSet oneDataSet = new OneDimensionalUnsupervisedDataSet();

        for (List<Double> key : this) {
            oneDataSet.add(key.get(index));
        }

        return oneDataSet;
    }
}
