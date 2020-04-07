import org.junit.Assert;
import org.junit.Test;

public class StateCensusAnalyserTest {
    private static String DATA_CSV_FILE_PATH = "./src/test/resources/StateCensusData.csv";
    private static String IMPROPER_FILE_NAME = "./src/test/resources/stateensusata.csv";
    private static String IMPROPER_FILE_TYPE = "./src/test/resources/StateCensusData.txt";
    private static String WRONG_FILE = "./src/test/resources/StateCensusDataWrongFormat.csv";

    private static String STATE_CODE_FILE = "./src/test/resources/StateCode.csv";
    private static String WRONG_STATE_CODE_FILE = "./src/test/resources/StateCodeWrongFormat.csv";

    StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
    // test to ensure no of records matches of StateCensusData.csv
    @Test
    public void totalRecordsFromCSV_whenMatched_shouldReturnTrue() throws StateCensusAnalyserException {
        int totalRecords =  stateCensusAnalyser.loadIndianCensusData(DATA_CSV_FILE_PATH);
        Assert.assertEquals(29,totalRecords);
    }
    // test to check if StateCensusData file is incorrect
    @Test
    public void givenFileName_whenImproper_shouldThrowException() {
        try {
            stateCensusAnalyser.loadIndianCensusData(IMPROPER_FILE_NAME);
        }
        catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND,e.exceptionTypeObject);
        }
    }
    // test to check if StateCensusData file is correct but type is incorrect
    @Test
    public void givenFileType_whenIncorrect_shouldThrowException() {
        try {
            stateCensusAnalyser.loadIndianCensusData(IMPROPER_FILE_TYPE);
        }
        catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND,e.exceptionTypeObject);
        }
    }
    // test to check if StateCensusData file is correct but Header and Delimiter is incorrect
    @Test
    public void givenFileData_whenIncorrect_shouldThrowException() {
        try {
            stateCensusAnalyser.loadIndianCensusData(WRONG_FILE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.INCORRECT_FILE, e.exceptionTypeObject);
        }
    }
    // test to ensure no of records matches of StateCode.csv
    @Test
    public void totalRecordsFrom_StateCodeCSVFile_whenMatched_shouldReturnTrue() throws StateCensusAnalyserException {
        int totalRecords = stateCensusAnalyser.loadStateCodes(STATE_CODE_FILE);
        Assert.assertEquals(37,totalRecords);
    }
    // test to check if StateCensusData file is incorrect
    @Test
    public void givenStateCodeCSVFileName_whenImproper_shouldThrowException() {
        try {
            stateCensusAnalyser.loadStateCodes(IMPROPER_FILE_NAME);
        }
        catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND,e.exceptionTypeObject);
        }
    }
    // test to check if StateCensusData file is correct but type is incorrect
    @Test
    public void givenStateCodeCSVFileType_whenIncorrect_shouldThrowException() {
        try {
            stateCensusAnalyser.loadStateCodes(IMPROPER_FILE_TYPE);
        }
        catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND,e.exceptionTypeObject);
        }
    }
    // test to check if StateCensusData file is correct but Header and Delimiter is incorrect
    @Test
    public void givenStateCodeCSVFileData_whenIncorrect_shouldThrowException() {
        try {
            stateCensusAnalyser.loadStateCodes(WRONG_STATE_CODE_FILE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.INCORRECT_FILE, e.exceptionTypeObject);
        }
    }
}
