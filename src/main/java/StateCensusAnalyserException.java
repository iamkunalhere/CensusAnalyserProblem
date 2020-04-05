public class StateCensusAnalyserException extends Exception{
    public enum exceptionType {
        FILE_NOT_FOUND,
    }
    exceptionType exceptionTypeObject;
    public StateCensusAnalyserException(exceptionType exceptionTypeObject) {
        this.exceptionTypeObject=exceptionTypeObject;
    }
}
