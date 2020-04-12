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
            Iterator<CSVStateCensusPojo> csvStateCensusIterator = icsvBuilder.getFileIterator(reader,CSVStateCensusPojo.class);
            Iterable<CSVStateCensusPojo> stateCensusIterable = () -> csvStateCensusIterator;
            StreamSupport.stream(stateCensusIterable.spliterator(), false)
                    .forEach(stateCensusCSV -> csvMap.put(stateCensusCSV.getState(), new CSVStateCensusDAO(stateCensusCSV)));
            csvList = csvMap.values().stream().collect(Collectors.toList());
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
            Iterator<CSVStateCodePojo> csvStateCodeIterator = icsvBuilder.getFileIterator(reader,CSVStateCodePojo.class);
            Iterable<CSVStateCodePojo> stateCodeIterable = () -> csvStateCodeIterator;
            StreamSupport.stream(stateCodeIterable.spliterator(), false)
                    .filter(stateDataCSV -> csvMap.get(stateDataCSV.getStateCode()) != null)
                    .forEach(stateDataCSV -> csvMap.get(stateDataCSV.getStateCode()).stateCode = stateDataCSV.getStateCode());
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
    public int loadUSCensusData(String csvFilePath) throws StateCensusAnalyserException {
        int totalRecords = 0;
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath))){
            ICSVBuilder icsvBuilder = CSVBuilderFactory.icsvBuilder();
            Iterator<CSVUSCensusPojo> csvUSCensusIterator = icsvBuilder.getFileIterator(reader,CSVUSCensusPojo.class);
            while (csvUSCensusIterator.hasNext()) {
                CSVUSCensusPojo USCensus = csvUSCensusIterator.next();
                System.out.println("State ID: " + USCensus.StateID);
                System.out.println("State Name : " + USCensus.State);
                System.out.println("Area : " + USCensus.Area);
                System.out.println("Housing units : " + USCensus.HousingUnits);
                System.out.println("Water area : " + USCensus.WaterArea);
                System.out.println("Land Area : " + USCensus.LandArea);
                System.out.println("Density : " + USCensus.PopulationDensity);
                System.out.println("Population : " + USCensus.Population);
                System.out.println("Housing Density : " + USCensus.HousingDensity);
                totalRecords++;
            }
            return totalRecords;
        } catch (IOException e) {
            throw new StateCensusAnalyserException(e.getMessage(),StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND);
        } catch (CSVBuilderException e) {
            throw new  StateCensusAnalyserException(e.getMessage(),e.type);
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
        Comparator<CSVStateCensusDAO> comparator = Comparator.comparing(census -> census.stateName);
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
    public String getSortedStateAreaWiseData() throws StateCensusAnalyserException {
        if (csvList.size() == 0 | csvList == null) {
            throw new StateCensusAnalyserException("No Census Data",StateCensusAnalyserException.exceptionType.NO_CENSUS_DATA);
        }
        Comparator<CSVStateCensusDAO> comparator = Comparator.comparing(census -> census.areaInSqKm);
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