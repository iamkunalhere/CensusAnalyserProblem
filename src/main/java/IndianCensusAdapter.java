import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.nio.file.Files.newBufferedReader;

public class IndianCensusAdapter extends CensusAdapter{
    @Override
    public Map<String, CSVStateCensusDAO> loadCensusData(String... csvFilePath) throws StateCensusAnalyserException {
        Map<String, CSVStateCensusDAO> csvMap = super.loadCensusData(CSVStateCensusPojo.class, csvFilePath[0]);
        if (csvFilePath.length == 1)
            return csvMap;
        return loadStateCodeCSVData(csvMap, csvFilePath[1]);
    }

    private Map<String,CSVStateCensusDAO> loadStateCodeCSVData(Map<String,CSVStateCensusDAO> csvMap, String csvFilePath) throws StateCensusAnalyserException{
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.icsvBuilder();
            Iterator<CSVStateCensusPojo> csvStateCensusIterator = icsvBuilder.getFileIterator(reader,CSVStateCensusPojo.class);
            Iterable<CSVStateCensusPojo> stateCensusIterable = () -> csvStateCensusIterator;
            StreamSupport.stream(stateCensusIterable.spliterator(), false)
                    .filter(csvStateCensusPojo -> csvMap.get(csvStateCensusPojo.getState()) != null)
                    .forEach(csvStateCensusPojo -> csvMap.get(csvStateCensusPojo.getState()).State = csvStateCensusPojo.getState());
        } catch (IOException e) {
            throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND);
        } catch (CSVBuilderException e) {
            throw new StateCensusAnalyserException(e.getMessage(), e.type);
        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException(e.getMessage(),StateCensusAnalyserException.exceptionType.INCORRECT_FILE);
        }
        return csvMap;
    }
}
