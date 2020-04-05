import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;

public class StateCensusAnalyserTest {
    private static String DATA_CSV_FILE_PATH = "./src/test/resources/StateCensusData.csv";
    private static String IMPROPER_FILE_NAME = "./src/test/resources/stateensusata.csv";
    private static String IMPROPER_FILE_TYPE = "./src/test/resources/StateCensusData.txt";
    private static String WRONG_FILE = "./src/test/resources/StateCensusDataWrongFormat.csv";
    StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
    @Test
    public void totalRecordsFromCSV_whenMatched_shouldReturnTrue() throws StateCensusAnalyserException {
        int totalRecords =  stateCensusAnalyser.loadData(DATA_CSV_FILE_PATH);
        Assert.assertEquals(29,totalRecords);
    }
    @Test
    public void givenFileName_whenImproper_shouldThrowException() {
        try {
            stateCensusAnalyser.loadData(IMPROPER_FILE_NAME);
        }
        catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND,e.exceptionTypeObject);
        }
    }
    @Test
    public void givenFileType_whenIncorrect_shouldThrowException() {
        try {
            stateCensusAnalyser.loadData(IMPROPER_FILE_TYPE);
        }
        catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND,e.exceptionTypeObject);
        }
    }
    @Test
    public void givenFileData_whenIncorrect_shouldThrowException() {
        try {
            stateCensusAnalyser.loadData(WRONG_FILE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.INCORRECT_FILE, e.exceptionTypeObject);
        }
    }
}
