import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class StateCensusAnalyserTest {
    private static final String DATA_CSV_FILE_PATH = "./src/test/resources/StateCensusData.csv";
    private static final String IMPROPER_FILE_NAME = "./src/test/resources/stateensusata.csv";
    private static final String IMPROPER_FILE_TYPE = "./src/test/resources/StateCensusData.txt";
    private static final String WRONG_FILE = "./src/test/resources/StateCensusDataWrongFormat.csv";

    private static final String STATE_CODE_FILE = "./src/test/resources/StateCode.csv";
    private static final String WRONG_STATE_CODE_FILE = "./src/test/resources/StateCodeWrongFormat.csv";

    private static final String US_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

    StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();

    // test to ensure no of records matches of StateCensusData.csv
    @Test
    public void givenTotalRecordsFromCSV_whenMatched_shouldReturnTrue() throws StateCensusAnalyserException, CSVBuilderException {
        try {
            int totalRecords = stateCensusAnalyser.loadStateCensusCSVData(StateCensusAnalyser.Country.INDIA,DATA_CSV_FILE_PATH);
            Assert.assertEquals(29, totalRecords);
        } catch (StateCensusAnalyserException e) {        }
    }
    // test to check if StateCensusData file is incorrect
    @Test
    public void givenFileName_whenImproper_shouldThrowException() throws CSVBuilderException {
        try {
            stateCensusAnalyser.loadStateCensusCSVData(StateCensusAnalyser.Country.INDIA,IMPROPER_FILE_NAME);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND, e.exceptionTypeObject);
        }
    }
    // test to check if StateCensusData file is correct but type is incorrect
    @Test
    public void givenFileType_whenIncorrect_shouldThrowException() throws CSVBuilderException {
        try {
            stateCensusAnalyser.loadStateCensusCSVData(StateCensusAnalyser.Country.INDIA,IMPROPER_FILE_TYPE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND, e.exceptionTypeObject);
        }
    }
    // test to check if StateCensusData file is correct but Header and Delimiter is incorrect
    @Test
    public void givenFileData_whenIncorrect_shouldThrowException() throws CSVBuilderException {
        try {
            stateCensusAnalyser.loadStateCensusCSVData(StateCensusAnalyser.Country.INDIA,WRONG_FILE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(e.exceptionTypeObject, StateCensusAnalyserException.exceptionType.INCORRECT_FILE);
        }
    }
    // test to ensure no of records matches of StateCode.csv
    @Test
    public void givenTotalRecordsFrom_StateCodeCSVFile_whenMatched_shouldReturnTrue() throws StateCensusAnalyserException, CSVBuilderException {
        try {
            int totalRecords = stateCensusAnalyser.loadStateCodes(STATE_CODE_FILE);
            Assert.assertEquals(37, totalRecords);
        } catch (StateCensusAnalyserException e) {
        }
    }
    // test to check if StateCensusData file is incorrect
    @Test
    public void givenStateCodeCSVFileName_whenImproper_shouldThrowException() throws CSVBuilderException {
        try {
            stateCensusAnalyser.loadStateCodes(IMPROPER_FILE_NAME);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND, e.exceptionTypeObject);
        }
    }

    // test to check if StateCensusData file is correct but type is incorrect
    @Test
    public void givenStateCodeCSVFileType_whenIncorrect_shouldThrowException() throws CSVBuilderException {
        try {
            stateCensusAnalyser.loadStateCodes(IMPROPER_FILE_TYPE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND, e.exceptionTypeObject);
        }
    }

    // test to check if StateCensusData file is correct but Header and Delimiter is incorrect
    @Test
    public void givenStateCodeCSVFileData_whenIncorrect_shouldThrowException() throws CSVBuilderException {
        try {
            stateCensusAnalyser.loadStateCodes(WRONG_STATE_CODE_FILE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.INCORRECT_FILE, e.exceptionTypeObject);
        }
    }
    // test to check census data is sorted in Json format according to State name
    @Test
    public void givenStateCensusData_whenSortedOnStates_shouldReturnSortedResult() throws CSVBuilderException {
        try {
            stateCensusAnalyser.loadStateCensusCSVData(StateCensusAnalyser.Country.INDIA,DATA_CSV_FILE_PATH);
            String sortedStateCensusData = stateCensusAnalyser.getSortedStateCensusData();
            CSVStateCensusPojo[] csvStateCensusPojo = new Gson().fromJson(sortedStateCensusData, CSVStateCensusPojo[].class);
            Assert.assertEquals("Andhra Pradesh", csvStateCensusPojo[0].getState());
            Assert.assertEquals("West Bengal",csvStateCensusPojo[28].getState());
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.INCORRECT_FILE, e.exceptionTypeObject);
        }
    }
    // test to check census data is sorted in Json format according to State code
    @Test
    public void givenStateCodeData_whenSortedOnStates_shouldReturnSortedResult() throws CSVBuilderException {
        try {
            stateCensusAnalyser.loadStateCodes(STATE_CODE_FILE);
            String sortedStateCodeData = stateCensusAnalyser.getSortedStateCodeData();
            CSVStateCodePojo[] csvStateCodePojo = new Gson().fromJson(sortedStateCodeData, CSVStateCodePojo[].class);
            Assert.assertEquals("AD", csvStateCodePojo[0].getStateCode());
            Assert.assertEquals("WB",csvStateCodePojo[36].getStateCode());
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.INCORRECT_FILE, e.exceptionTypeObject);
        }
    }
    // test to check census data is sorted in Json format according to Population
    @Test
    public void givenStateCensusData_whenSortedOnPopulation_shouldReturnSortedResult() throws CSVBuilderException {
        try {
            stateCensusAnalyser.loadStateCensusCSVData(StateCensusAnalyser.Country.INDIA,DATA_CSV_FILE_PATH);
            String sortedStateCensusData = stateCensusAnalyser.getSortedStatePopulationData();
            CSVStateCensusPojo[] csvStateCensusPojo = new Gson().fromJson(sortedStateCensusData, CSVStateCensusPojo[].class);
            Assert.assertEquals(199812341, csvStateCensusPojo[0].getPopulation());
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.INCORRECT_FILE, e.exceptionTypeObject);
        }
    }
    // test to check census data is sorted in Json format according to Density Wise
    @Test
    public void givenStateCensusData_whenSortedOnDensityWise_shouldReturnSortedResult() throws CSVBuilderException {
        try {
            stateCensusAnalyser.loadStateCensusCSVData(StateCensusAnalyser.Country.INDIA,DATA_CSV_FILE_PATH);
            String sortedStateCensusData = stateCensusAnalyser.getSortedStateDensityWiseData();
            CSVStateCensusPojo[] csvStateCensusPojo = new Gson().fromJson(sortedStateCensusData, CSVStateCensusPojo[].class);
            Assert.assertEquals(1102, csvStateCensusPojo[0].getDensityPerSqKm());
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.INCORRECT_FILE, e.exceptionTypeObject);
        }
    }
    // test to check census data is sorted in Json format according to Area Wise
    @Test
    public void givenStateCensusData_whenSortedOnAreaWise_shouldReturnSortedResult() throws CSVBuilderException {
        try {
            stateCensusAnalyser.loadStateCensusCSVData(StateCensusAnalyser.Country.INDIA,DATA_CSV_FILE_PATH);
            String sortedStateCensusData = stateCensusAnalyser.getSortedStateAreaWiseData();
            CSVStateCensusPojo[] csvStateCensusPojo = new Gson().fromJson(sortedStateCensusData, CSVStateCensusPojo[].class);
            Assert.assertEquals(342239, csvStateCensusPojo[0].getAreaInSqKm());
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.INCORRECT_FILE, e.exceptionTypeObject);
        }
    }
    // test to ensure no of records matches of USCensusData.csv
    @Test
    public void givenTotalRecordsFromUSCSV_whenMatched_shouldReturnTrue() throws StateCensusAnalyserException, CSVBuilderException {
        try {
            int totalRecords = stateCensusAnalyser.loadStateCensusCSVData(StateCensusAnalyser.Country.US,US_CSV_FILE_PATH);
            Assert.assertEquals(51, totalRecords);
        } catch (StateCensusAnalyserException e) {        }
    }
}