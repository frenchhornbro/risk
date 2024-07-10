package exceptions;

public abstract class ClientError extends Exception {
    public ClientError(String message) {
        super(message);
    }
    public abstract int getErrorCode();
}
