import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.nio.file.Files.newBufferedReader;

public class StateCensusAnalyser <E>{
    List<CSVStateCensusDAO> csvList = null;
    Map<String,CSVStateCensusDAO> csvMap = new HashMap<>();


    public int loadIndianCensusData(String csvFilePath) throws StateCensusAnalyserException, CSVBuilderException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.icsvBuilder();
            Iterator<CSVStateCensusDAO> csvStateCensusIterator = icsvBuilder.getFileIterator(reader,CSVStateCensusDAO.class);
            while (csvStateCensusIterator.hasNext()) {
                CSVStateCensusDAO value = csvStateCensusIterator.next();
                this.csvMap.put(value.state,value);
                csvList = csvMap.values().stream().collect(Collectors.toList());
            }
            int totalRecords = csvMap.size();
            return totalRecords;
            } catch (IOException e) {
            throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND);
        } catch (CSVBuilderException e) {
            throw new StateCensusAnalyserException(e.getMessage(), e.type);
        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException(e.getMessage(),StateCensusAnalyserException.exceptionType.INCORRECT_FILE);
        }
    }

    public int loadStateCodes(String csvFilePath) throws StateCensusAnalyserException, CSVBuilderException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.icsvBuilder();
            Iterator<CSVStateCensusDAO> csvStateCodeIterator = icsvBuilder.getFileIterator(reader,CSVStateCensusDAO.class);
            while (csvStateCodeIterator.hasNext()) {
                CSVStateCensusDAO value = csvStateCodeIterator.next();
                this.csvMap.put(value.stateCode,value);
                csvList = csvMap.values().stream().collect(Collectors.toList());
            }
            int totalRecords = csvMap.size();
            return totalRecords;
        } catch (IOException e) {
            throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND);
        } catch (CSVBuilderException e) {
            throw new StateCensusAnalyserException(e.getMessage(), e.type);
        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException(e.getMessage(),StateCensusAnalyserException.exceptionType.INCORRECT_FILE);
        }
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> iterable = () -> iterator;
        int totalRecords = (int) StreamSupport.stream(iterable.spliterator(), false).count();
        return totalRecords;
    }

    public String getSortedStateCensusData() throws StateCensusAnalyserException {
        if (csvList.size() == 0 | csvList == null) {
            throw new StateCensusAnalyserException("No Census Data",StateCensusAnalyserException.exceptionType.NO_CENSUS_DATA);
        }
        Comparator<CSVStateCensusDAO> comparator = Comparator.comparing(census -> census.state);
        this.sort(comparator);
        String stateCensusSortedJson = new Gson().toJson(csvList);
        return stateCensusSortedJson;
    }

    public String getSortedStateCodeData() throws StateCensusAnalyserException {
        if (csvList.size() == 0 | csvList == null) {
            throw new StateCensusAnalyserException("No Census Data",StateCensusAnalyserException.exceptionType.NO_CENSUS_DATA);
        }
        Comparator<CSVStateCensusDAO> comparator = Comparator.comparing(census -> census.stateCode);
        this.sort(comparator);
        String stateCensusSortedJson = new Gson().toJson(csvList);
        return stateCensusSortedJson;
    }
    public String getSortedStatePopulationData() throws StateCensusAnalyserException {
        if (csvList.size() == 0 | csvList == null) {
            throw new StateCensusAnalyserException("No Census Data",StateCensusAnalyserException.exceptionType.NO_CENSUS_DATA);
        }
        Comparator<CSVStateCensusDAO> comparator = Comparator.comparing(census -> census.population);
        this.sort(comparator);
        String stateCensusSortedJson = new Gson().toJson(csvList);
        return stateCensusSortedJson;
    }
    public String getSortedStateDensityWiseData() throws StateCensusAnalyserException {
        if (csvList.size() == 0 | csvList == null) {
            throw new StateCensusAnalyserException("No Census Data",StateCensusAnalyserException.exceptionType.NO_CENSUS_DATA);
        }
        Comparator<CSVStateCensusDAO> comparator = Comparator.comparing(census -> census.densityPerSqKm);
        this.sort(comparator);
        String stateCensusSortedJson = new Gson().toJson(csvList);
        return stateCensusSortedJson;
    }

    private<E> void sort(Comparator<CSVStateCensusDAO> comparator) {
        for (int i = 0; i < csvList.size(); i++) {
            for (int j = 0; j < csvList.size() - i - 1; j++) {
                CSVStateCensusDAO census1 = csvList.get(j);
                CSVStateCensusDAO census2 = csvList.get(j + 1);
                if (comparator.compare(census1, census2) > 0) {
                    csvList.set(j, census2);
                    csvList.set(j + 1, census1);
                }

            }
        }
    }
}