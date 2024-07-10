package handler;

import com.google.gson.Gson;
import exceptions.DataAccessError;
import exceptions.ServerError;
import game.GameData;
import service.Service;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class JoinGameHandler extends Handler {
    private final Service service;
    public JoinGameHandler() throws DataAccessError {
        service = new Service();
    }
    public Object joinGame(Request request, Response response) throws ServerError {
        try {
            String authToken = super.getAuthToken(request);
            HashMap<String, String> reqBody = super.getReqBody(request);
            String gameID = reqBody.get("gameID");
            Object[] info = service.authenticateGame(authToken, gameID, false);
            String username = (String) info[0];
            GameData gameData = (GameData) info[1];
            response.body(new Gson().toJson(service.joinPlayer(username, gameData, gameID)));
            response.status(200);
        }
        catch (DataAccessError dataAccessError) {
            super.handleDataAccessError(response, dataAccessError);
        }
        catch (Exception exception) {
            super.throwServerError(exception);
        }
        return response;
    }
}
