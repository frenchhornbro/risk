package exceptions;

public class DataAccessError extends ClientError {
	private final int errorCode;

	public DataAccessError(String message, int errorNum) {
		super(message);
		errorCode = errorNum;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
