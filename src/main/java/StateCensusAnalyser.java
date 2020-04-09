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
    List<CSVStateCensusPojo> csvStateCensusFileList = null;
    List<CSVStateCodePojo> csvStateCodeFileList = null;

    public int loadIndianCensusData(String csvFilePath) throws StateCensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.icsvBuilder();
            csvStateCensusFileList = icsvBuilder.getFileList(reader, CSVStateCensusPojo.class);
            return csvStateCensusFileList.size();
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
            csvStateCodeFileList = icsvBuilder.getFileList(reader, CSVStateCodePojo.class);
            return csvStateCodeFileList.size();
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
        if (csvStateCensusFileList.size() == 0 | csvStateCensusFileList == null) {
            throw new StateCensusAnalyserException("No Census Data",StateCensusAnalyserException.exceptionType.NO_CENSUS_DATA);
        }
        Comparator<CSVStateCensusPojo> censusComparator = Comparator.comparing(census -> census.getState());
        this.sortName(censusComparator);
        String stateCensusSortedJson = new Gson().toJson(csvStateCensusFileList);
        return stateCensusSortedJson;
    }
    public String getSortedStateCodeData() throws StateCensusAnalyserException {
        if (csvStateCodeFileList.size() == 0 | csvStateCodeFileList == null) {
            throw new StateCensusAnalyserException("No Census Data",StateCensusAnalyserException.exceptionType.NO_CENSUS_DATA);
        }
        Comparator<CSVStateCodePojo> stateCodeComparator = Comparator.comparing(census -> census.getStateCode());
        this.sortCode(stateCodeComparator);
        String stateCensusSortedJson = new Gson().toJson(csvStateCodeFileList);
        return stateCensusSortedJson;
    }

    private void sortName(Comparator<CSVStateCensusPojo> censusComparator) {
        for (int i = 0; i < csvStateCensusFileList.size(); i++) {
            for (int j = 0; j < csvStateCensusFileList.size() - i - 1; j++) {
                CSVStateCensusPojo census1 = csvStateCensusFileList.get(j);
                CSVStateCensusPojo census2 = csvStateCensusFileList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    csvStateCensusFileList.set(j, census2);
                    csvStateCensusFileList.set(j + 1, census1);
                }

            }
        }
    }
    private void sortCode(Comparator<CSVStateCodePojo> censusComparator) {
        for (int i = 0; i < csvStateCodeFileList.size(); i++) {
            for (int j = 0; j < csvStateCodeFileList.size() - i - 1; j++) {
                CSVStateCodePojo census1 = csvStateCodeFileList.get(j);
                CSVStateCodePojo census2 = csvStateCodeFileList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    csvStateCodeFileList.set(j, census2);
                    csvStateCodeFileList.set(j + 1, census1);
                }

            }
        }
    }
}