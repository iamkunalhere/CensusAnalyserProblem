public class CSVStateCensusDAO {
    public String stateName;
    public int densityPerSqKm;
    public int areaInSqKm;
    public int population;
    public String stateCode;
    public String StateID;
    public String State;
    public float HousingDensity;

    public CSVStateCensusDAO(CSVStateCensusPojo indiaCensusCSV) {
        densityPerSqKm = indiaCensusCSV.getDensityPerSqKm();
        areaInSqKm = indiaCensusCSV.getAreaInSqKm();
        population = indiaCensusCSV.getPopulation();
        stateName = indiaCensusCSV.getState();
    }
    public CSVStateCensusDAO(CSVStateCodePojo indiaCensusCSV) {
        stateCode = indiaCensusCSV.getStateCode();
    }
    public CSVStateCensusDAO(CSVUSCensusPojo usCensusCSV){
        this.StateID = usCensusCSV.StateID;
        this.State = usCensusCSV.State;
        this.population = (int) usCensusCSV.population;
        this.areaInSqKm= (int) usCensusCSV.Area;
        this.densityPerSqKm = usCensusCSV.PopulationDensity;
        this.HousingDensity = usCensusCSV.HousingDensity;
    }
}

