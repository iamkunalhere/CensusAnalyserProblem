import com.opencsv.bean.CsvBindByName;

public class CSVStateCodePojo {
    @CsvBindByName(column = "SrNo")
    private String SrNo;
    @CsvBindByName(column = "StateName")
    private String StateName;
    @CsvBindByName(column = "TIN")
    private String TIN;
    @CsvBindByName(column = "StateCode")
    private String StateCode;

    public String getSrNo() {
        return SrNo;
    }

    public void setSrNo(String srNo) {
        SrNo = srNo;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public String getStateCode() {
        return StateCode;
    }

    public void setStateCode(String stateCode) {
        StateCode = stateCode;
    }
}
