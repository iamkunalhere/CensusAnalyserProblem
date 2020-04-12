public class CSVStateCensusDAO {
    public String stateName;
    public int densityPerSqKm;
    public int areaInSqKm;
    public int population;
    public String stateCode;

    public CSVStateCensusDAO(CSVStateCensusPojo indiaCensusCSV) {
        densityPerSqKm = indiaCensusCSV.getDensityPerSqKm();
        areaInSqKm = indiaCensusCSV.getAreaInSqKm();
        population = indiaCensusCSV.getPopulation();
        stateName = indiaCensusCSV.getState();
    }
    public CSVStateCensusDAO(CSVStateCodePojo indiaCensusCSV) {
        stateCode = indiaCensusCSV.getStateCode();
    }
}
