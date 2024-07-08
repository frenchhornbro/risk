package handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exceptions.DataAccessError;
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
    protected String getAuthToken(Request request) {
        return request.headers("authToken");
    }

    protected HashMap<String, String> getReqBody(Request request) {
        return new Gson().fromJson(request.body(), new TypeToken<HashMap<String, String>>(){}.getType());
    }

    protected void handleDataAccessError(Response response, DataAccessError dataAccessError) {
        response.status(dataAccessError.getErrorCode());
        response.body(new Gson().toJson(dataAccessError.getMessage()));
    }

    protected void throwServerError(Exception exception) throws ServerError {
        throw new ServerError(exception.getMessage(), 500);
    }

    public static Piece stringToPiece(String pieceName) {
        return switch (pieceName) {
            case "Tank" -> new Tank();
            default -> new Infantry();
        };
    }
}
