package exceptions;

public class ServiceError extends Exception {
    private final int errorCode;
    public ServiceError (String message, int errorNum) {
        super(message);
        errorCode = errorNum;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
