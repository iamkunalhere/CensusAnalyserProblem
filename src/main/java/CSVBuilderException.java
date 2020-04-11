public class CSVBuilderException extends Throwable {
    StateCensusAnalyserException.exceptionType type;
    public CSVBuilderException(String message, StateCensusAnalyserException.exceptionType unableToParse) {
        super(message);
        this.type = type;
    }
    public CSVBuilderException(String message, StateCensusAnalyserException.exceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    public CSVBuilderException(String message, exceptionType unableToParse) {

    }

    enum exceptionType {
        FILE_NOT_FOUND,
        INCORRECT_FILE,
        NO_CENSUS_DATA,
        UNABLE_TO_PARSE
    }



}
