package exceptions;

public class ServerError extends Exception {
	private final int errorCode;

	public ServerError(String message, int errorNum) {
		super(message);
		errorCode = errorNum;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
