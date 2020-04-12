import java.util.Map;

public class USCensusAdapter extends CensusAdapter{
    @Override
    public Map<String,CSVStateCensusDAO> loadCensusData(String... csvFilePath) throws StateCensusAnalyserException {
        return super.loadCensusData(CSVUSCensusPojo.class, csvFilePath[0]);
    }
}
