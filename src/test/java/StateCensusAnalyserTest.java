import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;

public class StateCensusAnalyserTest {
    private static String DATA_CSV_FILE_PATH = "./src/test/resources/StateCensusData.csv";
    @Test
    public void totalRecordsFromCSV_whenMatched_shouldReturnTrue() throws StateCensusAnalyserException, IOException {
        StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser(DATA_CSV_FILE_PATH);
        int totalRecords =  stateCensusAnalyser.loadData();
        Assert.assertEquals(29,totalRecords);
    }
    @Test
    public void givenFileName_whenImproper_shouldThrowException() throws IOException {
        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser(DATA_CSV_FILE_PATH);
            stateCensusAnalyser.loadData();
        }
        catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND,e.exceptionTypeObject);
        }
    }
}
