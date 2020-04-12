import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

import static java.nio.file.Files.newBufferedReader;

public abstract class CensusAdapter {
    public abstract Map<String, CSVStateCensusDAO> loadCensusData(String... csvFilePath) throws StateCensusAnalyserException;

    public <E> Map<String, CSVStateCensusDAO> loadCensusData(Class<E> censusCSVClass, String csvFilePath) throws StateCensusAnalyserException {
        Map<String, CSVStateCensusDAO> censusDAOMap = new HashMap<>();

        try (Reader reader = newBufferedReader(Paths.get(csvFilePath))) {
            CSVBuilder csvBuilder = (CSVBuilder) CSVBuilderFactory.icsvBuilder();
            Iterator<E> stateCensusIterator = csvBuilder.getFileIterator(reader, censusCSVClass);
            Iterable<E> stateCensuses = () -> stateCensusIterator;
            if (censusCSVClass.getName().contains("CSVStateCensusPojo")) {
                StreamSupport.stream(stateCensuses.spliterator(), false)
                        .map(CSVStateCensusPojo.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.getState(), new CSVStateCensusDAO(censusCSV)));
            } else if (censusCSVClass.getName().contains("CSVUSCensusPojo")) {
                StreamSupport.stream(stateCensuses.spliterator(), false)
                        .map(CSVUSCensusPojo.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.State, new CSVStateCensusDAO(censusCSV)));
            }
        } catch (IOException e) {
            throw new StateCensusAnalyserException(e.getMessage(),StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND);
        } catch (CSVBuilderException e) {
            throw new StateCensusAnalyserException(e.getMessage(),e.type);
        }
        return censusDAOMap;
    }
}