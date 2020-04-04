import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class StateCensusAnalyserTest {
    @Test
    public void totalRecordsFromCSV_whenMatched_shouldReturnTrue() throws IOException {
        StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
        int totalRecords =  stateCensusAnalyser.loadData();
        Assert.assertEquals(29,totalRecords);
    }
}
