import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.nio.file.Files.newBufferedReader;

public class StateCensusAnalyser <E>{
    List<CSVStateCensusPojo> csvStateCensusFileList = null;
    List<CSVStateCodePojo> csvStateCodeFileList = null;

    Map<String,CSVStateCensusPojo> csvStateCensusMap = new HashMap<>();
    Map<String,CSVStateCodePojo> csvStateCodeMap = new HashMap<>();

    public int loadIndianCensusData(String csvFilePath) throws StateCensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.icsvBuilder();
            Iterator<CSVStateCensusPojo> csvStateCensusIterator = icsvBuilder.getFileIterator(reader,CSVStateCensusPojo.class);
            while (csvStateCensusIterator.hasNext()) {
                CSVStateCensusPojo value = csvStateCensusIterator.next();
                this.csvStateCensusMap.put(value.getState(),value);
                csvStateCensusFileList = csvStateCensusMap.values().stream().collect(Collectors.toList());
            }
            int totalRecords = csvStateCensusMap.size();
            return totalRecords;
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
            Iterator<CSVStateCodePojo> csvStateCodeIterator = icsvBuilder.getFileIterator(reader,CSVStateCodePojo.class);
            while (csvStateCodeIterator.hasNext()) {
                CSVStateCodePojo value = csvStateCodeIterator.next();
                this.csvStateCodeMap.put(value.getStateCode(),value);
                csvStateCodeFileList = csvStateCodeMap.values().stream().collect(Collectors.toList());
            }
            int totalRecords = csvStateCodeMap.size();
            return totalRecords;
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
        this.sort(censusComparator, csvStateCensusFileList);
        String stateCensusSortedJson = new Gson().toJson(csvStateCensusFileList);
        return stateCensusSortedJson;
    }
    public String getSortedStateCodeData() throws StateCensusAnalyserException {
        if (csvStateCodeFileList.size() == 0 | csvStateCodeFileList == null) {
            throw new StateCensusAnalyserException("No Census Data",StateCensusAnalyserException.exceptionType.NO_CENSUS_DATA);
        }
        Comparator<CSVStateCodePojo> stateCodeComparator = Comparator.comparing(census -> census.getStateCode());
        this.sort(stateCodeComparator,csvStateCodeFileList);
        String stateCensusSortedJson = new Gson().toJson(csvStateCodeFileList);
        return stateCensusSortedJson;
    }

    private<E> void sort(Comparator<E> censusComparator,List<E> csvFileList) {
        for (int i = 0; i < csvFileList.size(); i++) {
            for (int j = 0; j < csvFileList.size() - i - 1; j++) {
                E census1 = csvFileList.get(j);
                E census2 = csvFileList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    csvFileList.set(j, census2);
                    csvFileList.set(j + 1, census1);
                }

            }
        }
    }
}