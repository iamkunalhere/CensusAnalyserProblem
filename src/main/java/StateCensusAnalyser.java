import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.nio.file.Files.newBufferedReader;

public class StateCensusAnalyser {
    List<CSVStateCensusPojo> csvFileList = null;

    public int loadIndianCensusData(String csvFilePath) throws StateCensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.icsvBuilder();
            csvFileList = icsvBuilder.getFileList(reader, CSVStateCensusPojo.class);
            return csvFileList.size();
        } catch (IOException e) {
            throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND);
        } catch (CSVBuilderException e) {
            throw new StateCensusAnalyserException(e.getMessage(), e.type);
        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.exceptionType.INCORRECT_FILE);
        }
    }

    public int loadStateCodes(String csvFilePath) throws StateCensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.icsvBuilder();
            List<CSVStateCodePojo> csvFileList = icsvBuilder.getFileList(reader, CSVStateCodePojo.class);
            return csvFileList.size();
        } catch (IOException e) {
            throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND);
        } catch (CSVBuilderException e) {
            throw new StateCensusAnalyserException(e.getMessage(), e.type);
        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.exceptionType.INCORRECT_FILE);
        }
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> iterable = () -> iterator;
        int totalRecords = (int) StreamSupport.stream(iterable.spliterator(), false).count();
        return totalRecords;
    }

    public String getSortedStateCensusData() throws StateCensusAnalyserException {
        if (csvFileList.size() == 0 | csvFileList == null) {
            throw new StateCensusAnalyserException("No Census Data",StateCensusAnalyserException.exceptionType.NO_CENSUS_DATA);
        }
        Comparator<CSVStateCensusPojo> censusComparator = Comparator.comparing(census -> census.getState());
        this.sort(censusComparator);
        String stateCensusSortedJson = new Gson().toJson(csvFileList);
        return stateCensusSortedJson;
    }

    private void sort(Comparator<CSVStateCensusPojo> censusComparator) {
        for (int i = 0; i < csvFileList.size(); i++) {
            for (int j = 0; j < csvFileList.size() - i - 1; j++) {
                CSVStateCensusPojo census1 = csvFileList.get(j);
                CSVStateCensusPojo census2 = csvFileList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    csvFileList.set(j, census2);
                    csvFileList.set(j + 1, census1);
                }

            }
        }
    }
}