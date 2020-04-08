public class CSVBuilderException extends Throwable {

    StateCensusAnalyserException.exceptionType type;

    public CSVBuilderException(String message, StateCensusAnalyserException.exceptionType type) {
        super(message);
        this.type = type;
    }
}
