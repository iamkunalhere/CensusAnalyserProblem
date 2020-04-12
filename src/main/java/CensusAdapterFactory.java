import java.util.Map;

public class CensusAdapterFactory {
    public static Map<String, CSVStateCensusDAO> getCensusData(StateCensusAnalyser.Country country, String[] csvFilePath) throws StateCensusAnalyserException {
        if (country.equals(StateCensusAnalyser.Country.INDIA))
            return new IndianCensusAdapter().loadCensusData(csvFilePath);
        else if (country.equals(StateCensusAnalyser.Country.US))
            return new USCensusAdapter().loadCensusData(csvFilePath[0]);
        else
            throw new StateCensusAnalyserException( "Invalid country", StateCensusAnalyserException.exceptionType.INVALID_COUNTRY);
    }
}
