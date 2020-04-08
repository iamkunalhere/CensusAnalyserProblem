public class CSVBuilderException extends Throwable {
    enum exceptionType {
        FILE_NOT_FOUND,
        INCORRECT_FILE
    }
    StateCensusAnalyserException.exceptionType type;

    public CSVBuilderException(String message, StateCensusAnalyserException.exceptionType type) {
        super(message);
        this.type = type;
    }
    public CSVBuilderException(String message, StateCensusAnalyserException.exceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
