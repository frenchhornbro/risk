package exceptions;

public class CredentialError extends Exception {
    private final int errorCode;
    public CredentialError (String message, int errorNum) {
        super(message);
        errorCode = errorNum;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
