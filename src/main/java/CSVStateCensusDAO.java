import java.util.Comparator;

public class CSVStateCensusDAO {
    public String stateName;
    public int densityPerSqKm;
    public int areaInSqKm;

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getDensityPerSqKm() {
        return densityPerSqKm;
    }

    public void setDensityPerSqKm(int densityPerSqKm) {
        this.densityPerSqKm = densityPerSqKm;
    }

    public int getAreaInSqKm() {
        return areaInSqKm;
    }

    public void setAreaInSqKm(int areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateID() {
        return StateID;
    }

    public void setStateID(String stateID) {
        StateID = stateID;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public float getHousingDensity() {
        return HousingDensity;
    }

    public void setHousingDensity(float housingDensity) {
        HousingDensity = housingDensity;
    }

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
    public static Comparator<CSVStateCensusDAO> getSortComparator(StateCensusAnalyser.SortingMode mode) {
        if (mode.equals(StateCensusAnalyser.SortingMode.STATE))
            return Comparator.comparing(census -> census.State);
        if (mode.equals(StateCensusAnalyser.SortingMode.POPULATION))
            return Comparator.comparing(CSVStateCensusDAO::getPopulation).reversed();
        if (mode.equals(StateCensusAnalyser.SortingMode.AREA))
            return Comparator.comparing(CSVStateCensusDAO::getAreaInSqKm).reversed();
        if (mode.equals(StateCensusAnalyser.SortingMode.DENSITY))
            return Comparator.comparing(CSVStateCensusDAO::getDensityPerSqKm).reversed();
        return null;
    }
}

