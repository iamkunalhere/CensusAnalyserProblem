public class StateCensusAnalyserException extends Exception{

    public enum exceptionType {
        FILE_NOT_FOUND,
        NO_CENSUS_DATA,
        INCORRECT_FILE,
        UNABLE_TO_PARSE
    }
    exceptionType exceptionTypeObject;
    public StateCensusAnalyserException(String message, String name) {
        super(message);
        this.exceptionTypeObject = exceptionType.valueOf(name);
    }
    public StateCensusAnalyserException(String message , exceptionType exceptionTypeObject) {
        super(message);
        this.exceptionTypeObject=exceptionTypeObject;
    }
    public StateCensusAnalyserException(String message , exceptionType exceptionTypeObject, Throwable cause) {
        super(message,cause);
        this.exceptionTypeObject=exceptionTypeObject;
    }
}
