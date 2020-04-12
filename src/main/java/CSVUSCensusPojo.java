import com.opencsv.bean.CsvBindByName;

public class CSVUSCensusPojo {
    @CsvBindByName(column = "State Id")
    public String StateID;

    @CsvBindByName(column = "State")
    public String State;

    @CsvBindByName(column = "Population Density")
    public String PopulationDensity;

    @CsvBindByName(column = "Population")
    public String Population;

    @CsvBindByName(column = "Total area")
    public String Area;

    @CsvBindByName(column = "Housing units")
    public String HousingUnits;

    @CsvBindByName(column = "Water area")
    public String WaterArea;

    @CsvBindByName(column = "Land Area")
    public String LandArea;

    @CsvBindByName(column = "Housing Density")
    public String HousingDensity;
}
