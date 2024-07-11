package handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exceptions.ClientError;
import exceptions.ServerError;
import pieces.Infantry;
import pieces.Piece;
import pieces.Tank;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public abstract class Handler {
	public Handler() {

	}

	public static Piece stringToPiece(String pieceName) {
		return switch (pieceName) {
			case "Tank" -> new Tank();
			default -> new Infantry();
		};
	}

	protected String getAuthToken(Request request) {
		return request.headers("authToken");
	}

	protected HashMap<String, String> getReqBody(Request request) {
		return new Gson().fromJson(request.body(), new TypeToken<HashMap<String, String>>() {
		}.getType());
	}

	protected void handleDataAccessError(Response response, ClientError clientError) {
		response.status(clientError.getErrorCode());
		response.body(new Gson().toJson(clientError.getMessage()));
	}

	protected void throwServerError(Exception exception) throws ServerError {
		throw new ServerError(exception.getMessage(), 500);
	}
}
